package com.github.hualuomoli.sample.gateway.server.biz.version.controller;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.hualuomoli.gateway.client.entity.HttpHeader;
import com.github.hualuomoli.gateway.client.lang.ClientException;
import com.github.hualuomoli.sample.gateway.server.ClientControllerTest;

/**
 * 同一URL不同的版本
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiVersionGatewayTest extends ClientControllerTest {

  // 低于最低版本
  @Test(expected = ClientException.class)
  public void test01LessFirst() {
    this.execute("0.0.0.1");
  }

  // 等于第一个版本
  @Test
  public void test02EqualFirst() {
    String result = this.execute("0.0.1");
    Assert.assertEquals("0.0.1", result);
  }

  // 高于最低版本,比第二个版本低
  @Test
  public void test03GreaterFirstLessSecond() {
    String result = this.execute("0.9.0");
    Assert.assertEquals("0.0.1", result);
  }

  // 等于第二个版本
  @Test
  public void test04EqualSecond() {
    String result = this.execute("1.0.0");
    Assert.assertEquals("1.0.0", result);
  }

  // 高于第一个、第二个版本,低于第三个版本
  @Test
  public void test05GreaterSecondLessThird() {
    String result = this.execute("1.0.0.1");
    Assert.assertEquals("1.0.0", result);
  }

  // 等于第三个版本
  @Test
  public void test06EqualsThird() {
    String result = this.execute("1.0.1");
    Assert.assertEquals("1.0.1", result);
  }

  // 高于所有版本
  @Test
  public void test07GreaterThird() {
    String result = this.execute("5.0");
    Assert.assertEquals("1.0.1", result);
  }

  // 没有指定版本号
  @Test
  public void test08NoVersion() {
    String result = this.execute(null);
    Assert.assertEquals("1.0.1", result);
  }

  // 指定版本号为空值
  @Test
  public void test09EmptyVersion() {
    String result = this.execute("");
    Assert.assertEquals("1.0.1", result);
  }

  // 执行
  private String execute(String apiVersion) {
    client.addRequestHeader(new HttpHeader("api-version", apiVersion));
    return client.execute("version.find", "");
  }

}
