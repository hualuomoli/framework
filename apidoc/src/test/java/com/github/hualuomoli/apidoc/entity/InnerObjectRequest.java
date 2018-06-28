package com.github.hualuomoli.apidoc.entity;

import java.util.Arrays;
import java.util.List;

/**
 * 这是一个包含层级的请求信息
 * @module sample.inner
 * @title 包含层级API
 * @method api.innerObject
 * @error NO_USER|用户不存在|确定用户信息后重试
 * @error NO_MORE_MONEY|余额不足|
 * @error INVALID_PASSWORD|密码错误|重试
 */
public class InnerObjectRequest {

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

  // 用户
  public static class User {
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

    @Override
    public String toString() {
      return "User [username=" + username + ", nickname=" + nickname + ", email=" + email + ", phone=" + phone + "]";
    }
  } // end user

  // 地址
  public static class Address {
    /**
     * 省
     * @required
     * @maxLength 12
     * @sample 山东省
     */
    private String province;
    /**
     * 市
     * @required
     * @maxLength 12
     * @sample 青岛市
     */
    private String city;
    /**
     * 区
     * @required
     * @maxLength 12
     * @sample 市北区
     */
    private String county;
    /**
     * 详细地址
     * @required
     * @maxLength 50
     * @sample 合肥路666号6号楼6层107室
     */
    private String detailAddress;

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

    public String getDetailAddress() {
      return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
      this.detailAddress = detailAddress;
    }

    @Override
    public String toString() {
      return "Address [province=" + province + ", city=" + city + ", county=" + county + ", detailAddress=" + detailAddress + "]";
    }
  } // end address

  // 性别
  public static enum GenderEnum {
    /**
     * 男
     */
    MALE,
    /**
     * 女
     */
    FEMALE
  }

  // 用户类型
  public static enum UserTypeEnum {

    /**
     * 管理端
     */
    MANAGER,
    /**
     * 系统管理员
     */
    SYSTEM;

  }

  @Override
  public String toString() {
    return "InnerObjectRequest [username=" + username + ", age=" + age + ", addresses=" + addresses + ", info=" + info + ", gender=" + gender + ", userType=" + Arrays.toString(userType) + "]";
  }

}
