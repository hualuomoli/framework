package com.github.hualuomoli.config.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.hualuomoli.config.base.ds2.DataSourceConfig;
import com.github.hualuomoli.config.base.ds2.MybatisConfig;
import com.github.hualuomoli.config.base.ds2.MybatisScannerConfig;

@Configuration(value = "com.github.hualuomoli.config.base.Ds2Config")
@Import(value = { DataSourceConfig.class, MybatisConfig.class, MybatisScannerConfig.class })
public class Ds2Config {

}
