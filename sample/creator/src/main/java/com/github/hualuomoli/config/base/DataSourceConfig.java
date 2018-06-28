package com.github.hualuomoli.config.base;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.hualuomoli.util.ProjectConfig;

/**
 * 数据库
 */
@Configuration(value = "com.github.hualuomoli.config.base.DataSourceConfig")
// 加载配置文件
// @propertysource(value = { //
// "classpath:prop/jdbc.properties", //
// "file:path/to/test", // 测试环境绝对路径
// "file:path/to/publish", // 生产环境绝对路径
// }, ignoreResourceNotFound = true)
public class DataSourceConfig {

  @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
  public DataSource dataSource() {

    System.out.println("初始化数据库连接");

    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setDriverClassName(ProjectConfig.getString("jdbc.driverClassName"));
    dataSource.setUrl(ProjectConfig.getString("jdbc.url"));
    dataSource.setUsername(ProjectConfig.getString("jdbc.username"));
    dataSource.setPassword(ProjectConfig.getString("jdbc.password"));

    return dataSource;
  }

  // spring加载配置文件
  @Bean
  public static PropertySourcesPlaceholderConfigurer placehodlerConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
