package com.github.hualuomoli.sample.atomikos.biz.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.sample.atomikos.ds1.entity.User;
import com.github.hualuomoli.sample.atomikos.ds1.mapper.UserBaseMapper;
import com.github.hualuomoli.sample.atomikos.ds1.service.UserBaseService;
import com.github.hualuomoli.sample.atomikos.ds2.entity.Address;
import com.github.hualuomoli.sample.atomikos.ds2.mapper.AddressBaseMapper;
import com.github.hualuomoli.sample.atomikos.ds2.service.AddressBaseService;

@Service(value = "com.github.hualuomoli.sample.atomikos.biz.service.JtaService")
@Transactional(readOnly = true)
public class JtaService {

  @Autowired
  private UserBaseMapper userBaseMapper;
  @Autowired
  private UserBaseService userBaseService;
  @Autowired
  private AddressBaseMapper addressBaseMapper;
  @Autowired
  private AddressBaseService addressBaseService;

  @Transactional(readOnly = false)
  public void execute() {
    User user = new User();
    user.setId(UUID.randomUUID().toString().replaceAll("[-]", ""));
    user.setAge(18);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    userBaseMapper.insert(user);

    Address address = new Address();
    address.setId(UUID.randomUUID().toString().replaceAll("[-]", ""));
    address.setAreaCode("370203");
    address.setProvince("山东省");
    address.setCity("青岛市");
    address.setCounty("市北区");
    address.setDetailAddress("合肥路666号");
    addressBaseMapper.insert(address);
  }

  @Transactional(readOnly = false)
  public void executeUserService() {
    User user = new User();
    user.setId(UUID.randomUUID().toString().replaceAll("[-]", ""));
    user.setAge(18);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    userBaseService.insert(user);

    Address address = new Address();
    address.setId(UUID.randomUUID().toString().replaceAll("[-]", ""));
    address.setAreaCode("370203");
    address.setProvince("山东省");
    address.setCity("青岛市");
    address.setCounty("市北区");
    address.setDetailAddress("合肥路666号");
    addressBaseService.insert(address);
  }

  @Transactional(readOnly = false)
  public void secondError() {
    User user = new User();
    user.setId(UUID.randomUUID().toString().replaceAll("[-]", ""));
    user.setAge(18);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    userBaseMapper.insert(user);

    Address address = new Address();
    address.setAreaCode("370203");
    address.setProvince("山东省");
    address.setCity("青岛市");
    address.setCounty("市北区");
    address.setDetailAddress("合肥路666号");
    addressBaseMapper.insert(address);
  }

  @Transactional(readOnly = false)
  public void firstError() {
    User user = new User();
    user.setAge(18);
    user.setUsername("hualuomoli");
    user.setNickname("花落莫离");
    userBaseMapper.insert(user);

    Address address = new Address();
    address.setId(UUID.randomUUID().toString().replaceAll("[-]", ""));
    address.setAreaCode("370203");
    address.setProvince("山东省");
    address.setCity("青岛市");
    address.setCounty("市北区");
    address.setDetailAddress("合肥路666号");
    addressBaseMapper.insert(address);
  }

}
