package com.github.hualuomoli.sample.framework.creator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.creator.reverse.component.parser.Parser.Resolver;
import com.github.hualuomoli.creator.reverse.service.ReversService;
import com.github.hualuomoli.sample.enums.StateEnum;
import com.github.hualuomoli.sample.enums.StatusEnum;
import com.github.hualuomoli.sample.framework.ServiceTest;

public class AtomiksCratorTest extends ServiceTest {

  private static final String outputPath;
  private String packageName = "com.github.hualuomoli.sample.atomikos";

  @Autowired
  private ReversService reversService;

  static {
    String path = AtomiksCratorTest.class.getResource("/").getPath();
    path = path.substring(0, path.lastIndexOf("/creator/target/"));
    outputPath = path + "/plugin/atomikos";
    logger.info("outputPath={}", outputPath);
  }

  @Test
  public void testCreate() {
    reversService.create(outputPath, packageName + ".ds1", "hualuomoli", "t_user", "User", new Resolver() {

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

    reversService.create(outputPath, packageName + ".ds2", "hualuomoli", "t_address", "Address", null);

    logger.info("revers created.");
  }

}
