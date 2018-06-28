package com.github.hualuomoli.tool.http.parser;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.github.hualuomoli.tool.http.Param;

public interface Parser {

  /**
   * 解析参数为表单提交参数
   * @param object
   * @return 参数集合
   */
  List<Param> parse(Object object);

  /**
   * 解析参数为表单提交参数
   * @param object
   * @param formater 格式化
   * @return 参数集合
   */
  List<Param> parse(Object object, Formater formater);

  interface Formater {

    /**
     * 格式化日期
     * @param date 日期
     * @param field 实体类属性
     * @param handler 实体类属性
     * @return 日期字符串
     */
    String format(Date date, Field field, Object handler);

  }

}
