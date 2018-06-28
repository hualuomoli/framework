package com.github.hualuomoli.gateway.client.lang;

import com.github.hualuomoli.gateway.client.entity.Response;

/**
 * 业务处理失败
 */
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = 154725031372112684L;

  private Response response;

  public BusinessException(Response response) {
    this.response = response;
  }

  @SuppressWarnings("unchecked")
  public <Res extends Response> Res getResponse() {
    return (Res) response;
  }

}
