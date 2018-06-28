package com.github.hualuomoli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.hualuomoli.config.base.BaseComponentConfig;
import com.github.hualuomoli.config.base.DsConfig;
import com.github.hualuomoli.config.base.JmsConfig;
import com.github.hualuomoli.config.base.JtaTransactionConfig;
import com.github.hualuomoli.config.base.SpringTransactionConfig;

/**
 * base
 */
@Configuration(value = "com.github.hualuomoli.config.BaseConfig")
@Import(value = { DsConfig.class //
    , JmsConfig.class//
    , BaseComponentConfig.class //
    , SpringTransactionConfig.class //
    , JtaTransactionConfig.class })
public class BaseConfig {

}
