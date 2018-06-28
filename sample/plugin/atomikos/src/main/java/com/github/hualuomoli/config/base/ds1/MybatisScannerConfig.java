package com.github.hualuomoli.config.base.ds1;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.util.ProjectConfig;

/**
 * Mybatis扫描配置,需要与mybatis的其他配置分开
 */
@Configuration(value = "com.github.hualuomoli.config.base.ds1.MybatisScannerConfig")
public class MybatisScannerConfig {

  @Bean
  public MapperScannerConfigurer loadMapperScannerConfigurer() throws ClassNotFoundException {

    MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
    mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory1");
    mapperScannerConfigurer.setBasePackage(ProjectConfig.getString("mybatis.1.package"));
    mapperScannerConfigurer.setAnnotationClass(Repository.class);

    return mapperScannerConfigurer;
  }

}
