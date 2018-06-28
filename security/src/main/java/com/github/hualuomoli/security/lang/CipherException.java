package com.github.hualuomoli.security.lang;

/**
 * 加解密异常
 */
public class CipherException extends RuntimeException {

  private static final long serialVersionUID = 4779339043824685512L;

  public CipherException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
