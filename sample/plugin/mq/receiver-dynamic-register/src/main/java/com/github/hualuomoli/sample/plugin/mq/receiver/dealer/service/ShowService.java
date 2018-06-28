package com.github.hualuomoli.sample.plugin.mq.receiver.dealer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "com.github.hualuomoli.sample.plugin.mq.receiver.dealer.service.ShowService")
public class ShowService {

  private static final Logger logger = LoggerFactory.getLogger(ShowService.class);

  public void show(Class<?> clazz, String message) {
    logger.info("{}={}", clazz.getName(), message);
    this.sleep(); // 模拟业务操作
  }

  private void sleep() {
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
    }
  }

}
