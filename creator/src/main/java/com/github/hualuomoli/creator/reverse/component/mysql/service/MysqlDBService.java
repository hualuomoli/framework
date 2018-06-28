package com.github.hualuomoli.creator.reverse.component.mysql.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.entity.DBUniqueIndex;
import com.github.hualuomoli.creator.reverse.component.mysql.mapper.MysqlDbMapper;
import com.github.hualuomoli.creator.reverse.component.service.DBService;

@Service(value = "com.github.hualuomoli.creator.reverse.component.mysql.service.MysqlDBService")
public class MysqlDBService implements DBService {

  @Autowired
  private MysqlDbMapper mysqlDbMapper;

  @Override
  public String findComment(String db, String tableName) {
    return mysqlDbMapper.findComment(db, tableName);
  }

  @Override
  public List<DBColumn> findList(String db, String tableName) {
    return mysqlDbMapper.findList(db, tableName);
  }

  @Override
  public String findPrimaryKey(String db, String tableName) {
    return mysqlDbMapper.findPrimaryKey(db, tableName);
  }

  @Override
  public List<DBUniqueIndex> findUniqueKey(String db, String tableName) {
    return mysqlDbMapper.findUniqueKey(db + "/" + tableName);
  }

}
