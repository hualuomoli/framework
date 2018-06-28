package com.github.hualuomoli.config.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(value = "com.github.hualuomoli.config.base.JmsConfig")
@Import(value = {})
public class JmsConfig {

}
