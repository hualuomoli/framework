package com.github.hualuomoli.sample.framework.base.entity;

// 用户
public class User {

  /** id */
  private java.lang.String id;
  /** 用户名 */
  private java.lang.String username;
  /** 昵称 */
  private java.lang.String nickname;
  /** 年龄 */
  private java.lang.Integer age;
  /** 性别 */
  private java.lang.String sex;
  /** 数据状态1=正常 */
  private com.github.hualuomoli.sample.enums.StateEnum state;
  /** 数据状态 */
  private com.github.hualuomoli.sample.enums.StatusEnum status;
  /** 描述信息 */
  private java.lang.String remark;

  public User() {
  }

  // getter and setter

  public java.lang.String getId() {
    return this.id;
  }

  public void setId(java.lang.String id) {
    this.id = id;
  }

  public java.lang.String getUsername() {
    return this.username;
  }

  public void setUsername(java.lang.String username) {
    this.username = username;
  }

  public java.lang.String getNickname() {
    return this.nickname;
  }

  public void setNickname(java.lang.String nickname) {
    this.nickname = nickname;
  }

  public java.lang.Integer getAge() {
    return this.age;
  }

  public void setAge(java.lang.Integer age) {
    this.age = age;
  }

  public java.lang.String getSex() {
    return this.sex;
  }

  public void setSex(java.lang.String sex) {
    this.sex = sex;
  }

  public com.github.hualuomoli.sample.enums.StateEnum getState() {
    return this.state;
  }

  public void setState(com.github.hualuomoli.sample.enums.StateEnum state) {
    this.state = state;
  }

  public com.github.hualuomoli.sample.enums.StatusEnum getStatus() {
    return this.status;
  }

  public void setStatus(com.github.hualuomoli.sample.enums.StatusEnum status) {
    this.status = status;
  }

  public java.lang.String getRemark() {
    return this.remark;
  }

  public void setRemark(java.lang.String remark) {
    this.remark = remark;
  }

  @Override
  public String toString() {
    return "User [" //
        + "id=" + id //
        + ", username=" + username //
        + ", nickname=" + nickname //
        + ", age=" + age //
        + ", sex=" + sex //
        + ", state=" + state //
        + ", status=" + status //
        + ", remark=" + remark //
        + "]";
  }

}