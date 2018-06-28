package com.github.hualuomoli.creator.reverse.component.parser;

import java.util.List;

import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.entity.DBUniqueIndex;
import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;
import com.github.hualuomoli.creator.reverse.component.entity.JavaUniqueIndex;

/**
 * 转换器
 */
public interface Parser {

  /**
   * 转换数据库列为JAVA列
   * @param dbColumns 数据库列
   * @param primary 主键
   * @param resolver 自定义解析器
   * @return JAVA列
   */
  List<JavaColumn> parse(List<DBColumn> dbColumns, String primary, Resolver resolver);

  /**
   * 转换索引
   * @param javaColumns java列
   * @param uniques 唯一索引
   * @return java索引
   */
  List<JavaUniqueIndex> parse(List<JavaColumn> javaColumns, List<DBUniqueIndex> uniques);

  // 自定义解析器
  interface Resolver {

    /**
     * 解析java名称
     * @param javaType 默认判断的java类型
     * @param javaName java属性名称
     * @param dbName 数据库列名称
     * @return 解析后的java名称
     */
    String resolverJavaName(Class<?> javaType, String javaName, String dbName);

    /**
     * 解析java类型
     * @param javaType 默认判断的java类型
     * @param javaName java属性名称
     * @param dbName 数据库列名称
     * @return 解析后的java类型
     */
    Class<?> resolverJavaType(Class<?> javaType, String javaName, String dbName);

  }

}
