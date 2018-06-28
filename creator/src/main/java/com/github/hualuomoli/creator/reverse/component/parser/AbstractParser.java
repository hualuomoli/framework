package com.github.hualuomoli.creator.reverse.component.parser;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.creator.reverse.component.entity.DBColumn;
import com.github.hualuomoli.creator.reverse.component.entity.DBUniqueIndex;
import com.github.hualuomoli.creator.reverse.component.entity.JavaColumn;
import com.github.hualuomoli.creator.reverse.component.entity.JavaUniqueIndex;
import com.github.hualuomoli.creator.reverse.util.ReverseUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class AbstractParser implements Parser {

  @Override
  public List<JavaColumn> parse(List<DBColumn> dbColumns, String primary, Resolver resolver) {
    List<JavaColumn> javaColumns = Lists.newArrayList();

    for (DBColumn dbColumn : dbColumns) {
      javaColumns.add(this.format(dbColumn, resolver));
    }

    if (StringUtils.isNotBlank(primary)) {
      for (JavaColumn javaColumn : javaColumns) {
        if (StringUtils.equals(javaColumn.getDbName(), primary)) {
          javaColumn.setPrimary(true);
          break;
        }
      }
    }

    return javaColumns;
  }

  @Override
  public List<JavaUniqueIndex> parse(List<JavaColumn> javaColumns, List<DBUniqueIndex> uniques) {
    List<JavaUniqueIndex> javaUniqueIndexs = Lists.newArrayList();
    Map<Integer, List<JavaColumn>> map = Maps.newHashMap();

    for (DBUniqueIndex unique : uniques) {
      Integer indexId = unique.getIndexId();
      List<JavaColumn> list = map.get(indexId);
      if (list == null) {
        list = Lists.newArrayList();
        map.put(indexId, list);
      }
      for (JavaColumn javaColumn : javaColumns) {
        if (StringUtils.equals(javaColumn.getDbName(), unique.getName())) {
          javaColumn.setUnique(true);
          list.add(javaColumn);
          break;
        }
      }
    }

    Collection<List<JavaColumn>> valus = map.values();
    for (List<JavaColumn> value : valus) {
      JavaUniqueIndex javaUniqueIndex = new JavaUniqueIndex();
      javaUniqueIndex.setFirstJavaColumn(value.get(0));
      value.remove(0);
      javaUniqueIndex.setNextJavaColumns(value);
      javaUniqueIndexs.add(javaUniqueIndex);
    }

    return javaUniqueIndexs;
  }

  /**
   * 解析
   * @param dbColumn 数据库列
   * @param resolver 解析器
   * @return java列
   */
  private JavaColumn format(DBColumn dbColumn, Resolver resolver) {
    String dbName = dbColumn.getColumnName();
    String javaName = this.getName(dbName);
    String comment = dbColumn.getComment();

    Class<?> javaType = this.formatJavaType(dbColumn);

    if (resolver != null) {
      javaName = resolver.resolverJavaName(javaType, javaName, dbName);
      javaType = resolver.resolverJavaType(javaType, javaName, dbName);
    }

    return new JavaColumn(dbName, javaName, javaType, comment);
  }

  /**
   * 解析java类型
   * @param dbColumn 数据库列信息
   * @return java类型
   */
  protected abstract Class<?> formatJavaType(DBColumn dbColumn);

  protected String getName(String dbName) {
    return ReverseUtils.hump(dbName);
  }

}
