package com.github.hualuomoli.sample.plugin.ws.server.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.sample.plugin.ws.api.entity.User;
import com.github.hualuomoli.sample.plugin.ws.server.entity.Data;

@Service(value = "com.github.hualuomoli.sample.plugin.ws.server.service.ServerServiceImpl")
public class ServerServiceImpl implements ServerService {

  private static final Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);

  @Override
  public String execute(String param) {
    logger.debug("[execute] param={}", param);
    return param;
  }

  @Override
  public String inObject(User user) {
    logger.debug("[inObject] user={}", user);
    return "inObject";
  }

  @Override
  public String inArray(List<User> users) {
    logger.debug("[inArray] users={}", users);
    return "inArray";
  }

  @Override
  public User outObject(String param) {
    logger.debug("[outObject] param={}", param);
    User user = new User();
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    return user;
  }

  @Override
  public List<User> outArray(String param) {
    logger.debug("[outArray] param={}", param);
    User user1 = new User();
    user1.setUsername("hualuomoli");
    user1.setNickname("花落莫离");
    User user2 = new User();
    user2.setUsername("jack");
    user2.setNickname("杰克");

    List<User> users = new ArrayList<User>();
    users.add(user1);
    users.add(user2);

    return users;
  }

  @Override
  public Data my(Data data) {
    logger.debug("[my] data={}", data);
    return data;
  }

}
