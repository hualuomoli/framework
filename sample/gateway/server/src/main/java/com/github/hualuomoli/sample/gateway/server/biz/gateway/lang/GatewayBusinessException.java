package com.github.hualuomoli.sample.gateway.server.biz.gateway.lang;

import com.github.hualuomoli.sample.gateway.server.biz.gateway.enums.GatewaySubErrorEnum;

public class GatewayBusinessException extends RuntimeException {

  private static final long serialVersionUID = -3011234878022959136L;

  /** 业务错误编码 */
  private GatewaySubErrorEnum subCode;
  /** 业务错误描述 */
  private String subMessage;

  public GatewayBusinessException(GatewaySubErrorEnum subCode, String subMessage) {
    super(subMessage);
    this.subCode = subCode;
    this.subMessage = subMessage;
  }

  public GatewayBusinessException(GatewaySubErrorEnum subCode, String subMessage, Throwable t) {
    super(subMessage, t);
    this.subCode = subCode;
    this.subMessage = subMessage;
  }

  public GatewaySubErrorEnum getSubCode() {
    return subCode;
  }

  public String getSubMessage() {
    return subMessage;
  }

}
