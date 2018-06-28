package com.github.hualuomoli.validator.constraintvalidation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.hualuomoli.validator.constraints.Less;

public class LessValidatorForCharSequence implements ConstraintValidator<Less, CharSequence> {

  private BigDecimal less;

  public void initialize(Less less) {
    this.less = BigDecimal.valueOf(less.value());
  }

  public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
    //null values are valid
    if (value == null) {
      return true;
    }
    try {
      return new BigDecimal(value.toString()).compareTo(less) == -1;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }
}
