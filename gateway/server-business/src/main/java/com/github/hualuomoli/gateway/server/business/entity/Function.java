package com.github.hualuomoli.gateway.server.business.entity;

import java.lang.reflect.Method;

/**
 * 功能
 */
public class Function {

  /** 支持请求的URL */
  private String url;
  /** 方法 */
  private Method method;
  /** 处理 */
  private Class<?> clazz;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }

  @Override
  public String toString() {
    return "Function [url=" + url + ", method=" + method + ", clazz=" + clazz + "]";
  }

}
