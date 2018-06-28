package com.github.hualuomoli.sample.atomikos.ds2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.sample.atomikos.ds2.entity.Address;

// 收货地址
@Repository(value = "com.github.hualuomoli.sample.atomikos.ds2.mapper.AddressBaseMapper")
public interface AddressBaseMapper {

  /** 根据主键id查询 */
  Address get(java.lang.String id);

  /** 添加 */
  int insert(Address address);

  /** 批量添加 */
  <T extends Address> int batchInsert(@Param(value = "list") List<T> list);

  /** 根据主键修改 */
  int update(Address address);

  /** 根据主键删除 */
  int delete(java.lang.String id);

  /** 根据主键批量删除 */
  int deleteByArray(@Param(value = "ids") java.lang.String[] id);

  /** 查询列表 */
  List<Address> findList(Address address);

}
