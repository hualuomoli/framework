package com.github.hualuomoli.sample.framework.biz.version.controller;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.github.hualuomoli.tool.http.Header;
import com.github.hualuomoli.tool.http.HttpDefaultClient;
import com.google.common.collect.Lists;

public class VersionControllerClientTest {

  // 0.0.1 --> 1.0.0 --> 1.0.1

  @Test
  public void testFind_old() throws IOException {
    String result = new HttpDefaultClient("http://localhost/version/find").urlencoded("", Lists.newArrayList(new Header("api-version", "0.0.1")), null);
    Assert.assertEquals("0.0.1", result);
  }

  @Test
  public void testFind() throws IOException {
    String result = new HttpDefaultClient("http://localhost/version/find").urlencoded("", Lists.newArrayList(new Header("api-version", "1.0.0")), null);
    Assert.assertEquals("1.0.0", result);
  }

  @Test
  public void testFind_new() throws IOException {
    String result = new HttpDefaultClient("http://localhost/version/find").urlencoded("", Lists.newArrayList(new Header("api-version", "1.0.1")), null);
    Assert.assertEquals("1.0.1", result);
  }

  @Test(expected = IOException.class)
  public void testFindLessMinVersion() throws IOException {
    new HttpDefaultClient("http://localhost/version/find").urlencoded("", Lists.newArrayList(new Header("api-version", "0.0.0.6")), null);
  }

  @Test
  public void testFindBetweenVersion() throws IOException {
    String result = new HttpDefaultClient("http://localhost/version/find").urlencoded("", Lists.newArrayList(new Header("api-version", "1.0.0.6")), null);
    Assert.assertEquals("1.0.0", result);
  }

  @Test
  public void testFindGreaterMaxVersion() throws IOException {
    String result = new HttpDefaultClient("http://localhost/version/find").urlencoded("", Lists.newArrayList(new Header("api-version", "5.0")), null);
    Assert.assertEquals("1.0.1", result);
  }

  @Test
  public void testFindNoVersion() throws IOException {
    String result = new HttpDefaultClient("http://localhost/version/find").urlencoded("", null, null);
    Assert.assertEquals("1.0.1", result);
  }

  @Test
  public void testFindEmptyVersion() throws IOException {
    String result = new HttpDefaultClient("http://localhost/version/find").urlencoded("", Lists.newArrayList(new Header("api-version", "")), null);
    Assert.assertEquals("1.0.1", result);
  }

}
