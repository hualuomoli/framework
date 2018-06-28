package com.github.hualuomoli.sample.dubbo.api.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class User implements Serializable {

  private static final long serialVersionUID = 3529632937942382326L;

  private String username;
  private String nickname;
  private String password;
  private List<String> hobbys;
  private Set<Address> addresses;

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<String> getHobbys() {
    return hobbys;
  }

  public void setHobbys(List<String> hobbys) {
    this.hobbys = hobbys;
  }

  public Set<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
  }

  @Override
  public String toString() {
    return "User [username=" + username + ", nickname=" + nickname + ", password=" + password + ", hobbys=" + hobbys + ", addresses=" + addresses + "]";
  }

}
