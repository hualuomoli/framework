package com.github.hualuomoli.gateway.client.parser;

import java.util.List;
import java.util.Map;

/**
 * JSON转换器
 */
public interface JSONParser {

  /**
   * 数据转换成JSON字符串
   * @param object 数据
   * @return JSON字符串
   */
  String toJsonString(Object object);

  /**
   * JSON数据转换成map
   * @param content JSON数据
   * @return Map
   */
  Map<String, Object> parse(String content);

  /**
   * JSON数据转换成Object
   * @param content JSON数据
   * @param clazz 数据类型
   * @return 数据
   */
  <T> T parseObject(String content, Class<T> clazz);

  /**
   * JSON数据转换成List
   * @param content JSON数据
   * @param clazz 数据类型
   * @return 数据集合
   */
  <T> List<T> parseArray(String content, Class<T> clazz);

}
