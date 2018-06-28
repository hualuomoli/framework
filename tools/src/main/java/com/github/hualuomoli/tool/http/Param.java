package com.github.hualuomoli.tool.http;

public class Param {

  String name;
  String value;

  public Param(String name, String value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String toString() {
    return "Param [name=" + name + ", value=" + value + "]";
  }

}
