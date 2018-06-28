package com.github.hualuomoli.apidoc.entity;

import java.util.Arrays;
import java.util.List;

import com.github.hualuomoli.apidoc.entity.inner.Address;
import com.github.hualuomoli.apidoc.entity.inner.GenderEnum;
import com.github.hualuomoli.apidoc.entity.inner.User;
import com.github.hualuomoli.apidoc.entity.inner.UserTypeEnum;

/**
 * 这是一个包含外部属性层级的请求信息
 * @module sample.outer
 * @title 包含外部属性API
 * @method api.outerObject
 */
public class OuterObjectRequest {

  /**
   * 用户名
   * @required
   * @maxLength 20
   * @sample hualuomoli
   */
  private String username;

  /**
   * 年龄
   * @required
   * @sample 18
   */
  private Integer age;
  /**
   * 地址
   * @required
   */
  private List<Address> addresses;
  /**
   * 用户信息
   * @required
   */
  private User info;
  /**
   * 性别
   */
  private GenderEnum gender;
  /**
   * 用户类型
   */
  private UserTypeEnum[] userType;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public User getInfo() {
    return info;
  }

  public void setInfo(User info) {
    this.info = info;
  }

  public GenderEnum getGender() {
    return gender;
  }

  public void setGender(GenderEnum gender) {
    this.gender = gender;
  }

  public UserTypeEnum[] getUserType() {
    return userType;
  }

  public void setUserType(UserTypeEnum[] userType) {
    this.userType = userType;
  }

  @Override
  public String toString() {
    return "OuterObjectRequest [username=" + username + ", age=" + age + ", addresses=" + addresses + ", info=" + info + ", gender=" + gender + ", userType=" + Arrays.toString(userType) + "]";
  }

}
