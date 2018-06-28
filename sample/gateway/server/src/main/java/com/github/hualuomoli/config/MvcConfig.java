package com.github.hualuomoli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.hualuomoli.config.mvc.ConvertConfig;
import com.github.hualuomoli.config.mvc.MvcComponentConfig;
import com.github.hualuomoli.config.mvc.RequestVersionConfig;

/**
 * mvc
 */
@Configuration(value = "com.github.hualuomoli.config.MvcConfig")
@Import(value = { MvcComponentConfig.class, RequestVersionConfig.class, ConvertConfig.class })
public class MvcConfig {

}
