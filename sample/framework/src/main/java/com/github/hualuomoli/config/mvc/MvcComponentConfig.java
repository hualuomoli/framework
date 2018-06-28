package com.github.hualuomoli.config.mvc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.github.hualuomoli.config.base.BaseComponentConfig;

/**
 * 配置Controller自动注入
 * {@linkplain BaseComponentConfig}如果controller使用RestController注解,修改base增加RestController的忽略
 */
@Configuration(value = "com.github.hualuomoli.config.mvc.MvcComponentConfig")
@ComponentScan(//
    basePackages = { "com.github.hualuomoli" }, //
    useDefaultFilters = false, //
    includeFilters = { //
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }), //
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { RestController.class }) //
    }//
)
public class MvcComponentConfig {

  static {
    System.out.println("初始化spring mvc component");
  }

}
