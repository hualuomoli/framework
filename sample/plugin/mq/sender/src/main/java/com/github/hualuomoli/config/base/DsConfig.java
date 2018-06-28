package com.github.hualuomoli.config.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.hualuomoli.config.base.ds.DataSourceConfig;
import com.github.hualuomoli.config.base.ds.MybatisConfig;
import com.github.hualuomoli.config.base.ds.MybatisScannerConfig;

@Configuration(value = "com.github.hualuomoli.config.base.DsConfig")
@Import(value = { DataSourceConfig.class, MybatisConfig.class, MybatisScannerConfig.class })
public class DsConfig {

}
