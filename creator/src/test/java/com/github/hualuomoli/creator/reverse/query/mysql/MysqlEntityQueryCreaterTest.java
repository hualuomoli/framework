package com.github.hualuomoli.creator.reverse.query.mysql;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.creator.ServiceTest;
import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;
import com.github.hualuomoli.creator.reverse.component.mysql.parser.MysqlParser;
import com.github.hualuomoli.creator.reverse.component.mysql.service.MysqlDBService;
import com.github.hualuomoli.creator.reverse.component.parser.Parser.Resolver;
import com.github.hualuomoli.enums.StateEnum;
import com.github.hualuomoli.enums.StatusEnum;

public class MysqlEntityQueryCreaterTest extends ServiceTest {

  @Autowired
  private MysqlDBService dbService;
  @Autowired
  private MysqlParser parser;

  @Autowired
  private MysqlEntityQueryCreater entityQueryCreater;

  @Test
  public void testCreate() {
    List<DBColumn> dbColumns = dbService.findList(DATABASE_NAME, TABLE_NAME);
    String comments = dbService.findComment(DATABASE_NAME, TABLE_NAME);
    String primaryKey = dbService.findPrimaryKey(DATABASE_NAME, TABLE_NAME);

    List<JavaColumn> javaColumns = parser.parse(dbColumns, primaryKey, new Resolver() {

      @Override
      public String resolverJavaName(Class<?> javaType, String javaName, String dbName) {
        return javaName;
      }

      @Override
      public Class<?> resolverJavaType(Class<?> javaType, String javaName, String dbName) {
        Class<?> result = null;
        switch (dbName) {
        case "state":
          result = StateEnum.class;
          break;
        case "status":
          result = StatusEnum.class;
          break;
        default:
          result = javaType;
          break;
        }
        return result;
      }
    });

    entityQueryCreater.create(outputPath, packageName, ENTITY_NAME, comments, javaColumns);

    logger.info("entity created.");
  }

}
