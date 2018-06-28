package com.github.hualuomoli.sample.gateway.server.biz.type.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.hualuomoli.gateway.client.entity.Page;
import com.github.hualuomoli.sample.gateway.server.ClientControllerTest;
import com.github.hualuomoli.sample.gateway.server.biz.gateway.enums.StatusEnum;
import com.github.hualuomoli.sample.gateway.server.biz.type.entity.InArrayRequest;
import com.github.hualuomoli.sample.gateway.server.biz.type.entity.InArrayResponse;
import com.github.hualuomoli.sample.gateway.server.biz.type.entity.InObjectRequest;
import com.github.hualuomoli.sample.gateway.server.biz.type.entity.InObjectResponse;
import com.github.hualuomoli.sample.gateway.server.biz.type.entity.OutArrayRequest;
import com.github.hualuomoli.sample.gateway.server.biz.type.entity.OutArrayResponse;
import com.github.hualuomoli.sample.gateway.server.biz.type.entity.OutObjectRequest;
import com.github.hualuomoli.sample.gateway.server.biz.type.entity.OutObjectResponse;
import com.github.hualuomoli.sample.gateway.server.biz.type.entity.OutPageRequest;
import com.github.hualuomoli.sample.gateway.server.biz.type.entity.OutPageResponse;
import com.google.common.collect.Lists;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TypeControllerObjectClientTest extends ClientControllerTest {

  // 请求object
  @Test
  public void test01RequestObject() {
    InObjectRequest req = new InObjectRequest();
    req.setUsername("hualuomoli");
    req.setNickname("花落寞离");
    req.setAge(18);

    InObjectResponse res = client.callObject(req, InObjectResponse.class);
    Assert.assertEquals(StatusEnum.SUCCESS, res.getStatus());
  }

  // 请求list
  @Test
  public void test02RequestArray() {
    List<InArrayRequest> list = Lists.newArrayList();

    InArrayRequest req1 = new InArrayRequest();
    req1.setUsername("hualuomoli");
    req1.setNickname("花落寞离");
    req1.setAge(18);

    InArrayRequest req2 = new InArrayRequest();
    req2.setUsername("tester");
    req2.setNickname("测试");
    req2.setAge(20);

    list.add(req1);
    list.add(req2);

    InArrayResponse res = client.callObject(list, InArrayResponse.class);
    Assert.assertEquals(StatusEnum.SUCCESS, res.getStatus());
  }

  // 返回object
  @Test
  public void test03ResponseObject() {
    OutObjectRequest req = new OutObjectRequest();

    OutObjectResponse res = client.callObject(req, OutObjectResponse.class);
    Assert.assertEquals("hualuomoli", res.getUsername());
    Assert.assertEquals("花落寞离", res.getNickname());
    Assert.assertEquals(18, res.getAge().intValue());
  }

  // 返回list
  @Test
  public void test04ResponseArray() {
    OutArrayRequest req = new OutArrayRequest();
    List<OutArrayResponse> list = client.callArray(req, OutArrayResponse.class);
    Assert.assertEquals(2, list.size());

    Assert.assertEquals("hualuomoli", list.get(0).getUsername());
    Assert.assertEquals("花落寞离", list.get(0).getNickname());
    Assert.assertEquals(18, list.get(0).getAge().intValue());

    Assert.assertEquals("tester", list.get(1).getUsername());
    Assert.assertEquals("测试", list.get(1).getNickname());
    Assert.assertEquals(20, list.get(1).getAge().intValue());

  }

  // 返回page
  @Test
  public void test05ResponsePage() {
    OutPageRequest req = new OutPageRequest();

    Page<OutPageResponse> page = client.callPage(req, OutPageResponse.class);
    Assert.assertEquals(1, page.getPageNumber().intValue());
    Assert.assertEquals(10, page.getPageSize().intValue());
    Assert.assertEquals(3, page.getCount().intValue());

    List<OutPageResponse> list = page.getDataList();
    Assert.assertEquals(3, list.size());

    Assert.assertEquals("hualuomoli", list.get(0).getUsername());
    Assert.assertEquals("花落寞离", list.get(0).getNickname());
    Assert.assertEquals(18, list.get(0).getAge().intValue());

    Assert.assertEquals("tester", list.get(1).getUsername());
    Assert.assertEquals("测试", list.get(1).getNickname());
    Assert.assertEquals(20, list.get(1).getAge().intValue());

    Assert.assertEquals("jack", list.get(2).getUsername());
    Assert.assertEquals("杰克", list.get(2).getNickname());
    Assert.assertEquals(26, list.get(2).getAge().intValue());
  }

}
