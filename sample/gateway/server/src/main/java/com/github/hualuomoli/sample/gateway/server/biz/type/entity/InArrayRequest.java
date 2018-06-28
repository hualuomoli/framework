package com.github.hualuomoli.sample.gateway.server.biz.type.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.github.hualuomoli.sample.gateway.server.anno.ApiMethod;

/**
 * 输入array
 */
@ApiMethod(value = "type.inArray")
public class InArrayRequest {

  @NotBlank
  private String username;
  @NotBlank
  private String nickname;
  @NotNull
  @Min(value = 18)
  private Integer age;

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

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "InObjectRequest [username=" + username + ", nickname=" + nickname + ", age=" + age + "]";
  }
}
