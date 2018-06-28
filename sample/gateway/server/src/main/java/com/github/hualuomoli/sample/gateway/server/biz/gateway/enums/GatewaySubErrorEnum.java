package com.github.hualuomoli.sample.gateway.server.biz.gateway.enums;

/**
 * 网关业务处理错误枚举
 */
public enum GatewaySubErrorEnum {

  /**
   * 权限不合法
   */
  INVALID_AUTHORITY("权限不合法", "9998"),
  /**
   * 参数不合法
   */
  INVALID_PARAMETER("参数不合法", "9997"),
  /**
   * 系统错误
   */
  SYSTEM("系统错误", "9999");

  private String message;
  private String errorCode;

  private GatewaySubErrorEnum(String message, String errorCode) {
    this.message = message;
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public String getErrorCode() {
    return errorCode;
  }

}
