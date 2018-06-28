package com.github.hualuomoli.creator.reverse.query.mysql;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.creator.ServiceTest;
import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.entity.DBUniqueIndex;
import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;
import com.github.hualuomoli.creator.reverse.component.entity.JavaUniqueIndex;
import com.github.hualuomoli.creator.reverse.component.mysql.parser.MysqlParser;
import com.github.hualuomoli.creator.reverse.component.mysql.service.MysqlDBService;

public class MysqlMapperQueryCreaterTest extends ServiceTest {

  @Autowired
  private MysqlDBService dbService;
  @Autowired
  private MysqlParser parser;

  @Autowired
  private MysqlMapperQueryCreater mapperQueryCreater;

  @Test
  public void testCreate() {
    List<DBColumn> dbColumns = dbService.findList(DATABASE_NAME, TABLE_NAME);
    String comments = dbService.findComment(DATABASE_NAME, TABLE_NAME);
    String primaryKey = dbService.findPrimaryKey(DATABASE_NAME, TABLE_NAME);
    List<DBUniqueIndex> uniques = dbService.findUniqueKey(DATABASE_NAME, TABLE_NAME);

    List<JavaColumn> javaColumns = parser.parse(dbColumns, primaryKey, null);
    List<JavaUniqueIndex> javaUniqueIndexs = parser.parse(javaColumns, uniques);

    mapperQueryCreater.create(outputPath, packageName, ENTITY_NAME, comments, javaColumns, javaUniqueIndexs);

    logger.info("mapper created.");
  }

}
