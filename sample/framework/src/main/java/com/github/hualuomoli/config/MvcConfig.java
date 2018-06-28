package com.github.hualuomoli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.hualuomoli.config.mvc.ExceptionConfig;
import com.github.hualuomoli.config.mvc.FileuploadConfig;
import com.github.hualuomoli.config.mvc.MvcAspectConfig;
import com.github.hualuomoli.config.mvc.MvcComponentConfig;
import com.github.hualuomoli.config.mvc.ScheduleConfig;
import com.github.hualuomoli.config.mvc.ViewConfig;
import com.github.hualuomoli.config.mvc.WebMvcSupportConfig;

/**
 * mvc
 */
@Configuration(value = "com.github.hualuomoli.config.MvcConfig")
@Import(value = { ExceptionConfig.class //
    , FileuploadConfig.class //
    , MvcAspectConfig.class //
    , MvcComponentConfig.class//
    , WebMvcSupportConfig.class //
    , ViewConfig.class //
    , ScheduleConfig.class //
})
public class MvcConfig {

}
