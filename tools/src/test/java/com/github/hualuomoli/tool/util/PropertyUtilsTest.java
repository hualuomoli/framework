package com.github.hualuomoli.tool.util;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class PropertyUtilsTest {

  @Test
  public void testForCover() {
    Properties prop = PropertyUtils.loadCover("config2.properties", "config3.properties");

    String name = prop.getProperty("project.name");
    String version = prop.getProperty("project.version");
    Assert.assertNotNull(name);
    Assert.assertEquals("hualuomoli", name);
    Assert.assertNotNull(version);
    Assert.assertEquals("1.5.0", version);
  }

  @Test
  public void testForCoverReverse() {
    Properties prop = PropertyUtils.loadCover("config3.properties", "config2.properties");

    String name = prop.getProperty("project.name");
    String version = prop.getProperty("project.version");
    Assert.assertNotNull(name);
    Assert.assertEquals("hualuomoli", name);
    Assert.assertNotNull(version);
    Assert.assertEquals("2.0.0", version);
  }

  @Test
  public void test03AddResource() {
    Properties prop = PropertyUtils.loadCover("config2.properties", "config3.properties");

    String name = prop.getProperty("project.name");
    String version = prop.getProperty("project.version");
    Assert.assertNotNull(name);
    Assert.assertEquals("hualuomoli", name);
    Assert.assertNotNull(version);
    Assert.assertEquals("1.5.0", version);

    // add
    Properties p = PropertyUtils.load("config4.properties");
    PropertyUtils.copy(p, prop);
    name = prop.getProperty("project.name");
    version = prop.getProperty("project.version");
    Assert.assertNotNull(name);
    Assert.assertEquals("abcd", name);
    Assert.assertNotNull(version);
    Assert.assertEquals("1.0.0", version);
  }

  @Test
  @Ignore // add libs to configure
  public void testLoadFromJarFile() {

    Properties prop = PropertyUtils.loadCover("classpath*:config.properties", "classpath*:config1.properties");

    String name = prop.getProperty("project.name");
    String version = prop.getProperty("project.version");
    String type = prop.getProperty("project.type");
    Assert.assertNotNull(name);
    Assert.assertEquals("hualuomoli", name);
    Assert.assertNotNull(version);
    Assert.assertEquals("1.0.0", version);
    Assert.assertNotNull(type);
    Assert.assertEquals("war", type);
  }

}
