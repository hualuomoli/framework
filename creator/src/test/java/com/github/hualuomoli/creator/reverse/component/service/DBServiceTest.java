package com.github.hualuomoli.creator.reverse.component.service;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.hualuomoli.creator.ServiceTest;
import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.entity.DBUniqueIndex;
import com.github.hualuomoli.creator.reverse.component.mysql.service.MysqlDBService;

public class DBServiceTest extends ServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(DBServiceTest.class);

  @Autowired
  private MysqlDBService dbService;

  @Test
  public void testFindList() {
    String comment = dbService.findComment(DATABASE_NAME, TABLE_NAME);
    List<DBColumn> dbColumns = dbService.findList(DATABASE_NAME, TABLE_NAME);
    String primaryKey = dbService.findPrimaryKey(DATABASE_NAME, TABLE_NAME);
    List<DBUniqueIndex> uniqueKeys = dbService.findUniqueKey(DATABASE_NAME, TABLE_NAME);

    logger.debug("comment={}", comment);
    logger.debug("dbColumns={}", JSON.toJSONString(dbColumns));
    logger.debug("primaryKey={}", primaryKey);
    logger.debug("uniqueKeys={}", JSON.toJSONString(uniqueKeys));

  }

}
