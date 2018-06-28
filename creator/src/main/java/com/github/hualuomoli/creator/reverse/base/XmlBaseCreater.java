package com.github.hualuomoli.creator.reverse.base;

import java.util.List;

import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;
import com.github.hualuomoli.creator.reverse.component.entity.JavaUniqueIndex;

/**
 * XML生成器
 */
public interface XmlBaseCreater {

  /**
   * 生成mapper的xml
   * @param outputProjectPath 输出项目目录
   * @param rootPackageName 跟包名
   * @param entityName 实体类名称
   * @param javaColumns java列
   * @param uniques 唯一索引
   * @param tableName 表名
   */
  void create(String outputProjectPath, String rootPackageName, String entityName//
      , List<JavaColumn> javaColumns, List<JavaUniqueIndex> uniques//
      , String tableName);

}
