package com.github.hualuomoli.gateway.server.entity;

/**
 * 请求
 */
public interface Request {

  /**
   * 获取合作伙伴ID
   * @return 合作伙伴ID
   */
  String getPartnerId();

  /**
   * 获取请求方法
   * @return 请求方法
   */
  String getMethod();

  /**
   * 获取请求业务内容
   * @return 请求业务内容
   */
  String getBizContent();

}
