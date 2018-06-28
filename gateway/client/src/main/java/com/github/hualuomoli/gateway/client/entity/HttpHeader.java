package com.github.hualuomoli.gateway.client.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * HTTP的header
 */
public class HttpHeader {

  /** header名 */
  private String headerName;
  /** header值 */
  private List<String> headerValues;

  public HttpHeader(String headerName, List<String> headerValues) {
    super();
    this.headerName = headerName;
    this.headerValues = headerValues;
  }

  public HttpHeader(String headerName, String headerValue) {
    super();
    this.headerName = headerName;
    this.headerValues = new ArrayList<String>();
    this.headerValues.add(headerValue);
  }

  public String getHeaderName() {
    return headerName;
  }

  public void setHeaderName(String headerName) {
    this.headerName = headerName;
  }

  public List<String> getHeaderValues() {
    return headerValues;
  }

  public void setHeaderValues(List<String> headerValues) {
    this.headerValues = headerValues;
  }

  @Override
  public String toString() {
    return "HttpHeader [headerName=" + headerName + ", headerValues=" + headerValues + "]";
  }

}
