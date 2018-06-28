package com.github.hualuomoli.config.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.hualuomoli.config.base.ds1.DataSourceConfig;
import com.github.hualuomoli.config.base.ds1.MybatisConfig;
import com.github.hualuomoli.config.base.ds1.MybatisScannerConfig;

@Configuration(value = "com.github.hualuomoli.config.base.Ds1Config")
@Import(value = { DataSourceConfig.class, MybatisConfig.class, MybatisScannerConfig.class })
public class Ds1Config {

}
