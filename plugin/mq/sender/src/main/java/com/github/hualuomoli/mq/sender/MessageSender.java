package com.github.hualuomoli.mq.sender;

import com.github.hualuomoli.mq.sender.lang.MessageSendException;

/**
 * 消息发送者
 */
public interface MessageSender {

  /**
   * 发送消息到消息队列,默认点对点
   * @param destinationName 消息名称
   * @param data 数据
   * @throws MessageSendException 发送失败
   */
  void send(String destinationName, final String data) throws MessageSendException;

  /**
   * 发送消息到消息队列
   * @param destinationName 消息名称
   * @param data 数据
   * @param type MQ类型
   * @throws MessageSendException 发送失败
   */
  void send(String destinationName, final String data, Type type) throws MessageSendException;

  // MQ类型
  static enum Type {
    /** 点对点 */
    QUEUE(),
    /** 广播 */
    TOPIC();

  }

}
