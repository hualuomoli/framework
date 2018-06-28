package com.github.hualuomoli.gateway.client.lang;

import com.github.hualuomoli.gateway.client.entity.Response;

/**
 * 客户端错误
 */
public class ClientException extends RuntimeException {

  private static final long serialVersionUID = -8367991706522073265L;

  private Response response;

  public ClientException(Response response) {
    this.response = response;
  }

  public ClientException(Throwable cause) {
    super(cause);
  }

  @SuppressWarnings("unchecked")
  public <Res extends Response> Res getResponse() {
    return (Res) response;
  }

}
