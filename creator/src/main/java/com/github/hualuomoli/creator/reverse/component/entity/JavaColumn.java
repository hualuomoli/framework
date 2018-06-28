package com.github.hualuomoli.creator.reverse.component.entity;

public class JavaColumn {

  /** 数据库列 */
  private String dbName;
  /** 实体类名称 */
  private String javaName;
  /** 实体类类型 */
  private Class<?> javaType;
  /** 注释 */
  private String comment;

  /** 是否是主键 */
  private boolean primary;
  /** 是否是唯一索引的列 */
  private boolean unique;

  public JavaColumn() {
  }

  public JavaColumn(String dbName, String javaName, Class<?> javaType, String comment) {
    super();
    this.dbName = dbName;
    this.javaName = javaName;
    this.javaType = javaType;
    this.comment = comment;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getJavaName() {
    return javaName;
  }

  public void setJavaName(String javaName) {
    this.javaName = javaName;
  }

  public Class<?> getJavaType() {
    return javaType;
  }

  public void setJavaType(Class<?> javaType) {
    this.javaType = javaType;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getJavaTypeName() {
    return javaType.getName();
  }

  public boolean isEnum() {
    return Enum.class.isAssignableFrom(javaType);
  }

  public boolean isPrimary() {
    return primary;
  }

  public void setPrimary(boolean primary) {
    this.primary = primary;
  }

  public boolean isUnique() {
    return unique;
  }

  public void setUnique(boolean unique) {
    this.unique = unique;
  }

  @Override
  public String toString() {
    return "JavaColumn [dbName=" + dbName + ", javaName=" + javaName + ", javaType=" + javaType + ", comment=" + comment + ", primary=" + primary + ", unique=" + unique + "]";
  }

}
