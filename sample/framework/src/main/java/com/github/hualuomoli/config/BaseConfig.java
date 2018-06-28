package com.github.hualuomoli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.hualuomoli.config.base.BaseAspectConfig;
import com.github.hualuomoli.config.base.BaseComponentConfig;
import com.github.hualuomoli.config.base.DataSourceConfig;
import com.github.hualuomoli.config.base.MybatisConfig;
import com.github.hualuomoli.config.base.MybatisScannerConfig;
import com.github.hualuomoli.config.base.TransactionConfig;

/**
 * base
 */
@Configuration(value = "com.github.hualuomoli.config.BaseConfig")
@Import(value = { DataSourceConfig.class //
    , MybatisScannerConfig.class //
    , MybatisConfig.class //
    , BaseComponentConfig.class //
    , TransactionConfig.class //
    , BaseAspectConfig.class })
public class BaseConfig {

}
