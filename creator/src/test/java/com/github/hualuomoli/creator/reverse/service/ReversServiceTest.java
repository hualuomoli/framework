package com.github.hualuomoli.creator.reverse.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.creator.ServiceTest;
import com.github.hualuomoli.creator.reverse.component.parser.Parser.Resolver;
import com.github.hualuomoli.enums.StateEnum;
import com.github.hualuomoli.enums.StatusEnum;

public class ReversServiceTest extends ServiceTest {

  @Autowired
  private ReversService reversService;

  @Test
  public void testCreate() {
    reversService.create(outputPath, packageName, DATABASE_NAME, TABLE_NAME, ENTITY_NAME, new Resolver() {

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

    logger.info("revers created.");
  }

}
