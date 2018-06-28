package com.github.hualuomoli.apidoc.entity;

public class SimpleResponse {

  /**
   * 用户名
   * @required
   * @maxLength 20
   * @sample hualuomoli
   */
  private String username;

  /**
   * 有效时长,单位为m(分钟)
   * @required
   * @sample 10
   */
  private Long validateTime;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Long getValidateTime() {
    return validateTime;
  }

  public void setValidateTime(Long validateTime) {
    this.validateTime = validateTime;
  }

  @Override
  public String toString() {
    return "SimpleResponse [username=" + username + ", validateTime=" + validateTime + "]";
  }

}
