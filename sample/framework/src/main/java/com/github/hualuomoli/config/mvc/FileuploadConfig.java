package com.github.hualuomoli.config.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 配置HTTP文件上传
 */
@Configuration(value = "com.github.hualuomoli.config.mvc.FileuploadConfig")
public class FileuploadConfig {

  // 文件上传
  @Bean
  public MultipartResolver multipartResolver() {
    System.out.println("初始化文件上传 multipartResolver");

    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    multipartResolver.setMaxUploadSize(10 * 1024 * 1024);
    //    multipartResolver.setDefaultEncoding("UTF-8");
    return multipartResolver;
  }

}
