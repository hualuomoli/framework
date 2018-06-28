package com.github.hualuomoli.apidoc.entity;

import java.util.Date;

/**
 * 这是一个简单的请求信息,所有的参数都是简单的数据类型
 * @module sample.simple
 * @title 简单实用API
 * @method api.simple
 */
public class SimpleRequest {

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
   * 工资
   * @sample 2000
   */
  private Double salary;
  /**
   * 登录次数
   * @sample 1456987752
   */
  private Long mills;
  /**
   * 生日
   * @maxLength 10
   * @sample 1900-01-01
   */
  private Date birthday;

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

  public Double getSalary() {
    return salary;
  }

  public void setSalary(Double salary) {
    this.salary = salary;
  }

  public Long getMills() {
    return mills;
  }

  public void setMills(Long mills) {
    this.mills = mills;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  @Override
  public String toString() {
    return "SimpleRequest [username=" + username + ", age=" + age + ", salary=" + salary + ", mills=" + mills + ", birthday=" + birthday + "]";
  }

}
