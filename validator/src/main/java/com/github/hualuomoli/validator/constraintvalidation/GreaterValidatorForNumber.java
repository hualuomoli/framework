package com.github.hualuomoli.validator.constraintvalidation;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.hualuomoli.validator.constraints.Greater;

public class GreaterValidatorForNumber implements ConstraintValidator<Greater, Number> {

  private long greater;

  @Override
  public void initialize(Greater greater) {
    this.greater = greater.value();
  }

  @Override
  public boolean isValid(Number value, ConstraintValidatorContext context) {
    // null values are valid
    if (value == null) {
      return true;
    }

    if (value instanceof BigDecimal) {
      return ((BigDecimal) value).compareTo(BigDecimal.valueOf(greater)) == 1;
    } else if (value instanceof BigInteger) {
      return ((BigInteger) value).compareTo(BigInteger.valueOf(greater)) == 1;
    } else {
      double doubleValue = value.doubleValue();
      return doubleValue > greater;
    }
  }

}
