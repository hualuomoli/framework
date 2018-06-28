package com.github.hualuomoli.sample.atomikos.ds2.entity;

// 收货地址
public class Address {

  /** id */
  private java.lang.String id;
  /** 区域编码 */
  private java.lang.String areaCode;
  /** 省份名称 */
  private java.lang.String province;
  /** 地市 */
  private java.lang.String city;
  /** 区县 */
  private java.lang.String county;
  /** 详细地址 */
  private java.lang.String detailAddress;

  public Address() {
  }

  // getter and setter

  public java.lang.String getId() {
    return this.id;
  }

  public void setId(java.lang.String id) {
    this.id = id;
  }

  public java.lang.String getAreaCode() {
    return this.areaCode;
  }

  public void setAreaCode(java.lang.String areaCode) {
    this.areaCode = areaCode;
  }

  public java.lang.String getProvince() {
    return this.province;
  }

  public void setProvince(java.lang.String province) {
    this.province = province;
  }

  public java.lang.String getCity() {
    return this.city;
  }

  public void setCity(java.lang.String city) {
    this.city = city;
  }

  public java.lang.String getCounty() {
    return this.county;
  }

  public void setCounty(java.lang.String county) {
    this.county = county;
  }

  public java.lang.String getDetailAddress() {
    return this.detailAddress;
  }

  public void setDetailAddress(java.lang.String detailAddress) {
    this.detailAddress = detailAddress;
  }

  @Override
  public String toString() {
    return "Address [" //
        + "id=" + id //
        + ", areaCode=" + areaCode //
        + ", province=" + province //
        + ", city=" + city //
        + ", county=" + county //
        + ", detailAddress=" + detailAddress //
        + "]";
  }

}