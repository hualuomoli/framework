package com.github.hualuomoli.creator.reverse.component.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.entity.DBUniqueIndex;

@Repository(value = "com.github.hualuomoli.creator.reverse.component.mysql.mapper.MysqlDbMapper")
public interface MysqlDbMapper {

  /**
   * 查询表的列
   * @param db 数据库
   * @param tableName 表
   * @return 列集合
   */
  List<DBColumn> findList(@Param(value = "db") String db, @Param(value = "tableName") String tableName);

  /**
   * 查询表注释
   * @param db 数据库
   * @param tableName 表名
   * @return 注释
   */
  String findComment(@Param(value = "db") String db, @Param(value = "tableName") String tableName);

  /**
   * 查询表的主键
   * @param db 数据库
   * @param tableName 表名
   * @return 表的主键
   */
  String findPrimaryKey(@Param(value = "db") String db, @Param(value = "tableName") String tableName);

  /**
   * 查询表的唯一索引
   * @param name 名称=db/tableName
   * @return 表的唯一索引
   */
  List<DBUniqueIndex> findUniqueKey(@Param(value = "name") String name);

}
