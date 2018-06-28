package com.github.hualuomoli.sample.atomikos.ds2.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.framework.entity.Page;
import com.github.hualuomoli.framework.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.sample.atomikos.ds2.entity.Address;
import com.github.hualuomoli.sample.atomikos.ds2.query.entity.AddressQuery;
import com.github.hualuomoli.sample.atomikos.ds2.query.mapper.AddressQueryMapper;

// 收货地址
@Service(value = "com.github.hualuomoli.sample.atomikos.ds2.query.service.AddressQueryService")
@Transactional(readOnly = true)
public class AddressQueryService {

  @Autowired
  private AddressQueryMapper addressQueryMapper;

  /** 查询列表 */
  public List<Address> findList(AddressQuery addressQuery) {
    return addressQueryMapper.findList(addressQuery);
  }

  /** 查询列表排序 */
  public List<Address> findList(AddressQuery addressQuery, String orderBy) {
    // 设置排序
    PaginationInterceptor.setListOrder(orderBy);
    // 查询列表
    return addressQueryMapper.findList(addressQuery);
  }

  /** 查询分页 */
  public Page findPage(AddressQuery addressQuery, Integer pageNo, Integer pageSize) {
    // 设置分页
    PaginationInterceptor.setPagination(pageNo, pageSize);
    // 查询
    List<Address> list = addressQueryMapper.findList(addressQuery);
    // 总数量
    int count = PaginationInterceptor.getCount();
    // 组装
    return new Page(pageNo, pageSize, count, list);
  }

  /** 查询分页 */
  public Page findPage(AddressQuery addressQuery, Integer pageNo, Integer pageSize, String orderBy) {
    // 设置分页
    PaginationInterceptor.setPagination(pageNo, pageSize, orderBy);
    // 查询
    List<Address> list = addressQueryMapper.findList(addressQuery);
    // 总数量
    int count = PaginationInterceptor.getCount();
    // 组装
    return new Page(pageNo, pageSize, count, list);
  }

}
