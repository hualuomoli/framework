package com.github.hualuomoli.tool.http;

import java.util.Arrays;

public class Header {

  /** 名称 */
  String name;
  /** 值 */
  String[] value;

  public Header(String name, String value) {
    this.name = name;
    this.value = new String[] { value };
  }

  public Header(String name, String... values) {
    this.name = name;
    this.value = values;
  }

  public String getName() {
    return name;
  }

  public String[] getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Header [name=" + name + ", value=" + Arrays.toString(value) + "]";
  }

}
