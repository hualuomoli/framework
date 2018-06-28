package com.github.hualuomoli.creator.reverse.query;

import java.util.List;

import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;

/**
 * 实体类生成器
 */
public interface EntityQueryCreater {

  /**
   * 生成
   * @param outputProjectPath 输出项目目录
   * @param rootPackageName 跟包名
   * @param entityName 实体类名称
   * @param entityComment 实体类注释
   * @param javaColumns java列
   */
  void create(String outputProjectPath, String rootPackageName, String entityName, String entityComment, List<JavaColumn> javaColumns);

}
