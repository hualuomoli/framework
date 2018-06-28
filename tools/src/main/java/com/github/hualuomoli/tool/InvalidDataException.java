package com.github.hualuomoli.tool;

/**
 * 数据不合法
 */
public class InvalidDataException extends RuntimeException {

  private static final long serialVersionUID = 3513914060017051136L;

  public InvalidDataException() {
    super();
  }

  public InvalidDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidDataException(String message) {
    super(message);
  }

  public InvalidDataException(Throwable cause) {
    super(cause);
  }

}
