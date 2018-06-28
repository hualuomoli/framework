package com.github.hualuomoli.gateway.client.entity;

/**
 * 请求
 */
public interface Request {

  /**
   * 设置合作伙伴ID
   * @param partnerId 合作伙伴ID
   */
  void setPartnerId(String partnerId);

  /**
   * 设置请求方法
   * @param method 请求方法
   */
  void setMethod(String method);

  /**
   * 设置请求业务内容
   * @param bizContent 请求业务内容
   */
  void setBizContent(String bizContent);

}
