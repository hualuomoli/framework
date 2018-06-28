package com.github.hualuomoli.framework.mvc.version.condition;

import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析器
 */
public interface Parser {

  /**
   * 获取HTTP的请求版本号
   * @param req HTTP请求
   * @return 请求版本号
   */
  String getVersion(HttpServletRequest req);

  /**
   * 是否支持
   * @param version 业务功能版本号
   * @param requestVersion 请求版本号
   * @return 是否支持
   */
  boolean support(String version, String requestVersion);

  /**
   * 版本比较
   * @param v1 版本1
   * @param v2 版本2
   * @return {@linkplain Comparator#compare(Object, Object) }
   */
  int compare(String v1, String v2);

}
