package com.github.hualuomoli.validator.constraintvalidation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.hualuomoli.validator.constraints.Greater;

public class GreaterValidatorForCharSequence implements ConstraintValidator<Greater, CharSequence> {

  private BigDecimal greater;

  public void initialize(Greater greater) {
    this.greater = BigDecimal.valueOf(greater.value());
  }

  public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
    //null values are valid
    if (value == null) {
      return true;
    }
    try {
      return new BigDecimal(value.toString()).compareTo(greater) == 1;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }
}
