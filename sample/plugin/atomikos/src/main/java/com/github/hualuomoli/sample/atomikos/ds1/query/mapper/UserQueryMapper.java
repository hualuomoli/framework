package com.github.hualuomoli.sample.atomikos.ds1.query.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.hualuomoli.sample.atomikos.ds1.entity.User;
import com.github.hualuomoli.sample.atomikos.ds1.query.entity.UserQuery;

// 用户信息
@Repository(value = "com.github.hualuomoli.sample.atomikos.ds1.query.mapper.UserQueryMapper")
public interface UserQueryMapper {

  /** 查询列表 */
  List<User> findList(UserQuery userQuery);

}
