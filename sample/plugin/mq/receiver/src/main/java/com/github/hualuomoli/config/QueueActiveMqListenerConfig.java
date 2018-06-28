package com.github.hualuomoli.config;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.github.hualuomoli.mq.receiver.TextMessageListener;
import com.github.hualuomoli.sample.plugin.mq.receiver.dealer.queue.QueueMessageDealer;

@Configuration(value = "com.github.hualuomoli.config.QueueActiveMqListenerConfig")
@Import(value = { ConnectionConfig.class, BaseComponentConfig.class })
public class QueueActiveMqListenerConfig {

  @Resource(name = "receiveConnectionFactory")
  private ConnectionFactory receiveConnectionFactory;

  @Autowired
  private QueueMessageDealer queueMessageDealer;

  @Bean
  public DefaultMessageListenerContainer queueActiveMqListener1() {
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(receiveConnectionFactory);
    container.setMessageListener(new TextMessageListener(queueMessageDealer));
    container.setDestinationName("sample_queue");
    container.setConcurrentConsumers(3); // 同时启动3个Listener实例来消费消息。
    container.setMaxConcurrentConsumers(5); // 最大启动5个Listener实例来消费消息。
    //    container.setConcurrency("5-20"); // 最小并发数是5，最大并发数为20
    return container;
  }

}
