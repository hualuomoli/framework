package com.github.hualuomoli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.github.hualuomoli.util.ProjectConfig;

@Configuration(value = "com.github.hualuomoli.config.ConsumerConfig")
public class ConsumerConfig {

  @Bean
  public ApplicationConfig applicationConfig() {
    return new ApplicationConfig("consumer");
  }

  @Bean
  public RegistryConfig registryConfig() {
    return new RegistryConfig(ProjectConfig.getString("zookeeper.address"));
  }

  @Bean
  public ProtocolConfig protocolConfig() {
    return new ProtocolConfig("dubbo", ProjectConfig.getInteger("zookeeper.port"));
  }

  @Bean
  public AnnotationBean annotationBean() {
    AnnotationBean bean = new AnnotationBean();
    bean.setPackage("com.github.hualuomoli.sample.dubbo");

    return bean;
  }

}
