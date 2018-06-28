package com.github.hualuomoli.tool.util;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvUtilsTest {

  private static final Logger logger = LoggerFactory.getLogger(EnvUtils.class);

  @BeforeClass
  public static void beforeClass() {
    System.setProperty("environment", EnvUtils.Env.TEST.name());
    EnvUtils.init("environment");
  }

  @Test
  public void testParse() {
    String[] resources = null;

    // logs/log4j.properties
    resources = EnvUtils.parse("logs/log4j.properties");
    Assert.assertEquals("logs/log4j.properties", resources[0]);
    Assert.assertEquals("logs/log4j-test.properties", resources[1]);
    Assert.assertEquals("logs-test/log4j.properties", resources[2]);
    Assert.assertEquals("logs/test/log4j.properties", resources[3]);

    // /logs/log4j.properties
    resources = EnvUtils.parse("/logs/log4j.properties");
    Assert.assertEquals("/logs/log4j.properties", resources[0]);
    Assert.assertEquals("/logs/log4j-test.properties", resources[1]);
    Assert.assertEquals("/logs-test/log4j.properties", resources[2]);
    Assert.assertEquals("/logs/test/log4j.properties", resources[3]);

    // log4j.properties
    resources = EnvUtils.parse("log4j.properties");
    Assert.assertEquals("log4j.properties", resources[0]);
    Assert.assertEquals("log4j-test.properties", resources[1]);

    // /log4j.properties
    resources = EnvUtils.parse("/log4j.properties");
    Assert.assertEquals("/log4j.properties", resources[0]);
    Assert.assertEquals("/log4j-test.properties", resources[1]);

    // logs/log4j
    resources = EnvUtils.parse("logs/log4j");
    Assert.assertEquals("logs/log4j", resources[0]);
    Assert.assertEquals("logs-test/log4j", resources[1]);
    Assert.assertEquals("logs/test/log4j", resources[2]);

    // log4j
    resources = EnvUtils.parse("log4j");
    Assert.assertEquals("log4j", resources[0]);

  }

  @Test
  public void testGetEnv() {
    logger.debug("env={}", EnvUtils.getEnv());
  }

}
