package com.github.hualuomoli.config.base.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;

import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.github.hualuomoli.mq.sender.jms.JmsMessageSender;

@Configuration(value = "com.github.hualuomoli.config.base.jms.JmsTemplateConfig")
@Import(value = { ConnectionFactoryConfig.class })
public class JmsTemplateConfig {

  @Autowired
  private AtomikosConnectionFactoryBean atomikosConnectionFactory;
  @Autowired
  private JmsTemplate jmsTemplate;

  @Bean(name = "jmsTemplate")
  public JmsTemplate jmsTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate();
    jmsTemplate.setConnectionFactory(atomikosConnectionFactory);
    jmsTemplate.setDefaultDestinationName("destination");
    jmsTemplate.setSessionTransacted(true);
    jmsTemplate.setReceiveTimeout(10000);

    return jmsTemplate;
  }

  // 简单的JMS封装
  @Bean(name = "jmsMessageSender")
  public JmsMessageSender jmsMessageSender() {
    JmsMessageSender jmsMessageSender = new JmsMessageSender();
    jmsMessageSender.setJmsTemplate(jmsTemplate);

    return jmsMessageSender;
  }

}
