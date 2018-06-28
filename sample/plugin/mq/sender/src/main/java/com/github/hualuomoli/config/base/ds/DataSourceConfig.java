package com.github.hualuomoli.config.base.ds;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.github.hualuomoli.util.ProjectConfig;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * 数据库
 */
@Configuration(value = "com.github.hualuomoli.config.base.ds.DataSourceConfig")
// 加载配置文件
// @propertysource(value = { //
// "classpath:prop/jdbc.properties", //
// "file:path/to/test", // 测试环境绝对路径
// "file:path/to/publish", // 生产环境绝对路径
// }, ignoreResourceNotFound = true)
public class DataSourceConfig {

  @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
  public AtomikosDataSourceBean atomikosDataSourceBean() {

    MysqlXADataSource xaDataSource = new MysqlXADataSource();
    xaDataSource.setUrl(ProjectConfig.getString("jdbc.url"));
    xaDataSource.setUser(ProjectConfig.getString("jdbc.username"));
    xaDataSource.setPassword(ProjectConfig.getString("jdbc.password"));

    AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
    ds.setUniqueResourceName("ds");
    ds.setXaDataSource(xaDataSource);
    ds.setMinPoolSize(10);
    ds.setMaxPoolSize(100);
    ds.setMaxIdleTime(60);
    ds.setReapTimeout(2000);

    return ds;
  }

}
