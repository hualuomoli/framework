package com.github.hualuomoli.sample.gateway.server.biz.type.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.hualuomoli.sample.gateway.server.biz.gateway.entity.Page;
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
import com.google.common.collect.Lists;

/**
 * 请求数据类型
 */
@RequestMapping(value = "/type")
@Controller(value = "com.github.hualuomoli.sample.gateway.server.biz.type.controller.TypeController")
public class TypeController {

  private static final Logger logger = LoggerFactory.getLogger(TypeController.class);

  // 请求object
  @RequestMapping(value = "/inObject")
  @ResponseBody
  public InObjectResponse inObject(InObjectRequest req) {

    logger.debug("[inObject] req={}", req);

    InObjectResponse res = new InObjectResponse();
    res.setStatus(StatusEnum.SUCCESS);

    return res;
  }

  // 请求list
  @RequestMapping(value = "/inArray", method = RequestMethod.POST //
      , produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE })
  @ResponseBody
  public InArrayResponse inArray(@RequestBody List<InArrayRequest> list) {

    for (InArrayRequest req : list) {
      logger.debug("[inArray] req={}", req);
    }

    InArrayResponse res = new InArrayResponse();
    res.setStatus(StatusEnum.SUCCESS);

    return res;
  }

  // 返回object
  @RequestMapping(value = "/outObject")
  @ResponseBody
  public OutObjectResponse outObject(OutObjectRequest req) {
    OutObjectResponse res = new OutObjectResponse();
    res.setUsername("hualuomoli");
    res.setNickname("花落寞离");
    res.setAge(18);

    return res;
  }

  // 返回list
  @RequestMapping(value = "/outArray")
  public List<OutArrayResponse> outArray(OutArrayRequest req) {
    OutArrayResponse res1 = new OutArrayResponse();
    res1.setUsername("hualuomoli");
    res1.setNickname("花落寞离");
    res1.setAge(18);

    OutArrayResponse res2 = new OutArrayResponse();
    res2.setUsername("tester");
    res2.setNickname("测试");
    res2.setAge(20);

    return Lists.newArrayList(res1, res2);
  }

  // 返回page
  @RequestMapping(value = "/outPage")
  public Page outPage(OutPageRequest req) {
    OutArrayResponse res1 = new OutArrayResponse();
    res1.setUsername("hualuomoli");
    res1.setNickname("花落寞离");
    res1.setAge(18);

    OutArrayResponse res2 = new OutArrayResponse();
    res2.setUsername("tester");
    res2.setNickname("测试");
    res2.setAge(20);

    OutArrayResponse res3 = new OutArrayResponse();
    res3.setUsername("jack");
    res3.setNickname("杰克");
    res3.setAge(26);

    Page page = new Page();
    page.setPageNumber(1);
    page.setPageSize(10);
    page.setCount(3);
    page.setDataList(Lists.newArrayList(res1, res2, res3));

    return page;
  }

}
