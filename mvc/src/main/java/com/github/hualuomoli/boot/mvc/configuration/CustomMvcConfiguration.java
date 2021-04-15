package com.github.hualuomoli.boot.mvc.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "com.github.hualuomoli.boot.mvc")
@Configuration(value = "com.github.hualuomoli.boot.mvc.configuration.CustomMvcConfiguration")
public class CustomMvcConfiguration {

}
