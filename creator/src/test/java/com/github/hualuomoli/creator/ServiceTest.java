package com.github.hualuomoli.creator;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.github.hualuomoli.config.BaseConfig;
import com.github.hualuomoli.util.ProjectConfig;

@WebAppConfiguration
@ContextConfiguration(classes = { BaseConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

  protected static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);

  protected static final String DATABASE_NAME = "hualuomoli";
  protected static final String TABLE_NAME = "t_user";
  protected static final String ENTITY_NAME = "User";

  protected static String outputPath = "E:/demo";
  protected static String packageName = "com.github.hualuomoli.base";

  @Autowired
  protected WebApplicationContext wac;

  @BeforeClass
  public static void beforeClass() {
    ProjectConfig.init("classpath*:configs/jdbc.properties");

    // 输出目录 --> base
    if (StringUtils.isBlank(outputPath)) {
      outputPath = ServiceTest.class.getResource("/").getPath();
      outputPath = outputPath.substring(0, outputPath.lastIndexOf("/target/"));
      outputPath += "/../base";
    }
    logger.info("outputPath={}", outputPath);
  }

  @Test
  public void test() {
    logger.info("wac={}", wac);
  }

}
