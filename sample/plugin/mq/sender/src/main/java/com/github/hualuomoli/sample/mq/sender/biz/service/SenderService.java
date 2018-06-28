package com.github.hualuomoli.sample.mq.sender.biz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.mq.sender.MessageSender.Type;
import com.github.hualuomoli.mq.sender.jms.JmsMessageSender;

@Service(value = "com.github.hualuomoli.sample.mq.sender.biz.service.SenderService")
@Transactional(readOnly = true)
public class SenderService {

  private static final Logger logger = LoggerFactory.getLogger(SenderService.class);

  @Autowired
  private JmsMessageSender jmsMessageSender;

  @Transactional(readOnly = false)
  public void batchQueue(int count, String timestamp) {

    for (int i = 1; i <= count; i++) {
      jmsMessageSender.send("sample_queue", i + " " + timestamp + " 单点message");
    }

    logger.info("batch send {} queue data to mq", count);
  }

  @Transactional(readOnly = false)
  public void batchTopic(int count, String timestamp) {

    for (int i = 1; i <= count; i++) {
      jmsMessageSender.send("sample_topic", i + " " + timestamp + " 广播message", Type.TOPIC);
    }

    logger.info("batch send {} topic data to mq", count);
  }

}
