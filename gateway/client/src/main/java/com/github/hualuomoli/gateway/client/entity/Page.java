package com.github.hualuomoli.gateway.client.entity;

import java.util.List;

public class Page<T> {

  /** 当前页码 */
  private Integer pageNumber;
  /** 每页数量 */
  private Integer pageSize;
  /** 总数据量 */
  private Integer count;
  /** 数据集合 */
  private List<T> dataList;

  public Page() {
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

  public List<T> getDataList() {
    return dataList;
  }

  public void setDataList(List<T> dataList) {
    this.dataList = dataList;
  }

}
