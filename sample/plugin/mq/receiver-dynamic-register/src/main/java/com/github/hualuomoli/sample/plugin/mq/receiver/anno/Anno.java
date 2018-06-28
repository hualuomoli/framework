package com.github.hualuomoli.sample.plugin.mq.receiver.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Anno {

  // 目标名称
  String destinationName();

  // 客户端id,广播配置该值
  String clientId() default "";

}
