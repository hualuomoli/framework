package com.github.hualuomoli.gateway.client.entity;

/**
 * 响应
 */
public interface Response {

  /**
   * 是否执行成功
   * @return 执行成功
   */
  boolean success();

  /**
   * 网关调用是否执行成功
   * @return 网关调用是否执行成功
   */
  boolean callSuccess();

  /**
   * 获取返回结果
   * @return 返回结果
   */
  String getResult();

}
