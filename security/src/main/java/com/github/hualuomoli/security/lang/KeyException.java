package com.github.hualuomoli.security.lang;

/**
 * Key异常
 */
public class KeyException extends RuntimeException {

  private static final long serialVersionUID = -6131356725347303468L;

  public KeyException(String message) {
    super(message);
  }

  public KeyException(String message, Throwable cause) {
    super(message, cause);
  }

}
