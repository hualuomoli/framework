package com.github.hualuomoli.sample.framework.base.service;

import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.framework.entity.Page;
import com.github.hualuomoli.sample.enums.StateEnum;
import com.github.hualuomoli.sample.enums.StatusEnum;
import com.github.hualuomoli.sample.framework.ServiceRollbackTest;
import com.github.hualuomoli.sample.framework.base.entity.User;
import com.google.common.collect.Lists;

public class UserBaseServiceTest extends ServiceRollbackTest {

  @Autowired
  private UserBaseService userBaseService;

  @Test
  public void testGet() {
    // 添加
    String id = this.getId();
    User user = new User();
    user.setId(id);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    user.setAge(18);
    user.setSex("M");
    user.setState(StateEnum.NOMAL);
    user.setStatus(StatusEnum.NOMAL);
    user.setRemark("备注信息");
    int updated = userBaseService.insert(user);
    Assert.assertEquals(1, updated);

    // 根据主键查询
    User u = userBaseService.get(id);
    Assert.assertNotNull(u);
    Assert.assertEquals("hualuomoli", u.getUsername());
    Assert.assertEquals("花落莫离", u.getNickname());
  }

  @Test
  public void testFindByUsername() {
    // 添加
    String id = this.getId();
    User user = new User();
    user.setId(id);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    user.setAge(18);
    user.setSex("M");
    user.setState(StateEnum.NOMAL);
    user.setStatus(StatusEnum.NOMAL);
    user.setRemark("备注信息");
    int updated = userBaseService.insert(user);
    Assert.assertEquals(1, updated);

    // 根据唯一索引查询
    User u = userBaseService.findByUsername("hualuomoli");
    Assert.assertNotNull(u);
    Assert.assertEquals(id, u.getId());
    Assert.assertEquals("花落莫离", u.getNickname());
  }

  @Test
  public void testInsert() {
    String id = this.getId();
    User user = new User();
    user.setId(id);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    user.setAge(18);
    user.setSex("M");
    user.setState(StateEnum.NOMAL);
    user.setStatus(StatusEnum.NOMAL);
    user.setRemark("备注信息");
    int updated = userBaseService.insert(user);
    Assert.assertEquals(1, updated);
  }

  @Test
  public void testBatchInsert() {
    List<User> users = Lists.newArrayList();
    for (int i = 0; i < 100; i++) {
      User user = new User();
      user.setId(this.getId());
      user.setUsername("hualuomoli" + i);
      user.setNickname("花落莫离" + i);
      user.setAge(18);
      user.setSex("M");
      user.setState(StateEnum.NOMAL);
      user.setStatus(StatusEnum.NOMAL);
      user.setRemark("备注信息");
      users.add(user);
    }
    int updated = userBaseService.batchInsert(users, users.size());
    Assert.assertEquals(100, updated);
  }

  @Test
  public void testUpdate() {
    String id = this.getId();
    User user = new User();
    user.setId(id);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    user.setAge(18);
    user.setSex("M");
    user.setState(StateEnum.NOMAL);
    user.setStatus(StatusEnum.NOMAL);
    user.setRemark("备注信息");
    int updated = userBaseService.insert(user);
    Assert.assertEquals(1, updated);

    // 根据主键修改
    User update = new User();
    update.setId(id);
    update.setNickname("测试");
    updated = userBaseService.update(update);
    Assert.assertEquals(1, updated);

    // 获取
    User u = userBaseService.findByUsername("hualuomoli");
    Assert.assertNotNull(u);
    Assert.assertEquals(id, u.getId());
    Assert.assertEquals("hualuomoli", u.getUsername());
    Assert.assertEquals("测试", u.getNickname());
  }

  @Test
  public void testUpdateByUsername() {
    String id = this.getId();
    User user = new User();
    user.setId(id);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    user.setAge(18);
    user.setSex("M");
    user.setState(StateEnum.NOMAL);
    user.setStatus(StatusEnum.NOMAL);
    user.setRemark("备注信息");
    int updated = userBaseService.insert(user);
    Assert.assertEquals(1, updated);

    // 根据主键修改
    User update = new User();
    update.setRemark("备注修改");
    updated = userBaseService.updateByUsername("hualuomoli", update);
    Assert.assertEquals(1, updated);

    // 获取
    User u = userBaseService.get(id);
    Assert.assertNotNull(u);
    Assert.assertEquals(id, u.getId());
    Assert.assertEquals("hualuomoli", u.getUsername());
    Assert.assertEquals("备注修改", u.getRemark());
  }

  @Test
  public void testDelete() {
    String id = this.getId();
    User user = new User();
    user.setId(id);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    user.setAge(18);
    user.setSex("M");
    user.setState(StateEnum.NOMAL);
    user.setStatus(StatusEnum.NOMAL);
    user.setRemark("备注信息");
    int updated = userBaseService.insert(user);
    Assert.assertEquals(1, updated);

    updated = userBaseService.delete(id);
    Assert.assertEquals(1, updated);
  }

  @Test
  public void testDeleteByUsername() {
    String id = this.getId();
    User user = new User();
    user.setId(id);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    user.setAge(18);
    user.setSex("M");
    user.setState(StateEnum.NOMAL);
    user.setStatus(StatusEnum.NOMAL);
    user.setRemark("备注信息");
    int updated = userBaseService.insert(user);
    Assert.assertEquals(1, updated);

    updated = userBaseService.deleteByUsername("hualuomoli");
    Assert.assertEquals(1, updated);
  }

  @Test
  public void testDeleteByArray() {
    List<String> ids = Lists.newArrayList();
    // 添加
    List<User> users = Lists.newArrayList();
    for (int i = 0; i < 100; i++) {
      String id = this.getId();
      User user = new User();
      user.setId(id);
      user.setUsername("hualuomoli" + i);
      user.setNickname("花落莫离" + i);
      user.setAge(18);
      user.setSex("M");
      user.setState(StateEnum.NOMAL);
      user.setStatus(StatusEnum.NOMAL);
      user.setRemark("备注信息");
      users.add(user);
      ids.add(id);
    }
    int updated = userBaseService.batchInsert(users, users.size());
    Assert.assertEquals(100, updated);

    // 根据主键数组删除
    updated = userBaseService.deleteByArray(ids.toArray(new String[] {}));
    Assert.assertEquals(100, updated);
  }

  @Test
  public void testFindListUser() {
    // 添加
    List<User> users = Lists.newArrayList();
    for (int i = 0; i < 100; i++) {
      User user = new User();
      user.setId(this.getId());
      user.setUsername("hualuomoli" + i);
      user.setNickname("花落莫离" + i);
      user.setAge(18 + (i % 3));
      user.setSex(i % 2 == 0 ? "M" : "F");
      user.setState(StateEnum.NOMAL);
      user.setStatus(StatusEnum.NOMAL);
      user.setRemark("备注信息");
      users.add(user);
    }
    int updated = userBaseService.batchInsert(users, users.size());
    Assert.assertEquals(100, updated);

    // 16 * (3 * 2) + 4 = 100
    User user = new User();
    user.setAge(18);
    user.setSex("M");
    List<User> list = userBaseService.findList(user);
    Assert.assertEquals(17, list.size());
  }

  @Test
  public void testFindListUserString() {
    // 添加
    List<User> users = Lists.newArrayList();
    for (int i = 0; i < 100; i++) {
      User user = new User();
      user.setId(this.getId());
      user.setUsername("hualuomoli" + i);
      user.setNickname("花落莫离" + i);
      user.setAge(18 + (i % 3));
      user.setSex(i % 2 == 0 ? "M" : "F");
      user.setState(StateEnum.NOMAL);
      user.setStatus(StatusEnum.NOMAL);
      user.setRemark("备注信息");
      users.add(user);
    }
    int updated = userBaseService.batchInsert(users, users.size());
    Assert.assertEquals(100, updated);

    // 16 * (3 * 2) + 4 = 100
    User user = new User();
    user.setAge(18);
    user.setSex("M");
    List<User> list = userBaseService.findList(user, "age desc, sex asc");
    Assert.assertEquals(17, list.size());
  }

  @Test
  public void testFindPageUserIntegerInteger() {
    // 添加
    List<User> users = Lists.newArrayList();
    for (int i = 0; i < 100; i++) {
      User user = new User();
      user.setId(this.getId());
      user.setUsername("hualuomoli" + i);
      user.setNickname("花落莫离" + i);
      user.setAge(18 + (i % 3));
      user.setSex(i % 2 == 0 ? "M" : "F");
      user.setState(StateEnum.NOMAL);
      user.setStatus(StatusEnum.NOMAL);
      user.setRemark("备注信息");
      users.add(user);
    }
    int updated = userBaseService.batchInsert(users, users.size());
    Assert.assertEquals(100, updated);

    // 16 * (3 * 2) + 4 = 100
    User user = new User();
    user.setAge(18);
    user.setSex("M");
    Page page = userBaseService.findPage(user, 2, 10);
    List<User> list = page.getDataList();
    Assert.assertEquals(17, page.getCount().intValue());
    Assert.assertEquals(7, list.size());
    Assert.assertEquals(2, page.getPageNumber().intValue());
    Assert.assertEquals(10, page.getPageSize().intValue());
  }

  @Test
  public void testFindPageUserIntegerIntegerString() {
    // 添加
    List<User> users = Lists.newArrayList();
    for (int i = 0; i < 100; i++) {
      User user = new User();
      user.setId(this.getId());
      user.setUsername("hualuomoli" + i);
      user.setNickname("花落莫离" + i);
      user.setAge(18 + (i % 3));
      user.setSex(i % 2 == 0 ? "M" : "F");
      user.setState(StateEnum.NOMAL);
      user.setStatus(StatusEnum.NOMAL);
      user.setRemark("备注信息");
      users.add(user);
    }
    int updated = userBaseService.batchInsert(users, users.size());
    Assert.assertEquals(100, updated);

    // 16 * (3 * 2) + 4 = 100
    User user = new User();
    user.setAge(18);
    user.setSex("M");
    Page page = userBaseService.findPage(user, 2, 10, "age desc, sex asc");
    List<User> list = page.getDataList();
    Assert.assertEquals(17, page.getCount().intValue());
    Assert.assertEquals(7, list.size());
    Assert.assertEquals(2, page.getPageNumber().intValue());
    Assert.assertEquals(10, page.getPageSize().intValue());
  }

  private String getId() {
    return UUID.randomUUID().toString().replaceAll("[-]", "");
  }

}
