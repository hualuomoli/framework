package com.github.hualuomoli.mq.sender.lang;

/**
 * 消息发送错误
 */
public class MessageSendException extends RuntimeException {

  private static final long serialVersionUID = -1375239270026797095L;

  public MessageSendException() {
    super();
  }

  public MessageSendException(String message, Throwable cause) {
    super(message, cause);
  }

  public MessageSendException(String message) {
    super(message);
  }

  public MessageSendException(Throwable cause) {
    super(cause);
  }

}
