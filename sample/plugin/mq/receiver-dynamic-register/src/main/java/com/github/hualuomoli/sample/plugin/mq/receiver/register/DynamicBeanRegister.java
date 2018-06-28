package com.github.hualuomoli.sample.plugin.mq.receiver.register;

import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.github.hualuomoli.mq.receiver.MessageDealer;
import com.github.hualuomoli.mq.receiver.TextMessageListener;
import com.github.hualuomoli.sample.plugin.mq.receiver.anno.Anno;

@Component
public class DynamicBeanRegister implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Resource(name = "receiveConnectionFactory")
  private ConnectionFactory receiveConnectionFactory;

  @PostConstruct
  public void init() {

    //将applicationContext转换为ConfigurableApplicationContext  
    ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

    // 获取bean工厂并转换为DefaultListableBeanFactory  
    DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

    Map<String, MessageDealer> dealerMap = applicationContext.getBeansOfType(MessageDealer.class);
    Collection<MessageDealer> dealers = dealerMap.values();
    String name = MessageDealer.class.getName();
    int index = 1;

    for (MessageDealer dealer : dealers) {
      Anno anno = dealer.getClass().getAnnotation(Anno.class);

      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DefaultMessageListenerContainer.class);
      builder.addPropertyValue("connectionFactory", receiveConnectionFactory);
      builder.addPropertyValue("destinationName", anno.destinationName());
      builder.addPropertyValue("messageListener", new TextMessageListener(dealer));

      if (StringUtils.isNotBlank(anno.clientId())) {
        builder.addPropertyValue("clientId", anno.clientId());
        builder.addPropertyValue("pubSubDomain", true);
        builder.addPropertyValue("subscriptionDurable", true);
      } else {
        builder.addPropertyValue("concurrency", "5-20");
      }

      // register
      defaultListableBeanFactory.registerBeanDefinition(name + "$" + (index++), builder.getRawBeanDefinition());
    }

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
