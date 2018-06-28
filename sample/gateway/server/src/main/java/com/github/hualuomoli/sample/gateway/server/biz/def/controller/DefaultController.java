package com.github.hualuomoli.sample.gateway.server.biz.def.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(value = "com.github.hualuomoli.sample.gateway.server.biz.def.controller.DefaultController")
@RequestMapping(value = "/")
public class DefaultController {

  @RequestMapping(value = "")
  @ResponseBody
  public String def(HttpServletRequest req, HttpServletResponse res) {
    return "this is gateway index";
  }

}
