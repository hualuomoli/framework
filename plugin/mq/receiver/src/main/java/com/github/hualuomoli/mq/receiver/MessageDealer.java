package com.github.hualuomoli.mq.receiver;

/**
 * 消息处理者
 */
public interface MessageDealer {

  /**
   * 处理消息
   * @param content 消息信息
   */
  void onMessage(String content);

}
