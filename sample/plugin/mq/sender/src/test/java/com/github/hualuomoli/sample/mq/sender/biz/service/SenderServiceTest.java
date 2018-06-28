package com.github.hualuomoli.sample.mq.sender.biz.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.sample.mq.sender.ServiceTest;

public class SenderServiceTest extends ServiceTest {

  @Autowired
  private SenderService senderService;

  @Test
  public void testBatchQueue() {
    String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    int count = (int) (Math.random() * 50) + 10;
    logger.info("timestamp={}", timestamp);
    logger.info("count={}", count);

    senderService.batchQueue(count, timestamp);
  }

  @Test
  public void testBatchTopic() {
    String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    int count = (int) (Math.random() * 30) + 20;
    logger.info("timestamp={}", timestamp);
    logger.info("count={}", count);

    senderService.batchTopic(count, timestamp);
  }

}
