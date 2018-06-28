package com.github.hualuomoli.apidoc.entity.inner;

public class Address {
  /**
   * 省
   * @required
   * @maxLength 12
   * @sample 山东省
   */
  private String province;
  /**
   * 市
   * @required
   * @maxLength 12
   * @sample 青岛市
   */
  private String city;
  /**
   * 区
   * @required
   * @maxLength 12
   * @sample 市北区
   */
  private String county;
  /**
   * 详细地址
   * @required
   * @maxLength 50
   * @sample 合肥路666号6号楼6层107室
   */
  private String detailAddress;

  /**
   * 邮编
   * @sample 264000
   */
  private String zipCode;

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getDetailAddress() {
    return detailAddress;
  }

  public void setDetailAddress(String detailAddress) {
    this.detailAddress = detailAddress;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  @Override
  public String toString() {
    return "Address [province=" + province + ", city=" + city + ", county=" + county + ", detailAddress=" + detailAddress + ", zipCode=" + zipCode + "]";
  }

}
