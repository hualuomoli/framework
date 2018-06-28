package com.github.hualuomoli.apidoc.entity;

/**
 * 业务处理错误
 */
public class Error {

  /** 错误编码 */
  private String code;
  /** 错误描述 */
  private String message;
  /** 错误解决方案 */
  private String deal;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDeal() {
    return deal;
  }

  public void setDeal(String deal) {
    this.deal = deal;
  }

  @Override
  public String toString() {
    return "Error [code=" + code + ", message=" + message + ", deal=" + deal + "]";
  }

}
