package com.github.hualuomoli.config.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.github.hualuomoli.framework.mvc.version.method.RequestMappingVersionHandlerMapping;

@Configuration(value = "com.github.hualuomoli.config.mvc.RequestVersionConfig")
@EnableWebMvc
public class RequestVersionConfig extends WebMvcConfigurationSupport {

  @Bean
  @Override
  public RequestMappingHandlerMapping requestMappingHandlerMapping() {
    return super.requestMappingHandlerMapping();
  }

  @Override
  protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
    return new RequestMappingVersionHandlerMapping();
  }

}
