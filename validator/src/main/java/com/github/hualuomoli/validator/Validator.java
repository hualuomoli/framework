package com.github.hualuomoli.validator;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.groups.Default;

import com.github.hualuomoli.validator.lang.InvalidParameterException;

/**
 * 验证器工具
 */
public class Validator {

  private static javax.validation.Validator validator;

  /**
   * 验证实体类是否有效
   * @param object 实体类
   * @throws InvalidParameterException 不合法的数据
   */
  public static void valid(Object object) throws InvalidParameterException {
    Set<ConstraintViolation<Object>> set = valid(object, Default.class);
    if (set == null || set.size() == 0) {
      return;
    }
    // 设置错误信息
    Set<String> errors = new HashSet<String>();
    for (ConstraintViolation<Object> constraintViolation : set) {
      String fieldName = "[" + constraintViolation.getPropertyPath() + "]";
      errors.add(fieldName + constraintViolation.getMessage());
    }
    throw new InvalidParameterException(errors);
  }

  /**
   * 验证实体类是否有效
   * @param object 实体类
   * @throws InvalidParameterException 不合法的数据
   */
  public static void valid(Object object, String message) throws InvalidParameterException {
    Set<ConstraintViolation<Object>> set = valid(object, Default.class);
    if (set == null || set.size() == 0) {
      return;
    }
    throw new InvalidParameterException(message);
  }

  /**
   * 验证实体类是否有效
   * @param object 实体类
   * @param groups 验证规则
   * @throws InvalidParameterException 不合法的数据
   */
  private static Set<ConstraintViolation<Object>> valid(Object object, Class<?>... groups) {

    if (object == null) {
      return null;
    }

    if (groups == null || groups.length == 0) {
      return null;
    }

    // 获取验证器
    javax.validation.Validator validator = getValidator();

    // 验证
    return validator.validate(object, groups);
  }

  /**
   * 获取验证器
   * @return 验证器
   */
  private static javax.validation.Validator getValidator() {
    if (validator == null) {
      validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    return validator;
  }

}
