package com.github.hualuomoli.config.base.jms;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.github.hualuomoli.util.ProjectConfig;

// http://blog.csdn.net/kkdelta/article/details/7264752
@Configuration(value = "com.github.hualuomoli.config.base.jms.ConnectionFactoryConfig")
public class ConnectionFactoryConfig {

  @Autowired
  private ActiveMQXAConnectionFactory amqConnectionFactory;

  // MQ
  @Bean(name = "amqConnectionFactory")
  public ActiveMQXAConnectionFactory amqConnectionFactory() {
    String username = ProjectConfig.getString("activemq.username");
    String password = ProjectConfig.getString("activemq.password");
    String brokerURL = ProjectConfig.getString("activemq.brokerURL");
    ActiveMQXAConnectionFactory amqConnectionFactory = new ActiveMQXAConnectionFactory(username, password, brokerURL);

    return amqConnectionFactory;
  }

  @Bean(name = "atomikosConnectionFactory", initMethod = "init", destroyMethod = "close")
  public AtomikosConnectionFactoryBean atomikosConnectionFactory() {
    AtomikosConnectionFactoryBean atomikosConnectionFactory = new AtomikosConnectionFactoryBean();
    atomikosConnectionFactory.setUniqueResourceName("activemq");
    atomikosConnectionFactory.setXaConnectionFactory(amqConnectionFactory);
    atomikosConnectionFactory.setPoolSize(ProjectConfig.getInteger("activemq.pool.size"));

    return atomikosConnectionFactory;
  }

}
