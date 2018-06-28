package com.github.hualuomoli.creator.reverse.component.entity;

/**
 * 唯一索引
 */
public class DBUniqueIndex {

  /** 索引ID */
  private Integer indexId;
  /** 索引列名称 */
  private String name;
  /** 索引列位置 */
  private Integer pos;

  public Integer getIndexId() {
    return indexId;
  }

  public void setIndexId(Integer indexId) {
    this.indexId = indexId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPos() {
    return pos;
  }

  public void setPos(Integer pos) {
    this.pos = pos;
  }

}
