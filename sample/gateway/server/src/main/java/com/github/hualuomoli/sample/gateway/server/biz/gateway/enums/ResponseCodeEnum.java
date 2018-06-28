package com.github.hualuomoli.sample.gateway.server.biz.gateway.enums;

/**
 * 响应编码枚举
 */
public enum ResponseCodeEnum {

  /** 成功 */
  SUCCESS,
  /** 合作伙伴未找到 */
  NO_PARTNER,
  /** 安全认证失败 */
  INVALID_SECURITY,
  /**  请求方法未注册 */
  NO_ROUTER,
  /**  业务处理失败 */
  BUSINESS,
  /**  系统错误 */
  SYSTEM;

}
