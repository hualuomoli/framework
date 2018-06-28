package com.github.hualuomoli.gateway.server.lang;

/**
 * 安全错误
 */
public class SecurityException extends RuntimeException {

  private static final long serialVersionUID = 126784162667371180L;

  public SecurityException() {
    super();
  }

  public SecurityException(String message, Throwable cause) {
    super(message, cause);
  }

  public SecurityException(String message) {
    super(message);
  }

  public SecurityException(Throwable cause) {
    super(cause);
  }

}
