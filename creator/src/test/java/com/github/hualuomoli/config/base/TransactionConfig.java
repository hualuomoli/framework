package com.github.hualuomoli.config.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启用注解事务管理，使用CGLib代理
 */
@Configuration(value = "com.github.hualuomoli.config.base.TransactionConfig")
@EnableTransactionManagement(proxyTargetClass = true)
public class TransactionConfig {

  static {
    System.out.println("初始化spring transaction");
  }

}
