package com.github.hualuomoli.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 配置请求/响应消息转换
 */
@Configuration(value = "com.github.hualuomoli.config.mvc.ScheduleConfig")
@EnableScheduling
public class ScheduleConfig {
}
