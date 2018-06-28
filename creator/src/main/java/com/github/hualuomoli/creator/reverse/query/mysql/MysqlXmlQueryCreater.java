package com.github.hualuomoli.creator.reverse.query.mysql;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;
import com.github.hualuomoli.creator.reverse.component.entity.JavaUniqueIndex;
import com.github.hualuomoli.creator.reverse.query.XmlQueryCreater;
import com.github.hualuomoli.tool.util.TemplateUtils;
import com.google.common.collect.Maps;

@Component(value = "com.github.hualuomoli.creator.reverse.query.mysql.MysqlXmlQueryCreater")
public class MysqlXmlQueryCreater implements XmlQueryCreater {

  @Override
  public void create(String outputProjectPath, String rootPackageName, String entityName//
      , List<JavaColumn> javaColumns, List<JavaUniqueIndex> uniques, String tableName) {
    String outputPath = outputProjectPath + "/src/main/resources/mappers/" + StringUtils.replace(rootPackageName, ".", "/");

    Map<String, Object> data = Maps.newHashMap();
    data.put("packageName", rootPackageName);
    data.put("javaName", entityName);
    data.put("columns", javaColumns);
    data.put("uniques", uniques);
    data.put("tableName", tableName);

    TemplateUtils.processByResource("tpl/query/mysql", "xml.tpl", data, new File(outputPath + "/query", entityName + "QueryMapper.xml"));
  }

}
