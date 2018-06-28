package com.github.hualuomoli.sample.gateway.server.biz.version.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.hualuomoli.framework.mvc.version.annotation.ApiVersion;

@Controller(value = "com.github.hualuomoli.sample.gateway.server.biz.version.controller.VersionController")
@RequestMapping(value = "/version")
@ApiVersion(value = "0.0.1")
public class VersionController {

  @RequestMapping(value = "/find")
  @ResponseBody
  public String find_old() {
    return "0.0.1";
  }

  @RequestMapping(value = "/find")
  @ResponseBody
  @ApiVersion(value = "1.0.0")
  public String find() {
    return "1.0.0";
  }

  @RequestMapping(value = "/find")
  @ResponseBody
  @ApiVersion(value = "1.0.1")
  public String find_new() {
    return "1.0.1";
  }

}
