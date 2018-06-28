package com.github.hualuomoli.validator.constraintvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.hualuomoli.validator.constraints.Identity;

public class IdentityValidator implements ConstraintValidator<Identity, String> {

  @Override
  public void initialize(Identity constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    return true;
  }

}
