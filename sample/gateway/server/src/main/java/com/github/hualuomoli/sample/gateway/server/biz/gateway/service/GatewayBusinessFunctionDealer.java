package com.github.hualuomoli.sample.gateway.server.biz.gateway.service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.hualuomoli.framework.mvc.version.annotation.ApiVersion;
import com.github.hualuomoli.gateway.server.business.dealer.FunctionDealer;
import com.github.hualuomoli.gateway.server.business.entity.Function;
import com.github.hualuomoli.gateway.server.lang.NoRouterException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component(value = "com.github.hualuomoli.sample.gateway.server.biz.gateway.service.GatewayBusinessFunctionDealer")
public class GatewayBusinessFunctionDealer implements FunctionDealer, ApplicationContextAware {

  private static final Logger logger = LoggerFactory.getLogger(GatewayBusinessFunctionDealer.class);

  private static final String VERSION_REGEX = "^\\d+(.\\d+)*$";
  private static final Map<String, List<VersionFunction>> functionMap = Maps.newHashMap();

  private ApplicationContext context;

  @SuppressWarnings("unchecked")
  @Override
  public VersionFunction getFunction(String method, HttpServletRequest request) throws NoRouterException {
    String url = "/" + StringUtils.replace(method, ".", "/");
    logger.debug("searching method {} for url {}", method, url);

    List<VersionFunction> functions = functionMap.get(url);
    if (functions == null || functions.size() == 0) {
      throw new NoRouterException(method);
    }

    // get supports
    String version = this.getRequestVersion(request);
    List<VersionFunction> supports = Lists.newArrayList();
    for (VersionFunction f : functions) {
      if (this.versionSupport(f, version)) {
        supports.add(f);
      }
    }
    if (supports == null || supports.size() == 0) {
      throw new NoRouterException(method);
    }

    // sort desc
    Collections.sort(supports, new Comparator<VersionFunction>() {

      @Override
      public int compare(VersionFunction o1, VersionFunction o2) {
        return GatewayBusinessFunctionDealer.this.versionCompile(o2.version, o1.version);
      }
    });

    return supports.get(0);
  }

  private String getRequestVersion(HttpServletRequest request) {
    String version = null;
    if (StringUtils.isBlank(version)) {
      version = request.getHeader("api-version");
    }

    return version;
  }

  private boolean versionSupport(VersionFunction function, String version) {
    if (StringUtils.isBlank(version)) {
      return true;
    }
    return this.versionCompile(function.version, version) <= 0;
  }

  public int versionCompile(String v1, String v2) {
    if (v1 == null && v2 == null) {
      return 0;
    }
    if (v1 == null) {
      return -1;
    }
    if (v2 == null) {
      return 1;
    }
    Validate.matchesPattern(v1, VERSION_REGEX);
    Validate.matchesPattern(v2, VERSION_REGEX);

    if (StringUtils.equals(v1, v2)) {
      return 0;
    }

    String[] array1 = StringUtils.split(v1, ".");
    String[] array2 = StringUtils.split(v2, ".");

    int len1 = array1.length;
    int len2 = array2.length;
    int len = len1 > len2 ? len2 : len1;
    for (int i = 0; i < len; i++) {
      Integer i1 = Integer.parseInt(array1[i]);
      Integer i2 = Integer.parseInt(array2[i]);
      int c = i1 - i2;
      if (c == 0) {
        continue;
      }
      return c;
    }

    return len1 - len2;
  }

  @Override
  public <F extends Function> Object getDealer(F function) {
    return context.getBean(function.getClazz());
  }

  @Override
  public void init() {
    logger.info("instance gateway functions.............");
    Map<String, Object> map = context.getBeansWithAnnotation(RequestMapping.class);
    Collection<Object> dealers = map.values();
    for (Object dealer : dealers) {
      Tool.parse(dealer.getClass());
    }
  }

  private static class Tool {

    private static void parse(Class<?> clazz) {
      Method[] methods = clazz.getDeclaredMethods();
      for (Method method : methods) {
        Tool.parser(clazz, method);
      }
    }

    private static void parser(Class<?> clazz, Method method) {
      RequestMapping classRequestMapping = clazz.getAnnotation(RequestMapping.class);
      String[] classValues = classRequestMapping.value();
      if (classValues == null || classValues.length == 0) {
        return;
      }

      RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
      if (methodRequestMapping == null) {
        return;
      }

      String[] methodValues = methodRequestMapping.value();
      if (methodValues == null || methodValues.length == 0) {
        return;
      }

      for (String classValue : classValues) {
        for (String methodValue : methodValues) {
          String url = Tool.getUrl(classValue, methodValue);
          VersionFunction f = new VersionFunction();
          f.setClazz(clazz);
          f.setMethod(method);
          f.setUrl(url);
          f.version = Tool.getVersion(clazz, method);

          logger.info("init function {}", f);

          // add
          List<VersionFunction> list = functionMap.get(url);
          if (list == null) {
            list = Lists.newArrayList();
            functionMap.put(url, list);
          }
          list.add(f);
        }
      }
      // end
    }

    private static String getUrl(String classValue, String methodValue) {
      if (!StringUtils.startsWith(classValue, "/")) {
        classValue = "/" + classValue;
      }
      if (StringUtils.endsWith(classValue, "/")) {
        classValue = classValue.substring(0, classValue.length() - 1);
      }
      if (!StringUtils.startsWith(methodValue, "/")) {
        methodValue = "/" + methodValue;
      }
      if (StringUtils.endsWith(methodValue, "/")) {
        methodValue = methodValue.substring(0, methodValue.length() - 1);
      }
      return classValue + methodValue;
    }

    private static String getVersion(Class<?> clazz, Method method) {
      ApiVersion classApiVersion = clazz.getAnnotation(ApiVersion.class);
      ApiVersion methodApiVersion = method.getAnnotation(ApiVersion.class);
      return methodApiVersion == null ? classApiVersion == null ? null : classApiVersion.value() : methodApiVersion.value();
    }

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
    //    this.init(applicationContext);
  }

  public static class VersionFunction extends Function {

    private String version;

    public String getVersion() {
      return version;
    }

    public void setVersion(String version) {
      this.version = version;
    }

    @Override
    public String toString() {
      return "VersionFunction [version=" + version + "]" + super.toString();
    }

  }

}
