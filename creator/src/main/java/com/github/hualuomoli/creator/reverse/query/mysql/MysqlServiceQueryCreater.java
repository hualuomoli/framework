package com.github.hualuomoli.creator.reverse.query.mysql;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;
import com.github.hualuomoli.creator.reverse.component.entity.JavaUniqueIndex;
import com.github.hualuomoli.creator.reverse.query.ServiceQueryCreater;
import com.github.hualuomoli.tool.util.TemplateUtils;
import com.google.common.collect.Maps;

@Component(value = "com.github.hualuomoli.creator.reverse.query.mysql.MysqlServiceQueryCreater")
public class MysqlServiceQueryCreater implements ServiceQueryCreater {

  @Override
  public void create(String outputProjectPath, String rootPackageName, String entityName, String entityComment//
      , List<JavaColumn> javaColumns, List<JavaUniqueIndex> uniques) {
    String outputPath = outputProjectPath + "/src/main/java/" + StringUtils.replace(rootPackageName, ".", "/");

    Map<String, Object> data = Maps.newHashMap();
    data.put("comment", entityComment);
    data.put("packageName", rootPackageName);
    data.put("javaName", entityName);
    data.put("columns", javaColumns);
    data.put("uniques", uniques);

    TemplateUtils.processByResource("tpl/query/mysql", "service.tpl", data, new File(outputPath + "/query/service", entityName + "QueryService.java"));
  }

}
