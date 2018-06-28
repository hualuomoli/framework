package com.github.hualuomoli.apidoc.entity.inner;

public class User {

  /**
   * 用户名
   * @sample hualuomoli
   */
  private String username;
  /**
  * 昵称
  * @sample 花落莫离
  */
  private String nickname;
  /**
  * 邮箱
  * @sample 123456@163.com
  */
  private String email;
  /**
  * 手机号
  * @sample 15688886666
  */
  private String phone;
  /**
   * 性别
   * @sample 男
   */
  private String sex;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  @Override
  public String toString() {
    return "User [username=" + username + ", nickname=" + nickname + ", email=" + email + ", phone=" + phone + ", sex=" + sex + "]";
  }

}
