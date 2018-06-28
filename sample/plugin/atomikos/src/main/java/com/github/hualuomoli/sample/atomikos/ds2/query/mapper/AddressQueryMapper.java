package com.github.hualuomoli.sample.atomikos.ds2.query.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.hualuomoli.sample.atomikos.ds2.entity.Address;
import com.github.hualuomoli.sample.atomikos.ds2.query.entity.AddressQuery;

// 收货地址
@Repository(value = "com.github.hualuomoli.sample.atomikos.ds2.query.mapper.AddressQueryMapper")
public interface AddressQueryMapper {

  /** 查询列表 */
  List<Address> findList(AddressQuery addressQuery);

}
