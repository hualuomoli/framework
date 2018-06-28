package com.github.hualuomoli.sample.dubbo.provider.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.hualuomoli.sample.dubbo.api.entity.User;
import com.github.hualuomoli.sample.dubbo.api.service.UseCommonService;
import com.github.hualuomoli.sample.dubbo.provider.user.service.UserBaseService;

//@Service(interfaceClass = UseCommonService.class)
@Service
public class UseCommonServiceImpl implements UseCommonService {

  @Autowired
  private UserBaseService userBaseService;

  @Override
  public User get(String username) {
    return userBaseService.get(username);
  }

  @Override
  public String create(User user) {
    return userBaseService.create(user);
  }

  @Override
  public boolean login(String username, String password) {
    return userBaseService.login(username, password);
  }

  @Override
  public Object[] result() {
    return userBaseService.result();
  }

}
