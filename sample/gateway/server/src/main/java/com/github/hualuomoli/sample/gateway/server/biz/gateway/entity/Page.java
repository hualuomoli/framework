package com.github.hualuomoli.sample.gateway.server.biz.gateway.entity;

import java.util.List;

@SuppressWarnings("unchecked")
public class Page {

  /** 当前页码 */
  private Integer pageNumber;
  /** 每页数量 */
  private Integer pageSize;
  /** 总数据量 */
  private Integer count;
  /** 数据集合 */
  private List<?> dataList;

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

  public <T> List<T> getDataList() {
    return (List<T>) dataList;
  }

  public void setDataList(List<?> dataList) {
    this.dataList = dataList;
  }

  @Override
  public String toString() {
    return "Page [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", count=" + count + ", dataList=" + dataList + "]";
  }

}
