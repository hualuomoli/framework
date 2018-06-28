package com.github.hualuomoli.config.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 切面
 */
@Configuration(value = "com.github.hualuomoli.config.base.BaseAspectConfig")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BaseAspectConfig {

  static {
    System.out.println("初始化spring aspect");
  }

}
