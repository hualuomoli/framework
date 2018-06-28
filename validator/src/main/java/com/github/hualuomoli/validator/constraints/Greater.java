package com.github.hualuomoli.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.hualuomoli.validator.constraintvalidation.GreaterValidatorForCharSequence;
import com.github.hualuomoli.validator.constraintvalidation.GreaterValidatorForNumber;

/**
 * 大于
 */
@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { GreaterValidatorForNumber.class, GreaterValidatorForCharSequence.class })
public @interface Greater {

  String message() default "{com.github.hualuomoli.validator.constraints.Greater.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  long value();

  @interface List {

    Greater[] value();

  }

}
