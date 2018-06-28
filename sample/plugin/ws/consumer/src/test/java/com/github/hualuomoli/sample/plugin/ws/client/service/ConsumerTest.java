package com.github.hualuomoli.sample.plugin.ws.client.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.hualuomoli.config.BaseComponentConfig;
import com.github.hualuomoli.config.WsClientConfig;
import com.github.hualuomoli.sample.plugin.ws.api.entity.User;
import com.github.hualuomoli.sample.plugin.ws.consumer.entity.Data;
import com.github.hualuomoli.sample.plugin.ws.consumer.service.ServerService;

@WebAppConfiguration
@ContextConfiguration(classes = { BaseComponentConfig.class, WsClientConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConsumerTest {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerTest.class);

  @Autowired
  private ServerService serverService;

  @Test
  public void testExecute() {
    String result = serverService.execute("this is parameter.");
    logger.debug("[execute] result={}", result);
  }

  @Test
  public void testInObject() {
    User user = new User();
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    String result = serverService.inObject(user);
    logger.debug("[inObject] result={}", result);
  }

  @Test
  public void testInArray() {
    User user1 = new User();
    user1.setUsername("hualuomoli");
    user1.setNickname("花落莫离");
    User user2 = new User();
    user2.setUsername("jack");
    user2.setNickname("杰克");

    List<User> users = new ArrayList<User>();
    users.add(user1);
    users.add(user2);
    String result = serverService.inArray(users);
    logger.debug("[inArray] result={}", result);
  }

  @Test
  public void testOutObject() {
    User user = serverService.outObject("this is parameter.");
    logger.debug("[outObject] user={}", user);
  }

  @Test
  public void testOutArray() {
    List<User> users = serverService.outArray("this is parameter.");
    logger.debug("[outArray] users={}", users);
  }

  @Test
  public void testMy() {
    Data data = new Data();
    data.setUsername("hualuomoli");
    data.setAge(18);
    Data d = serverService.my(data);
    logger.debug("[my] data={}", d);
  }

}
