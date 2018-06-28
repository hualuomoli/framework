package com.github.hualuomoli.framework.entity;

import java.util.List;

/**
 * 分页
 */
public class Page {

  /** 当前页码,从1开始 */
  private Integer pageNumber;
  /** 每页数据量 */
  private Integer pageSize;
  /** 数据总量 */
  private Integer count;
  /** 数据 */
  private List<?> dataList;

  public Page(Integer pageNumber, Integer pageSize, Integer count, List<?> dataList) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.count = count;
    this.dataList = dataList;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> getDataList() {
    return (List<T>) dataList;
  }

  public void setDataList(List<?> dataList) {
    this.dataList = dataList;
  }

}
