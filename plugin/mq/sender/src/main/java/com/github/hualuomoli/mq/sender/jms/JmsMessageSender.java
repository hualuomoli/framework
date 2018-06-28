package com.github.hualuomoli.mq.sender.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.github.hualuomoli.mq.sender.MessageSender;
import com.github.hualuomoli.mq.sender.lang.MessageSendException;

/**
 * 使用Spring的JMS发送消息队列
 */
public class JmsMessageSender implements MessageSender {

  private static final Logger logger = LoggerFactory.getLogger(JmsMessageSender.class);

  private JmsTemplate jmsTemplate;

  public void setJmsTemplate(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Override
  public void send(String destinationName, String data) throws MessageSendException {
    this.send(destinationName, data, Type.QUEUE);
  }

  @Override
  public void send(String destinationName, final String data, Type type) throws MessageSendException {

    switch (type) {
    case QUEUE:
      jmsTemplate.setPubSubDomain(false);
      break;
    case TOPIC:
      jmsTemplate.setPubSubDomain(true);
      break;
    default:
      break;
    }

    logger.debug("发送数据到MQ,type={},destinationName={},data={}", type, destinationName, data);

    try {
      jmsTemplate.send(destinationName, new MessageCreator() {

        @Override
        public Message createMessage(Session session) throws JMSException {
          return session.createTextMessage(data);
        }
      });
    } catch (JmsException e) {
      throw new MessageSendException(e);
    }

  }

}
