package com.github.hualuomoli.validator.lang;

import java.util.HashSet;
import java.util.Set;

/**
 * 不合法的参数
 */
public class InvalidParameterException extends RuntimeException {

  private static final long serialVersionUID = -6131419253556355465L;

  private String message;
  private Set<String> errors;

  public InvalidParameterException(String message) {
    this.message = message;
    this.errors = new HashSet<String>();
    this.errors.add(message);
  }

  public InvalidParameterException(Set<String> errors) {
    if (errors == null || errors.size() == 0) {
      return;
    }
    StringBuilder buffer = new StringBuilder();
    for (String error : errors) {
      buffer.append(", ").append(error);
    }
    this.message = buffer.toString().substring(2);
    this.errors = errors;
  }

  public Set<String> getErrors() {
    return errors;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public String toString() {
    return this.message;
  }

}
