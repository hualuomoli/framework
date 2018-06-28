package com.github.hualuomoli.sample.gateway.server.entity;

import com.github.hualuomoli.gateway.client.entity.Request;

public class GatewayClientRequest implements Request {

  /** 网关版本号 */
  private String version;
  /** 合作伙伴ID */
  private String partnerId;
  /** 请求方法 */
  private String method;
  /** 请求业务内容 */
  private String bizContent;
  /** 签名数据 */
  private String sign;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getBizContent() {
    return bizContent;
  }

  public void setBizContent(String bizContent) {
    this.bizContent = bizContent;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  @Override
  public String toString() {
    return "GatewayClientRequest [version=" + version + ", partnerId=" + partnerId + ", method=" + method + ", bizContent=" + bizContent + ", sign=" + sign
        + "]";
  }

}
