package com.github.hualuomoli.sample.framework.base.service;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.framework.entity.Page;
import com.github.hualuomoli.framework.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.sample.framework.base.entity.User;
import com.github.hualuomoli.sample.framework.base.mapper.UserBaseMapper;

// 用户
@Service(value = "com.github.hualuomoli.sample.framework.base.service.UserBaseService")
@Transactional(readOnly = true)
public class UserBaseService {

  @Autowired
  private UserBaseMapper userBaseMapper;

  /** 根据主键id查询 */
  public User get(java.lang.String id) {
    Validate.notNull(id, "id is null.");

    return userBaseMapper.get(id);
  }

  /** 根据唯一索引查询 */
  public User findByUsername(java.lang.String username) {
    Validate.notNull(username, "username is null.");

    User user = new User();
    user.setUsername(username);
    List<User> list = userBaseMapper.findList(user);
    if (list == null || list.size() == 0) {
      return null;
    }
    Validate.isTrue(list.size() == 1, "More Data found.");
    return list.get(0);
  }

  /** 根据唯一索引查询 */
  public User findByNicknameAndAge(java.lang.String nickname, java.lang.Integer age) {
    Validate.notNull(nickname, "nickname is null.");
    Validate.notNull(age, "age is null.");

    User user = new User();
    user.setNickname(nickname);
    user.setAge(age);
    List<User> list = userBaseMapper.findList(user);
    if (list == null || list.size() == 0) {
      return null;
    }
    Validate.isTrue(list.size() == 1, "More Data found.");
    return list.get(0);
  }

  /** 添加 */
  @Transactional(readOnly = false)
  public int insert(User user) {
    Validate.notNull(user, "user is null.");

    return userBaseMapper.insert(user);
  }

  /** 批量添加 */
  @Transactional(readOnly = false)
  public <T extends User> int batchInsert(List<T> list, int fetchSize) {
    if (list == null || list.size() == 0) {
      return 0;
    }
    return userBaseMapper.batchInsert(list);
  }

  /** 根据主键id修改 */
  @Transactional(readOnly = false)
  public int update(User user) {
    Validate.notNull(user, "user is null.");

    return userBaseMapper.update(user);
  }

  /** 根据唯一索引修改 */
  @Transactional(readOnly = false)
  public int updateByUsername(java.lang.String username, User user) {
    Validate.notNull(username, "username is null.");
    Validate.notNull(user, "user is null.");

    user.setUsername(username);
    return userBaseMapper.updateByUsername(user);
  }

  /** 根据唯一索引修改 */
  @Transactional(readOnly = false)
  public int updateByNicknameAndAge(java.lang.String nickname, java.lang.Integer age, User user) {
    Validate.notNull(nickname, "nickname is null.");
    Validate.notNull(age, "age is null.");
    Validate.notNull(user, "user is null.");

    user.setNickname(nickname);
    user.setAge(age);
    return userBaseMapper.updateByNicknameAndAge(user);
  }

  /** 根据主键删除 */
  @Transactional(readOnly = false)
  public int delete(java.lang.String id) {
    Validate.notNull(id, "id is null.");

    return userBaseMapper.delete(id);
  }

  /** 根据唯一索引删除 */
  @Transactional(readOnly = false)
  public int deleteByUsername(java.lang.String username) {
    Validate.notNull(username, "username is null.");

    User user = new User();
    user.setUsername(username);
    return userBaseMapper.deleteByUsername(user);
  }

  /** 根据唯一索引删除 */
  @Transactional(readOnly = false)
  public int deleteByNicknameAndAge(java.lang.String nickname, java.lang.Integer age) {
    Validate.notNull(nickname, "nickname is null.");
    Validate.notNull(age, "age is null.");

    User user = new User();
    user.setNickname(nickname);
    user.setAge(age);
    return userBaseMapper.deleteByNicknameAndAge(user);
  }

  /** 根据主键批量删除 */
  @Transactional(readOnly = false)
  public int deleteByArray(java.lang.String[] ids) {
    if (ids == null || ids.length == 0) {
      return 0;
    }
    return userBaseMapper.deleteByArray(ids);
  }

  /** 查询列表 */
  public List<User> findList(User user) {
    Validate.notNull(user, "user is null.");

    return userBaseMapper.findList(user);
  }

  /** 查询列表排序 */
  public List<User> findList(User user, String orderBy) {
    Validate.notNull(user, "user is null.");
    Validate.notNull(orderBy, "orderBy is blank.");

    // 设置排序
    PaginationInterceptor.setListOrder(orderBy);
    // 查询列表
    return userBaseMapper.findList(user);
  }

  /** 查询分页 */
  public Page findPage(User user, Integer pageNo, Integer pageSize) {
    Validate.notNull(user, "user is null.");
    Validate.notNull(pageNo, "pageNo is null.");
    Validate.isTrue(pageNo > 0, "invalid pageNo.");
    Validate.notNull(pageSize, "pageSize is null.");
    Validate.isTrue(pageSize > 0, "invalid pageSize.");

    // 设置分页
    PaginationInterceptor.setPagination(pageNo, pageSize);
    // 查询
    List<User> list = userBaseMapper.findList(user);
    // 总数量
    int count = PaginationInterceptor.getCount();
    // 组装
    return new Page(pageNo, pageSize, count, list);
  }

  /** 查询分页 */
  public Page findPage(User user, Integer pageNo, Integer pageSize, String orderBy) {
    Validate.notNull(user, "user is null.");
    Validate.notNull(pageNo, "pageNo is null.");
    Validate.isTrue(pageNo > 0, "invalid pageNo.");
    Validate.notNull(pageSize, "pageSize is null.");
    Validate.isTrue(pageSize > 0, "invalid pageSize.");
    Validate.notNull(orderBy, "orderBy is blank.");

    // 设置分页
    PaginationInterceptor.setPagination(pageNo, pageSize, orderBy);
    // 查询
    List<User> list = userBaseMapper.findList(user);
    // 总数量
    int count = PaginationInterceptor.getCount();
    // 组装
    return new Page(pageNo, pageSize, count, list);
  }

}
