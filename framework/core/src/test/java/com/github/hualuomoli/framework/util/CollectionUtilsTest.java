package com.github.hualuomoli.framework.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionUtilsTest {

  private static final Logger logger = LoggerFactory.getLogger(CollectionUtilsTest.class);

  @Test
  public void testSplit() {
    List<User> users = new ArrayList<User>();
    for (int i = 0; i < 24; i++) {
      users.add(new User(i + 1));
    }
    logger.debug("split 24 by 3 is {}", CollectionUtils.split(users, 3));// 可以整除
    logger.debug("split 24 by 5 is {}", CollectionUtils.split(users, 5));// 不可以整除
  }

  @Test
  public void testFetch() {
    List<User> users = new ArrayList<User>();
    for (int i = 0; i < 24; i++) {
      users.add(new User(i + 1));
    }
    logger.debug("fetch 24 from 1 by 3 is {}", CollectionUtils.fetch(users, 1, 3));// 从头开始
    logger.debug("fetch 24 from 5 by 3 is {}", CollectionUtils.fetch(users, 5, 3));// 从中间位置开始
    logger.debug("fetch 24 from 22 by 3 is {}", CollectionUtils.fetch(users, 22, 3));// 最后的数据
    logger.debug("fetch 24 from 23 by 3 is {}", CollectionUtils.fetch(users, 23, 3));// 截取的长度不满足
  }

  private class User {
    private Integer id;

    public User(Integer id) {
      super();
      this.id = id;
    }

    @Override
    public String toString() {
      return String.valueOf(id);
    }
  }

}
