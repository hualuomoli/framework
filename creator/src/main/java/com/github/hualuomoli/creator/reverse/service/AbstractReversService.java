package com.github.hualuomoli.creator.reverse.service;

import java.util.List;

import com.github.hualuomoli.creator.reverse.base.EntityBaseCreater;
import com.github.hualuomoli.creator.reverse.base.MapperBaseCreater;
import com.github.hualuomoli.creator.reverse.base.ServiceBaseCreater;
import com.github.hualuomoli.creator.reverse.base.XmlBaseCreater;
import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.entity.DBUniqueIndex;
import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;
import com.github.hualuomoli.creator.reverse.component.entity.JavaUniqueIndex;
import com.github.hualuomoli.creator.reverse.component.parser.Parser;
import com.github.hualuomoli.creator.reverse.component.parser.Parser.Resolver;
import com.github.hualuomoli.creator.reverse.component.service.DBService;
import com.github.hualuomoli.creator.reverse.query.EntityQueryCreater;
import com.github.hualuomoli.creator.reverse.query.MapperQueryCreater;
import com.github.hualuomoli.creator.reverse.query.ServiceQueryCreater;
import com.github.hualuomoli.creator.reverse.query.XmlQueryCreater;

public abstract class AbstractReversService implements ReversService {

  protected abstract DBService dbService();

  protected abstract Parser parser();

  // base
  protected abstract EntityBaseCreater entityBaseCreater();

  protected abstract XmlBaseCreater xmlBaseCreater();

  protected abstract MapperBaseCreater mapperBaseCreater();

  protected abstract ServiceBaseCreater serviceBaseCreater();

  // query
  protected abstract EntityQueryCreater entityQueryCreater();

  protected abstract XmlQueryCreater xmlQueryCreater();

  protected abstract MapperQueryCreater mapperQueryCreater();

  protected abstract ServiceQueryCreater serviceQueryCreater();

  @Override
  public void create(String outputProjectPath, String rootPackageName, String db, String tableName, String entityName) {
    this.create(outputProjectPath, rootPackageName, db, tableName, entityName, null);
  }

  @Override
  public void create(String outputProjectPath, String rootPackageName, String db, String tableName, String entityName, Resolver resolver) {
    // 查询数据库列
    List<DBColumn> dbColumns = this.dbService().findList(db, tableName);
    String entityComment = this.dbService().findComment(db, tableName);

    // 主键
    String primaryKey = this.dbService().findPrimaryKey(db, tableName);
    // 唯一索引
    List<DBUniqueIndex> uniques = this.dbService().findUniqueKey(db, tableName);

    // java列
    List<JavaColumn> javaColumns = this.parser().parse(dbColumns, primaryKey, resolver);
    List<JavaUniqueIndex> javaUniqueIndexs = this.parser().parse(javaColumns, uniques);

    // create

    // base
    this.entityBaseCreater().create(outputProjectPath, rootPackageName, entityName, entityComment, javaColumns);
    this.xmlBaseCreater().create(outputProjectPath, rootPackageName, entityName, javaColumns, javaUniqueIndexs, tableName);
    this.mapperBaseCreater().create(outputProjectPath, rootPackageName, entityName, entityComment, javaColumns, javaUniqueIndexs);
    this.serviceBaseCreater().create(outputProjectPath, rootPackageName, entityName, entityComment, javaColumns, javaUniqueIndexs);
    // query
    this.entityQueryCreater().create(outputProjectPath, rootPackageName, entityName, entityComment, javaColumns);
    this.xmlQueryCreater().create(outputProjectPath, rootPackageName, entityName, javaColumns, javaUniqueIndexs, tableName);
    this.mapperQueryCreater().create(outputProjectPath, rootPackageName, entityName, entityComment, javaColumns, javaUniqueIndexs);
    this.serviceQueryCreater().create(outputProjectPath, rootPackageName, entityName, entityComment, javaColumns, javaUniqueIndexs);
  }

  @Override
  public void create(String outputProjectPath, String rootPackageName, String db, Resolver resolver, EntityParser parser, String... tableNames) {

    for (String tableName : tableNames) {
      this.create(outputProjectPath, rootPackageName, db, tableName, parser.parse(tableName), resolver);
    }

  }

}
