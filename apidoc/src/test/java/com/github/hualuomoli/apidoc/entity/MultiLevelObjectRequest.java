package com.github.hualuomoli.apidoc.entity;

import java.util.Arrays;
import java.util.List;

/**
 * 多层级请求
 * @module sample.multi
 * @method multi.level
 * @title 多层级请求
 */
public class MultiLevelObjectRequest {

  /**
   * 用户名
   */
  private String username;
  /**
   * 用户信息
   */
  private User user;
  /**
   * 年龄
   */
  private Integer age;
  /**
   * 性别
   */
  private String sex;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  // 用户
  public static class User {
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 住址
     */
    private List<Address> addresses;

    /**
     * 爱好
     */
    private String[] hobby;
    /**
     * 邮箱
     */
    private String email;

    public String getNickname() {
      return nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }

    public List<Address> getAddresses() {
      return addresses;
    }

    public void setAddresses(List<Address> addresses) {
      this.addresses = addresses;
    }

    public String[] getHobby() {
      return hobby;
    }

    public void setHobby(String[] hobby) {
      this.hobby = hobby;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    @Override
    public String toString() {
      return "User [nickname=" + nickname + ", addresses=" + addresses + ", hobby=" + Arrays.toString(hobby) + ", email=" + email + "]";
    }

  }

  public static class Address {
    /***
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String county;

    public String getProvince() {
      return province;
    }

    public void setProvince(String province) {
      this.province = province;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getCounty() {
      return county;
    }

    public void setCounty(String county) {
      this.county = county;
    }

  }

  @Override
  public String toString() {
    return "MultiLevelObjectRequest [username=" + username + ", user=" + user + ", age=" + age + ", sex=" + sex + "]";
  }

}
