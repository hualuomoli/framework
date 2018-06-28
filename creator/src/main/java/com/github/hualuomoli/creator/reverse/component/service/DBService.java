package com.github.hualuomoli.creator.reverse.component.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.entity.DBUniqueIndex;

@Transactional(readOnly = true)
public interface DBService {

  /**
   * 查询表注释
   * @param db 数据库
   * @param tableName 表名
   * @return 注释
   */
  String findComment(String db, String tableName);

  /**
   * 查询表的列
   * @param db 数据库
   * @param tableName 表名
   * @return 列信息
   */
  List<DBColumn> findList(String db, String tableName);

  /**
   * 查询表的主键,如果没有主键返回null
   * @param db 数据库
   * @param tableName 表名
   * @return 表的主键
   */
  String findPrimaryKey(String db, String tableName);

  /**
   * 查询表的唯一索引
   * @param db 数据库
   * @param tableName 表名
   * @return 表的唯一索引
   */
  List<DBUniqueIndex> findUniqueKey(String db, String tableName);

}
