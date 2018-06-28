package com.github.hualuomoli.config;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.github.hualuomoli.mq.receiver.TextMessageListener;
import com.github.hualuomoli.sample.plugin.mq.receiver.dealer.topic.TopicMessageDealer;

@Configuration(value = "com.github.hualuomoli.config.TopicActiveMqListenerConfig")
@Import(value = { ConnectionConfig.class, BaseComponentConfig.class })
public class TopicActiveMqListenerConfig {

  @Resource(name = "receiveConnectionFactory")
  private ConnectionFactory receiveConnectionFactory;

  @Autowired
  private TopicMessageDealer topicMessageDealer;

  @Bean
  public DefaultMessageListenerContainer topicActiveMqListener1() {
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(receiveConnectionFactory);
    container.setMessageListener(new TextMessageListener(topicMessageDealer));
    container.setDestinationName("sample_topic");

    container.setClientId("clientId");
    container.setPubSubDomain(true);
    container.setSubscriptionDurable(true);

    return container;
  }

}
