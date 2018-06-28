package com.github.hualuomoli.validator.constraintvalidation;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.hualuomoli.validator.constraints.Less;

public class LessValidatorForNumber implements ConstraintValidator<Less, Number> {

  private long less;

  @Override
  public void initialize(Less less) {
    this.less = less.value();
  }

  @Override
  public boolean isValid(Number value, ConstraintValidatorContext context) {
    // null values are valid
    if (value == null) {
      return true;
    }

    if (value instanceof BigDecimal) {
      return ((BigDecimal) value).compareTo(BigDecimal.valueOf(less)) == -1;
    } else if (value instanceof BigInteger) {
      return ((BigInteger) value).compareTo(BigInteger.valueOf(less)) == -1;
    } else {
      long longValue = value.longValue();
      return longValue > less;
    }
  }

}
