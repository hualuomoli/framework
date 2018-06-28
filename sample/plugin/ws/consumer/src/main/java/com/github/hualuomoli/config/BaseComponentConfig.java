package com.github.hualuomoli.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * 使用Annotation自动注册Bean
 */
@Configuration
// 主容器中不扫描@Controller注解，在SpringMvc中只扫描@Controller注解(解决事物失效问题)
@ComponentScan( //
    basePackages = { "com.github.hualuomoli" }, //
    excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) } //
)
public class BaseComponentConfig {

  private static final Logger logger = LoggerFactory.getLogger(BaseComponentConfig.class);

  static {
    logger.info("初始化spring component");
  }

}
