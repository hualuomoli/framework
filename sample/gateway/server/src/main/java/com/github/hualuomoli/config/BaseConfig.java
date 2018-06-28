package com.github.hualuomoli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.hualuomoli.config.base.BaseComponentConfig;
import com.github.hualuomoli.config.base.TransactionConfig;

/**
 * base
 */
@Configuration(value = "com.github.hualuomoli.config.BaseConfig")
@Import(value = { BaseComponentConfig.class, TransactionConfig.class })
public class BaseConfig {

}
