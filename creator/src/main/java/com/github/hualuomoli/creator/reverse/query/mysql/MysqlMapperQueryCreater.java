package com.github.hualuomoli.creator.reverse.query.mysql;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;
import com.github.hualuomoli.creator.reverse.component.entity.JavaUniqueIndex;
import com.github.hualuomoli.creator.reverse.query.MapperQueryCreater;
import com.github.hualuomoli.tool.util.TemplateUtils;
import com.google.common.collect.Maps;

@Service(value = "com.github.hualuomoli.creator.reverse.query.mysql.MysqlMapperQueryCreater")
public class MysqlMapperQueryCreater implements MapperQueryCreater {

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

    TemplateUtils.processByResource("tpl/query/mysql", "mapper.tpl", data, new File(outputPath + "/query/mapper", entityName + "QueryMapper.java"));
  }

}
