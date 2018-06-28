package com.github.hualuomoli.mq.receiver;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

public class TextMessageListener implements SessionAwareMessageListener<TextMessage> {

  private MessageDealer messageDealer;

  public TextMessageListener(MessageDealer messageDealer) {
    this.messageDealer = messageDealer;
  }

  @Override
  public void onMessage(TextMessage message, Session session) throws JMSException {
    String data = message.getText();
    try {
      messageDealer.onMessage(data);
    } catch (Exception e) {
      throw new JMSException(e.getMessage());
    }
  }

}
