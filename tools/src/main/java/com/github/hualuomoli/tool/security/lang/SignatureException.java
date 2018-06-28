package com.github.hualuomoli.tool.security.lang;

/**
 * 签名验签错误
 */
public class SignatureException extends RuntimeException {

  private static final long serialVersionUID = 1545635954816356487L;

  public SignatureException(String message, Throwable cause) {
    super(message, cause);
  }

}
