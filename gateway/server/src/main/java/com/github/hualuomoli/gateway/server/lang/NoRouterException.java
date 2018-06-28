package com.github.hualuomoli.gateway.server.lang;

/**
 * 没有路由
 */
public class NoRouterException extends RuntimeException {

  private static final long serialVersionUID = 3217734247208187615L;

  private String method;

  public NoRouterException(String method) {
    super();
    this.method = method;
  }

  public NoRouterException(String method, Throwable cause) {
    super(cause);
    this.method = method;
  }

  public String method() {
    return method;
  }

  @Override
  public String getMessage() {
    return "method " + method + " can not found.";
  }

}
