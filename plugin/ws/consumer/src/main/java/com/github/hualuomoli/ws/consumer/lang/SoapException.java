package com.github.hualuomoli.ws.consumer.lang;

/**
 * SOAP错误
 */
public class SoapException extends RuntimeException {

  private static final long serialVersionUID = 2507549143676123969L;

  public SoapException() {
    super();
  }

  public SoapException(String message, Throwable cause) {
    super(message, cause);
  }

  public SoapException(String message) {
    super(message);
  }

  public SoapException(Throwable cause) {
    super(cause);
  }

}
