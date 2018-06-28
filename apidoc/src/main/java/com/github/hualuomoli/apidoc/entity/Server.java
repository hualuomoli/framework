package com.github.hualuomoli.apidoc.entity;

/**
 * 服务器信息
 */
public class Server {

  private String name;
  private String url;

  public Server() {
  }

  public Server(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
