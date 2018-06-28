package com.github.hualuomoli.sample.dubbo.api.service;

import com.github.hualuomoli.sample.dubbo.api.entity.User;

/**
 * 使用同一个接口
 */
public interface UseCommonService {

  // 入参简单类型,出参复杂类型
  User get(String username);

  // 入参复杂类型,出参简单类型
  String create(User user);

  // 多参数入参
  boolean login(String username, String password);

  // 返回数组
  Object[] result();

}
