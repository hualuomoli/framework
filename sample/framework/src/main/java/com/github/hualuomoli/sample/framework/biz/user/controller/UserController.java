package com.github.hualuomoli.sample.framework.biz.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.hualuomoli.sample.framework.base.entity.User;
import com.github.hualuomoli.sample.framework.base.service.UserBaseService;

/**
 * 用户,测试spring mvc的基础使用
 */
@RequestMapping(value = "/user")
@Controller(value = "com.github.hualuomoli.sample.framework.biz.user.controller.UserController")
public class UserController {

  @Autowired
  private UserBaseService userBaseService;

  // http://localhost/user/json?username=hualuomoil&nickname=花落寞离
  @RequestMapping(value = "/json")
  @ResponseBody
  public User json(User user, HttpServletRequest req) {
    return user;
  }

  // http://localhost/user/view
  @RequestMapping(value = "/view")
  public String view() {
    return "show";
  }

  // http://localhost/user/find?username=hualuomoil
  @RequestMapping(value = "/find")
  @ResponseBody
  public User find(User user, HttpServletRequest req) {
    User u = new User();
    u.setUsername(user.getUsername());
    List<User> list = userBaseService.findList(u);

    if (list != null && list.size() > 0) {
      u = list.get(0);
    }
    return u;
  }

}
