package com.github.hualuomoli.sample.atomikos.ds2.query.entity;

import com.github.hualuomoli.sample.atomikos.ds2.entity.Address;

// 收货地址
public class AddressQuery extends Address {

  /** 区域编码 */
  private java.lang.String areaCodeLeftLike;
  private java.lang.String areaCodeRightLike;
  private java.lang.String areaCodeLike;
  /** 省份名称 */
  private java.lang.String provinceLeftLike;
  private java.lang.String provinceRightLike;
  private java.lang.String provinceLike;
  /** 地市 */
  private java.lang.String cityLeftLike;
  private java.lang.String cityRightLike;
  private java.lang.String cityLike;
  /** 区县 */
  private java.lang.String countyLeftLike;
  private java.lang.String countyRightLike;
  private java.lang.String countyLike;
  /** 详细地址 */
  private java.lang.String detailAddressLeftLike;
  private java.lang.String detailAddressRightLike;
  private java.lang.String detailAddressLike;

  public AddressQuery() {
  }

  // getter and setter

  public java.lang.String getAreaCodeLeftLike() {
    return areaCodeLeftLike;
  }

  public void setAreaCodeLeftLike(java.lang.String areaCodeLeftLike) {
    this.areaCodeLeftLike = areaCodeLeftLike;
  }

  public java.lang.String getAreaCodeRightLike() {
    return areaCodeRightLike;
  }

  public void setAreaCodeRightLike(java.lang.String areaCodeRightLike) {
    this.areaCodeRightLike = areaCodeRightLike;
  }

  public java.lang.String getAreaCodeLike() {
    return areaCodeLike;
  }

  public void setAreaCodeLike(java.lang.String areaCodeLike) {
    this.areaCodeLike = areaCodeLike;
  }

  public java.lang.String getProvinceLeftLike() {
    return provinceLeftLike;
  }

  public void setProvinceLeftLike(java.lang.String provinceLeftLike) {
    this.provinceLeftLike = provinceLeftLike;
  }

  public java.lang.String getProvinceRightLike() {
    return provinceRightLike;
  }

  public void setProvinceRightLike(java.lang.String provinceRightLike) {
    this.provinceRightLike = provinceRightLike;
  }

  public java.lang.String getProvinceLike() {
    return provinceLike;
  }

  public void setProvinceLike(java.lang.String provinceLike) {
    this.provinceLike = provinceLike;
  }

  public java.lang.String getCityLeftLike() {
    return cityLeftLike;
  }

  public void setCityLeftLike(java.lang.String cityLeftLike) {
    this.cityLeftLike = cityLeftLike;
  }

  public java.lang.String getCityRightLike() {
    return cityRightLike;
  }

  public void setCityRightLike(java.lang.String cityRightLike) {
    this.cityRightLike = cityRightLike;
  }

  public java.lang.String getCityLike() {
    return cityLike;
  }

  public void setCityLike(java.lang.String cityLike) {
    this.cityLike = cityLike;
  }

  public java.lang.String getCountyLeftLike() {
    return countyLeftLike;
  }

  public void setCountyLeftLike(java.lang.String countyLeftLike) {
    this.countyLeftLike = countyLeftLike;
  }

  public java.lang.String getCountyRightLike() {
    return countyRightLike;
  }

  public void setCountyRightLike(java.lang.String countyRightLike) {
    this.countyRightLike = countyRightLike;
  }

  public java.lang.String getCountyLike() {
    return countyLike;
  }

  public void setCountyLike(java.lang.String countyLike) {
    this.countyLike = countyLike;
  }

  public java.lang.String getDetailAddressLeftLike() {
    return detailAddressLeftLike;
  }

  public void setDetailAddressLeftLike(java.lang.String detailAddressLeftLike) {
    this.detailAddressLeftLike = detailAddressLeftLike;
  }

  public java.lang.String getDetailAddressRightLike() {
    return detailAddressRightLike;
  }

  public void setDetailAddressRightLike(java.lang.String detailAddressRightLike) {
    this.detailAddressRightLike = detailAddressRightLike;
  }

  public java.lang.String getDetailAddressLike() {
    return detailAddressLike;
  }

  public void setDetailAddressLike(java.lang.String detailAddressLike) {
    this.detailAddressLike = detailAddressLike;
  }

}
