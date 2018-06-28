package com.github.hualuomoli.creator.reverse.component.mysql.parser;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.parser.AbstractParser;
import com.github.hualuomoli.creator.reverse.component.parser.Parser;

@Component(value = "com.github.hualuomoli.creator.reverse.component.mysql.parser.MysqlParser")
public class MysqlParser extends AbstractParser implements Parser {

  @Override
  protected Class<?> formatJavaType(DBColumn dbColumn) {
    switch (dbColumn.getDataType()) {
    case "varchar":
      return String.class;
    case "char":
      return String.class;
    case "text":
      return String.class;
    case "longtext":
      return String.class;
    case "int":
      return Integer.class;
    case "smallint":
      return Integer.class;
    case "tinyint":
      return Integer.class;
    case "mediumint":
      return Integer.class;
    case "bigint":
      return Integer.class;
    case "integer":
      return Integer.class;
    case "double":
      return Double.class;
    case "decimal":
      return Double.class;
    case "datetime":
      return Date.class;
    case "timestamp":
      return Date.class;
    case "date":
      return Date.class;
    case "time":
      return Date.class;
    case "enum":
      return String.class;
    default:
      throw new RuntimeException("can not support data type " + dbColumn.getDataType());
    }
  }

}
