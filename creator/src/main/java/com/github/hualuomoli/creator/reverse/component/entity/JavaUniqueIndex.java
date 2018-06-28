package com.github.hualuomoli.creator.reverse.component.entity;

import java.util.List;

/**
 * 唯一索引
 */
public class JavaUniqueIndex {

  /** 唯一索引的第一个值 */
  private JavaColumn firstJavaColumn;
  /** 联合唯一索引,除第一个属性外的其它属性 */
  private List<JavaColumn> nextJavaColumns;

  public JavaColumn getFirstJavaColumn() {
    return firstJavaColumn;
  }

  public void setFirstJavaColumn(JavaColumn firstJavaColumn) {
    this.firstJavaColumn = firstJavaColumn;
  }

  public List<JavaColumn> getNextJavaColumns() {
    return nextJavaColumns;
  }

  public void setNextJavaColumns(List<JavaColumn> nextJavaColumns) {
    this.nextJavaColumns = nextJavaColumns;
  }

  @Override
  public String toString() {
    return "JavaUniqueIndex [firstJavaColumn=" + firstJavaColumn + ", nextJavaColumns=" + nextJavaColumns + "]";
  }

}
