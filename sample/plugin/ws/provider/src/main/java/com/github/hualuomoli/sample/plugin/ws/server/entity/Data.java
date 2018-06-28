package com.github.hualuomoli.sample.plugin.ws.server.entity;

public class Data {

  private String username;
  private Integer age;

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

  @Override
  public String toString() {
    return "Data [username=" + username + ", age=" + age + "]";
  }

}
