package com.github.hualuomoli.gateway.client.parser;

public interface GenericParser {

  /**
   * 获取请求实体类的方法
   * @param object 请求实体类
   * @return 方法
   */
  String getMethod(Object object);

  /**
   * 分页名称信息
   * @return 分页名称
   */
  PageName getPageName();

  // 分页名称
  interface PageName {

    String pageNumber();

    String pageSize();

    String count();

    String datas();

  }

}
