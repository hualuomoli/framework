package com.github.hualuomoli.sample.mq.sender.biz.service;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.mq.sender.jms.JmsMessageSender;
import com.github.hualuomoli.sample.mq.sender.base.entity.User;
import com.github.hualuomoli.sample.mq.sender.base.mapper.UserBaseMapper;
import com.github.hualuomoli.sample.mq.sender.base.service.UserBaseService;

@Service(value = "com.github.hualuomoli.sample.mq.sender.biz.service.JtaService")
@Transactional(readOnly = true)
public class JtaService {

  @Autowired
  private UserBaseMapper userBaseMapper;
  @Autowired
  private UserBaseService userBaseService;
  @Autowired
  private JmsTemplate jmsTemplate;
  @Autowired
  private JmsMessageSender jmsMessageSender;

  @Transactional(readOnly = false)
  public void execute() {
    User user = new User();
    user.setId(UUID.randomUUID().toString().replaceAll("[-]", ""));
    user.setAge(18);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    userBaseMapper.insert(user);

    jmsMessageSender.send("jta_user", user.getId());
    System.out.println("数据库执行成功,MQ发送成功");
  }

  @Transactional(readOnly = false)
  public void executeUseService() {
    User user = new User();
    user.setId(UUID.randomUUID().toString().replaceAll("[-]", ""));
    user.setAge(18);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    userBaseService.insert(user);

    jmsMessageSender.send("jta_user", user.getId());
    System.out.println("数据库执行成功,MQ发送成功");
  }

  @Transactional(readOnly = false)
  public void secondErrorForMq() {
    final User user = new User();
    user.setId(UUID.randomUUID().toString().replaceAll("[-]", ""));
    user.setAge(18);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");

    // 先插入数据
    userBaseMapper.insert(user);

    // 后发送MQ
    jmsTemplate.send("jta_user", new MessageCreator() {

      @Override
      public Message createMessage(Session session) throws JMSException {
        throw new RuntimeException("发送失败");
      }

    });

  }

  @Transactional(readOnly = false)
  public void secondErrorForDB() {
    User user = new User();
    user.setAge(18);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");

    // 先发送MQ
    jmsMessageSender.send("jta_user", user.getUsername());
    // 后插入数据
    userBaseMapper.insert(user);
  }

  @Transactional(readOnly = false)
  public void firstError() {
    User user = new User();
    user.setAge(18);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    userBaseMapper.insert(user);

    jmsMessageSender.send("jta_user", user.getUsername());
  }

}
