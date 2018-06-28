package com.github.hualuomoli.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.hualuomoli.validator.constraintvalidation.LessValidatorForCharSequence;
import com.github.hualuomoli.validator.constraintvalidation.LessValidatorForNumber;

/**
 * 小于
 */
@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { LessValidatorForNumber.class, LessValidatorForCharSequence.class })
public @interface Less {

  String message() default "{com.github.hualuomoli.validator.constraints.Less.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  long value();

  @interface List {

    Greater[] value();

  }

}
