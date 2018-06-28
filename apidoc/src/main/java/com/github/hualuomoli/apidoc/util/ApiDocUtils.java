package com.github.hualuomoli.apidoc.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.apidoc.entity.ApiDoc;
import com.github.hualuomoli.apidoc.entity.Parameter;
import com.github.hualuomoli.apidoc.entity.Server;
import com.github.hualuomoli.apidoc.enums.ParameterTypeEnum;
import com.github.hualuomoli.apidoc.filter.Filter;
import com.github.hualuomoli.tool.util.TemplateUtils;

/**
 * API DOC生成器
 */
public class ApiDocUtils {

  private static final Logger logger = LoggerFactory.getLogger(ApiDocUtils.class);
  private static final ClassLoader LOADER = ApiDocUtils.class.getClassLoader();;

  private static String apiPath = null;
  private static String encoding = null;
  private static Filter filter = null;

  private static String flushPath = null;
  private static List<Server> servers = null;

  /**
   * 输出API文档
   * @param docs API文档
   * @param flushPath 输出地址
   * @param servers 服务器
   */
  public static void flush(List<ApiDoc> docs, String flushPath, List<Server> servers) {
    ApiDocUtils.flushPath = flushPath;
    ApiDocUtils.servers = servers;

    // copy
    copy();

    // flush
    for (ApiDoc doc : docs) {
      flush(doc);
    }

    // 输出index
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("docs", docs);
    TemplateUtils.processByResource("tpl", "index.tpl", map, new File(flushPath, "index.html"));
  }

  /**
   * 复制公共信息
   */
  private static void copy() {
    // fonts
    copy("fonts", "FontAwesome.otf");
    copy("fonts", "fontawesome-webfont.eot");
    copy("fonts", "fontawesome-webfont.svg");
    copy("fonts", "fontawesome-webfont.ttf");
    copy("fonts", "fontawesome-webfont.woff");
    copy("fonts", "fontawesome-webfont.woff2");

    // css
    copy("css", "font-awesome.css");
    copy("css", "api.css");
    copy("css", "index.css");
    copy("css", "sign.css");

    // js
    copy("js", "jquery.js");
    copy("js", "api.js");

    // html
    // copy(null, "api.html");
    copy(null, "sign.html");
  }

  private static void copy(String folder, String filename) {
    try {
      String resource = null;
      String outputFilename = null;
      if (folder == null) {
        resource = "tpl/" + filename;
        outputFilename = filename;
      } else {
        resource = "tpl/" + folder + "/" + filename;
        outputFilename = folder + "/" + filename;
      }
      logger.info("copy resource {}", resource);
      logger.info("output filename {}", outputFilename);
      FileUtils.copyInputStreamToFile(LOADER.getResourceAsStream(resource), new File(flushPath, outputFilename));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 输出APi
   * @param doc API文档
   */
  public static void flush(ApiDoc doc) {
    logger.info("create apidoc {}", doc.getTitle());
    logger.debug("apidoc data={}", doc);

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("servers", servers);
    map.put("method", doc.getMethod());
    map.put("title", doc.getTitle());
    map.put("description", doc.getDescription());
    map.put("errors", doc.getErrors());
    map.put("requestParameters", doc.getRequests());
    map.put("responseParameters", doc.getResponses());

    TemplateUtils.processByResource("tpl", "api.tpl", map, new File(new File(flushPath, doc.getRelativePath()), doc.getMethod() + ".html"));
  }

  /**
   * 获取API文档
   * @param apiPath API源码路径
   * @param encoding 源码编码集
   * @param filter 过滤器
   * @return API文档
   * @throws IOException 文件未找到
   */
  public static List<ApiDoc> getApiDocs(String apiPath, String encoding, Filter filter) throws IOException {
    ApiDocUtils.apiPath = apiPath;
    ApiDocUtils.encoding = encoding;
    ApiDocUtils.filter = filter;

    File dir = new File(apiPath);
    return call(dir, "");
  }

  /**
   * 调用
   * @param dir 文件目录
   * @param filter 过滤器
   * @return API文档
   * @throws IOException 文件未找到
   */
  private static List<ApiDoc> call(File dir, String parentPackageName) throws IOException {
    List<ApiDoc> docs = new ArrayList<ApiDoc>();

    File[] files = dir.listFiles();
    String packageName = parentPackageName + "." + dir.getName();
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      if (file.isFile()) {
        // support
        if (filter != null && !filter.support(packageName.substring(".java.".length()), file)) {
          continue;
        }

        String name = file.getName();
        if (name.endsWith("Request.java")) {
          File request = file;
          File response = null;
          String responseFilename = name.substring(0, name.length() - "Request.java".length()) + "Response.java";
          for (int j = 0; j < files.length; j++) {
            File f = files[j];
            if (f.isFile() && f.getName().equals(responseFilename)) {
              response = f;
              break;
            }
          }
          if (response == null) {
            throw new RuntimeException("there is no file " + responseFilename);
          }
          ApiDoc doc = call(request, response);
          docs.add(doc);
        }
      } else if (file.isDirectory()) {
        List<ApiDoc> subDocs = call(file, packageName);
        docs.addAll(subDocs);
      }
    } // end for
    return docs;
  }

  /**
   * 调用
   * @param request 请求文件
   * @param response 响应文件
   * @return API文档
   * @throws IOException 未见未找到
   */
  private static ApiDoc call(File request, File response) throws IOException {
    List<String> requestLines = FileUtils.readLines(request, encoding);
    List<String> responseLines = FileUtils.readLines(response, encoding);

    ApiDoc doc = new ApiDoc();
    configureApi(doc, requestLines);
    doc.setRequests(getParameter(requestLines));
    doc.setResponses(getParameter(responseLines));

    // configure level
    Integer level = 0;
    configureLevel(doc.getRequests(), level);
    configureLevel(doc.getResponses(), level);

    return doc;
  }

  /**
   * 配置级别
   * @param parameters 参数
   * @param level 级别
   */
  private static void configureLevel(List<Parameter> parameters, Integer level) {
    if (parameters == null || parameters.size() == 0) {
      return;
    }
    for (Parameter parameter : parameters) {
      parameter.setLevel(level);
      configureLevel(parameter.getParameters(), level + 1);
    }
  }

  /**
   * 获取参数
   * @param lines 数据
   * @return 参数
   * @throws IOException 文件找不到
   */
  private static List<Parameter> getParameter(List<String> lines) throws IOException {
    List<Parameter> parameters = new ArrayList<Parameter>();

    Parameter parameter = null;
    boolean start = false;
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i).trim();

      if (line.length() == 0) {
        continue;
      }

      // class
      if (line.startsWith("public class ")) {
        start = true;
        continue;
      }

      // check start
      if (!start) {
        continue;
      }

      // 注释开始,以下为一个参数
      if (line.startsWith("/**")) {
        parameter = new Parameter();
        parameter.setDescription("");
        continue;
      }

      // 注释
      if (line.startsWith("* ")) {
        line = line.substring(2);
        if (line.length() == 0) {
          continue;
        }

        if (!line.startsWith("@")) {
          // description
          parameter.setDescription(parameter.getDescription() + line);
        } else {
          // required
          if (line.startsWith(Name.PARAMETER_REQUIRED)) {
            parameter.setRequired(true);
            continue;
          }
          // parameter length
          if (line.startsWith(Name.PARAMETER_MAXLENGTH)) {
            line = line.substring(Name.PARAMETER_MAXLENGTH.length()).trim();
            parameter.setMaxLength(Integer.parseInt(line));
            continue;
          }
          // sample
          if (line.startsWith(Name.PARAMETER_SAMPLE)) {
            line = line.substring(Name.PARAMETER_SAMPLE.length()).trim();
            parameter.setSample(line);
            continue;
          }
        }

      }

      // 属性
      if (line.startsWith("private ") && line.endsWith(";")) {

        line = line.substring("private ".length(), line.length() - 1);

        // 参数信息
        String[] array = line.split(" ");
        String type = array[0];
        String name = array[1];

        if (parameter == null) {
          throw new RuntimeException("please add Notes for " + name);
        }

        // 属性名
        parameter.setName(name);

        if (isBasicType(type)) {
          parameter.setParameterType(ParameterTypeEnum.SIMPLE);
          parameter.setType(type);
          // simple
        } else if (type.startsWith("Set<")) {
          // set
          type = type.substring(4, type.length() - 1);
          parameter.setParameterType(ParameterTypeEnum.ARRAY);
          parameter.setType("Array");
          configureSubParameter(parameter, lines, type);
        } else if (type.startsWith("List")) {
          // list
          type = type.substring(5, type.length() - 1);
          parameter.setParameterType(ParameterTypeEnum.ARRAY);
          parameter.setType("Array");
          configureSubParameter(parameter, lines, type);
        } else if (type.endsWith("[]")) {
          // array
          type = type.substring(0, type.length() - 2);
          parameter.setParameterType(ParameterTypeEnum.ARRAY);
          parameter.setType("Array");
          configureSubParameter(parameter, lines, type);
        } else {
          // object
          parameter.setParameterType(ParameterTypeEnum.OBJECT);
          parameter.setType("Object");
          configureSubParameter(parameter, lines, type);
        }

        parameters.add(parameter);

        parameter = null;
      }

      // getter and setter
      if (line.startsWith("public ")) {
        break;
      }

    }

    return parameters;

  }

  /**
   * 配置子参数
   * @param parameter 当前参数
   * @param lines 当前数据信息
   * @param className 子参数类名
   * @throws IOException 文件未找到
   */
  private static void configureSubParameter(Parameter parameter, List<String> lines, String className) throws IOException {

    if (isBasicType(className)) {
      parameter.setParameterType(ParameterTypeEnum.SIMPLE);
      parameter.setType(className + "[]");
      return;
    }

    // 判断是否为外部引用类
    String subClassFullName = getSubClassFullName(lines, className);

    if (subClassFullName == null) {
      // 内部类

      if (isInnerEnum(lines, className)) {
        // 枚举
        parameter.setParameterType(ParameterTypeEnum.SIMPLE);
        parameter.setType("String[]");

        // 备注
        List<Parameter> enumParameters = getInnerEnums(lines, className);
        String desciption = parameter.getDescription();
        for (Parameter enumParameter : enumParameters) {
          desciption += "<br />" + enumParameter.getName() + "=" + enumParameter.getDescription();
        }
        parameter.setDescription(desciption);
      } else {
        // 类
        parameter.setParameters(getInnerSubParameter(lines, className));
      }
    } else {
      // 外部引用
      String packageName = subClassFullName.substring(0, subClassFullName.lastIndexOf(".")).replaceAll("[.]", "/");
      String filename = subClassFullName.substring(subClassFullName.lastIndexOf(".") + 1) + ".java";
      lines = FileUtils.readLines(new File(new File(apiPath, packageName), filename), encoding);

      if (isOuterEnum(lines, className)) {
        // 枚举
        parameter.setParameterType(ParameterTypeEnum.SIMPLE);
        parameter.setType("String[]");

        // 备注
        List<Parameter> enumParameters = getOuterEnums(lines);
        String desciption = parameter.getDescription();
        for (Parameter enumParameter : enumParameters) {
          desciption += "<br />" + enumParameter.getName() + "=" + enumParameter.getDescription();
        }
        parameter.setDescription(desciption);
      } else {
        // 类
        parameter.setParameters(getParameter(lines));
      }

    }
    // end
  }

  /**
   * 获取类的全路径
   * @param lines 数据
   * @param className 类名
   * @return 如果类为引用类,返回类的全路径,否则返回null
   */
  private static String getSubClassFullName(List<String> lines, String className) {

    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i).trim();
      if (line.length() == 0) {
        continue;
      }
      // import导入的外部类
      if (line.startsWith("import ") && line.endsWith("." + className + ";")) {
        return line.substring("import ".length(), line.length() - 1).trim();
      }
      // 找到了类的定义
      if (line.startsWith("public class ")) {
        break;
      }
    }
    return null;
  }

  /**
   * 是否是外部引用枚举
   * @param lines 数据
   * @param className 类名
   * @return 是否是枚举
   */
  private static boolean isOuterEnum(List<String> lines, String className) {
    String classFullname = "public enum " + className;
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i).trim();
      if (line.length() == 0) {
        continue;
      }

      if (line.startsWith(classFullname)) {
        return true;
      }
    }

    return false;
  }

  /**
   * 获取外部枚举的属性
   * @param lines 枚举类的所有数据
   * @return 枚举的属性
   */
  private static List<Parameter> getOuterEnums(List<String> lines) {
    List<Parameter> parameters = new ArrayList<Parameter>();
    boolean start = false;
    Parameter parameter = null;
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i).trim();
      if (line.length() == 0) {
        continue;
      }

      // 枚举类开始
      if (line.startsWith("public enum ")) {
        start = true;
        continue;
      }

      // 还没有到枚举的定义
      if (!start) {
        continue;
      }

      // 枚举属性定义结束
      if (line.startsWith("private ") || line.equals("}")) {
        break;
      }

      // 注释开始
      if (line.equals("/**")) {
        parameter = new Parameter();
        parameter.setDescription("");
        continue;
      }

      // 注释结束
      if (line.equals("/*")) {
        continue;
      }

      // 注释
      if (line.startsWith("* ")) {
        parameter.setDescription(parameter.getDescription() + line.substring(2).trim());
      }

      // 枚举
      if (line.endsWith(",") || line.endsWith(";")) {
        line = line.substring(0, line.length() - 1);
        if (line.indexOf("(") > 0) {
          // 包含内部名称
          line = line.substring(0, line.indexOf("("));
        }
        parameter.setName(line);
        parameters.add(parameter);
      }

    }
    return parameters;
  }

  /**
   * 是否是内部定义枚举
   * @param lines 数据
   * @param className 类名
   * @return 是否是枚举
   */
  private static boolean isInnerEnum(List<String> lines, String className) {
    String innerClassFullname = "public static enum " + className + " {";
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i).trim();
      if (line.length() == 0) {
        continue;
      }

      if (line.startsWith(innerClassFullname)) {
        return true;
      }
    }

    return false;
  }

  /**
   * 获取外部枚举的属性
   * @param lines 枚举类的所有数据
   * @return 枚举的属性
   */
  private static List<Parameter> getInnerEnums(List<String> lines, String className) {
    String innerClassFullname = "public static enum " + className + " {";
    List<Parameter> parameters = new ArrayList<Parameter>();
    boolean start = false;
    Parameter parameter = null;
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i).trim();
      if (line.length() == 0) {
        continue;
      }

      // 枚举类开始
      if (line.startsWith(innerClassFullname)) {
        start = true;
        continue;
      }

      // 还没有到枚举的定义
      if (!start) {
        continue;
      }

      // 枚举属性定义结束
      if (line.startsWith("private ") || line.equals("}")) {
        break;
      }

      // 注释开始
      if (line.equals("/**")) {
        parameter = new Parameter();
        parameter.setDescription("");
        continue;
      }

      // 注释结束
      if (line.equals("/*")) {
        continue;
      }

      // 注释
      if (line.startsWith("* ")) {
        parameter.setDescription(parameter.getDescription() + line.substring(2).trim());
      }

      // 枚举
      if (line.endsWith(",") || line.endsWith(";")) {
        line = line.substring(0, line.length() - 1);
        if (line.indexOf("(") > 0) {
          // 包含内部名称
          line = line.substring(0, line.indexOf("("));
        }
        parameter.setName(line);
        parameters.add(parameter);
      }

    }
    return parameters;
  }

  /**
   * 获取内部类下级的参数
   * @param lines 数据
   * @param skip 跨过的行数
   * @param className 类名
   * @return 下级的参数
   * @throws IOException 文件找不到
   */
  private static List<Parameter> getInnerSubParameter(List<String> lines, String className) throws IOException {
    List<Parameter> parameters = new ArrayList<Parameter>();

    boolean start = false;
    Parameter parameter = null;
    String classDefinition = "public static class " + className + " {";
    logger.debug("classDefinition={}", classDefinition);
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i).trim();
      if (line.length() == 0) {
        continue;
      }

      // public static class
      if (line.startsWith(classDefinition)) {
        start = true;
        continue;
      }

      // 没有开始
      if (!start) {
        continue;
      }

      // 注释开始,以下为参数
      if (line.startsWith("/**")) {
        parameter = new Parameter();
        parameter.setDescription("");
        continue;
      }

      // 注释
      if (line.startsWith("* ")) {
        line = line.substring(2);
        if (line.length() == 0) {
          continue;
        }

        if (!line.startsWith("@")) {
          // description
          parameter.setDescription(parameter.getDescription() + line);
        } else {
          // required
          if (line.startsWith(Name.PARAMETER_REQUIRED)) {
            parameter.setRequired(true);
            continue;
          }
          // parameter length
          if (line.startsWith(Name.PARAMETER_MAXLENGTH)) {
            line = line.substring(Name.PARAMETER_MAXLENGTH.length()).trim();
            parameter.setMaxLength(Integer.parseInt(line));
            continue;
          }
          // sample
          if (line.startsWith(Name.PARAMETER_SAMPLE)) {
            line = line.substring(Name.PARAMETER_SAMPLE.length()).trim();
            parameter.setSample(line);
            continue;
          }
        }

      }

      // 属性
      if (line.startsWith("private ") && line.endsWith(";")) {
        line = line.substring("private ".length(), line.length() - 1);

        // 参数信息
        String[] array = line.split(" ");
        String type = array[0];
        String name = array[1];
        parameter.setName(name);

        if (isBasicType(type)) {
          parameter.setParameterType(ParameterTypeEnum.SIMPLE);
          parameter.setType(type);
        } else if (type.startsWith("Set<")) {
          // set
          type = type.substring(4, type.length() - 1);
          parameter.setParameterType(ParameterTypeEnum.ARRAY);
          parameter.setType("Array");
          configureSubParameter(parameter, lines, type);
        } else if (type.startsWith("List")) {
          // list
          type = type.substring(5, type.length() - 1);
          parameter.setParameterType(ParameterTypeEnum.ARRAY);
          parameter.setType("Array");
          configureSubParameter(parameter, lines, type);
        } else if (type.endsWith("[]")) {
          // array
          type = type.substring(0, type.length() - 2);
          parameter.setParameterType(ParameterTypeEnum.ARRAY);
          parameter.setType("Array");
          configureSubParameter(parameter, lines, type);
        } else {
          // object
          parameter.setParameterType(ParameterTypeEnum.OBJECT);
          parameter.setType("Object");
          configureSubParameter(parameter, lines, type);
        }

        parameters.add(parameter);

        parameter = null;
      }

      // getter or setter
      if (line.startsWith("public ")) {
        break;
      }

    }

    return parameters;
  }

  /**
   * 配置API信息
   * @param doc 文档
   * @param lines 数据
   */
  private static void configureApi(ApiDoc doc, List<String> lines) {
    doc.setDescription("");
    List<com.github.hualuomoli.apidoc.entity.Error> errors = new ArrayList<com.github.hualuomoli.apidoc.entity.Error>();
    doc.setErrors(errors);

    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i).trim();
      if (line.length() == 0) {
        continue;
      }

      // 注释
      if (line.startsWith("* ")) {
        line = line.substring(2).trim();
        if (line.length() == 0) {
          continue;
        }
        if (!line.startsWith("@")) {
          // description
          doc.setDescription(doc.getDescription() + line);
        } else {
          // 其他注释
          // modules
          if (line.startsWith(Name.API_MODULE)) {
            doc.setModule(line.substring(Name.API_MODULE.length()).trim());
            continue;
          }
          // method
          if (line.startsWith(Name.API_METHOD)) {
            doc.setMethod(line.substring(Name.API_METHOD.length()).trim());
            continue;
          }
          // title
          if (line.startsWith(Name.API_TITLE)) {
            doc.setTitle(line.substring(Name.API_TITLE.length()).trim());
            continue;
          }
          // error
          if (line.startsWith(Name.API_ERROR)) {
            line = line.substring(Name.API_ERROR.length()).trim();
            String[] array = line.split("[|]");
            com.github.hualuomoli.apidoc.entity.Error error = new com.github.hualuomoli.apidoc.entity.Error();
            error.setCode(array[0].trim());
            error.setMessage(array[1].trim());
            if (array.length == 2) {
              error.setDeal("");
            } else {
              error.setDeal(array[2].trim());
            }
            errors.add(error);
          }
        }
      }

      // 找到类
      if (line.startsWith("public class ")) {
        break;
      }

    } // end for

    // 如果没设置模块,使用方法
    if (doc.getModule() == null) {
      doc.setModule(doc.getMethod());
    }

  } // end configure

  /**
   * 是否是基本数据类型
   * @param className 类名
   * @return 是否是基本数据类型
   */
  private static boolean isBasicType(String className) {
    return (className.equals("Date") || className.equals("String")// 
        || className.equals("Integer") || className.equals("Double") || className.equals("Long"));
  }

}
