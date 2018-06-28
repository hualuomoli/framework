package com.github.hualuomoli.sample.plugin.ws.api.entity;

public class User {

  private String username;
  private String nickname;

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

  @Override
  public String toString() {
    return "User [username=" + username + ", nickname=" + nickname + "]";
  }

}
