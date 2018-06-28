package com.github.hualuomoli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.hualuomoli.config.base.BaseComponentConfig;
import com.github.hualuomoli.config.base.Ds1Config;
import com.github.hualuomoli.config.base.Ds2Config;
import com.github.hualuomoli.config.base.JtaTransactionConfig;
import com.github.hualuomoli.config.base.SpringTransactionConfig;

/**
 * base
 */
@Configuration(value = "com.github.hualuomoli.config.BaseConfig")
@Import(value = { Ds1Config.class //
    , Ds2Config.class//
    , BaseComponentConfig.class //
    , SpringTransactionConfig.class //
    , JtaTransactionConfig.class })
public class BaseConfig {

}
