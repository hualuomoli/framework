package com.github.hualuomoli.creator.reverse.component.parser;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.hualuomoli.creator.ServiceTest;
import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;
import com.github.hualuomoli.creator.reverse.component.mysql.parser.MysqlParser;
import com.github.hualuomoli.creator.reverse.component.mysql.service.MysqlDBService;
import com.github.hualuomoli.creator.reverse.component.parser.Parser.Resolver;

public class ParserTest extends ServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(ParserTest.class);

  @Autowired
  private MysqlDBService dbService;
  @Autowired
  private MysqlParser parser;

  @Test
  public void testFindList() {
    String comment = dbService.findComment(DATABASE_NAME, TABLE_NAME);
    List<DBColumn> dbColumns = dbService.findList(DATABASE_NAME, TABLE_NAME);
    String primaryKey = dbService.findPrimaryKey(DATABASE_NAME, TABLE_NAME);
    logger.debug("comment={}", comment);
    logger.debug("dbColumns={}", JSON.toJSONString(dbColumns));
    logger.debug("primaryKey={}", primaryKey);

    List<JavaColumn> javaColumns = parser.parse(dbColumns, primaryKey, null);
    logger.debug("javaColumns={}", JSON.toJSONString(javaColumns));
    javaColumns = parser.parse(dbColumns, primaryKey, new Resolver() {

      @Override
      public String resolverJavaName(Class<?> javaType, String javaName, String dbName) {
        return javaName;
      }

      @Override
      public Class<?> resolverJavaType(Class<?> javaType, String javaName, String dbName) {
        return String.class;
      }
    });
    logger.debug("javaColumns={}", JSON.toJSONString(javaColumns));

  }

}
