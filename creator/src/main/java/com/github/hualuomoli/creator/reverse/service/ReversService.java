package com.github.hualuomoli.creator.reverse.service;

import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.creator.reverse.component.parser.Parser.Resolver;

@Transactional(readOnly = true)
public interface ReversService {

  /**
   * 生成
   * @param outputProjectPath 输出项目路径
   * @param rootPackageName 包路径
   * @param db 数据库
   * @param tableName 表名
   * @param entityName 实体类名
   */
  void create(String outputProjectPath, String rootPackageName, String db, String tableName, String entityName);

  /**
   * 生成
   * @param outputProjectPath 输出项目路径
   * @param rootPackageName 包路径
   * @param db 数据库
   * @param tableName 表名
   * @param entityName 实体类名
   * @param resolver 解析器
   */
  void create(String outputProjectPath, String rootPackageName, String db, String tableName, String entityName, Resolver resolver);

  /**
   * 生成
   * @param outputProjectPath 输出项目路径
   * @param rootPackageName 包路径
   * @param db 数据库
   * @param resolver 解析器
   * @param parser 转换器
   * @param tableNames 表名
   */
  void create(String outputProjectPath, String rootPackageName, String db, Resolver resolver, EntityParser parser, String... tableNames);

  public static interface EntityParser {

    /**
     * 解析表名获取实体类名
     * @param tableName 表名
     * @return 转换后的表名
     */
    String parse(String tableName);

  }

}
