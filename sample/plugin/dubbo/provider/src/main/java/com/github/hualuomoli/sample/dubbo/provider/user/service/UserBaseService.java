package com.github.hualuomoli.sample.dubbo.provider.user.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.sample.dubbo.api.entity.Address;
import com.github.hualuomoli.sample.dubbo.api.entity.User;

// 验证spring注解
@Service(value = "com.github.hualuomoli.sample.dubbo.provider.user.service.UserBaseService")
public class UserBaseService {

  private static final Logger logger = LoggerFactory.getLogger(UserBaseService.class);

  // 入参简单类型,出参复杂类型
  public User get(String username) {

    logger.info("[get]");

    User user = new User();
    user.setUsername(username);
    user.setNickname("用户昵称");
    user.setHobbys(this.newArrayList("games", "playing"));

    Address address1 = new Address();
    address1.setAreaCode("370203");
    address1.setProvinceName("山东省");
    address1.setCityName("青岛市");
    address1.setCountyName("市北区");

    Address address2 = new Address();
    address2.setAreaCode("370203");
    address2.setProvinceName("山东省");
    address2.setCityName("青岛市");
    address2.setCountyName("市北区");

    user.setAddresses(this.newHashSet(address1, address2));

    return user;
  }

  // 入参复杂类型,出参简单类型
  public String create(User user) {

    logger.info("[create]");

    return user.getUsername();
  }

  // 多参数入参
  public boolean login(String username, String password) {

    logger.info("[login]");

    return true;
  }

  public Object[] result() {

    logger.info("[result]");

    Address address = new Address();
    address.setAreaCode("370828");

    return new Object[] { address, "tester", 1 };
  }

  @SuppressWarnings("unchecked")
  private <T> List<T> newArrayList(T... objs) {
    List<T> list = new ArrayList<T>();
    if (objs == null || objs.length == 0) {
      return list;
    }
    for (T obj : objs) {
      list.add(obj);
    }
    return list;
  }

  @SuppressWarnings("unchecked")
  private <T> Set<T> newHashSet(T... objs) {
    Set<T> list = new HashSet<T>();
    if (objs == null || objs.length == 0) {
      return list;
    }
    for (T obj : objs) {
      list.add(obj);
    }
    return list;
  }

}
