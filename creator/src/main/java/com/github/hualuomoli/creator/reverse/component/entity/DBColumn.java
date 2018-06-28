package com.github.hualuomoli.creator.reverse.component.entity;

public class DBColumn {

  /** 列名 */
  private String columnName;
  /** 数据类型 */
  private String dataType;
  /** 长度 */
  private Long length;
  /** 精度 */
  private Integer precision;
  /** 小数位数 */
  private Integer scale;
  /** 注释 */
  private String comment;

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public Long getLength() {
    return length;
  }

  public void setLength(Long length) {
    this.length = length;
  }

  public Integer getPrecision() {
    return precision;
  }

  public void setPrecision(Integer precision) {
    this.precision = precision;
  }

  public Integer getScale() {
    return scale;
  }

  public void setScale(Integer scale) {
    this.scale = scale;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

}
