package com.github.hualuomoli.sample.framework.biz.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "com.github.hualuomoli.sample.framework.biz.error.controller.ErrorController")
@RequestMapping(value = "/error")
public class ErrorController {

  // http://localhost/error/unsupport
  @RequestMapping(value = "/unsupport", method = RequestMethod.POST)
  public void unsupport() {
  }

  // http://localhost/error/nullPointer
  @RequestMapping(value = "/nullPointer")
  public void nullPointer() {
    throw new NullPointerException("空指针异常");
  }

  // http://localhost/error/runtime
  @RequestMapping(value = "/runtime")
  public void runtime() {
    throw new RuntimeException("业务处理失败");
  }

  // http://localhost/error/exception
  @RequestMapping(value = "/exception")
  public void exception() throws Exception {
    throw new Exception("系统错误");
  }

}
