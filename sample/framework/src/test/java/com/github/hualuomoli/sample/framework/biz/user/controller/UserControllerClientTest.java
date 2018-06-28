package com.github.hualuomoli.sample.framework.biz.user.controller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.tool.http.HttpDefaultClient;
import com.github.hualuomoli.tool.http.Param;
import com.google.common.collect.Lists;

public class UserControllerClientTest {

  private static final Logger logger = LoggerFactory.getLogger(UserControllerClientTest.class);

  @Test
  public void testJson() throws Exception {
    String result = new HttpDefaultClient("http://localhost/user/json").urlencoded(Lists.newArrayList(new Param("id", "1234")));
    logger.info("[testJson] result={}", result);
  }

  @Test
  public void testView() throws Exception {
    String result = new HttpDefaultClient("http://localhost/user/view").urlencoded(null);
    logger.info("[testView] result={}", result);
  }

  @Test
  public void testFind() throws Exception {
    String result = new HttpDefaultClient("http://localhost/user/find").urlencoded(Lists.newArrayList(new Param("username", "hualuomoli")));
    logger.info("[testFind] result={}", result);
  }

}
