package com.github.hualuomoli.sample.framework.biz.def.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import com.github.hualuomoli.framework.mvc.util.servlet.ContextUtils;
import com.github.hualuomoli.framework.mvc.util.servlet.ServletUtils;

/**
 * 默认
 */
@Controller(value = "com.github.hualuomoli.sample.framework.biz.controller.DefaultController")
@RequestMapping(value = "/")
public class DefaultController {

  private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

  // 跟请求
  @RequestMapping(value = "")
  public String def(HttpServletRequest req, HttpServletResponse res) {

    HttpServletRequest request = ServletUtils.getRequest();
    HttpServletResponse response = ServletUtils.getResponse();
    ServletContext servletContext = ServletUtils.getServletContext();
    WebApplicationContext applicationContext = ContextUtils.getContext();

    logger.debug("req={}", req);
    logger.debug("res={}", res);
    logger.debug("request={}", request);
    logger.debug("response={}", response);
    logger.debug("servletContext={}", servletContext);
    logger.debug("applicationContext={}", applicationContext);
    logger.info("same request={}", (req == request));
    logger.info("same response={}", (res == response));

    return "index";
  }

  // 错误
  @RequestMapping(value = "/error")
  public String error(HttpServletRequest req, HttpServletResponse res) {
    throw new IllegalArgumentException("请求错误");
  }

}
