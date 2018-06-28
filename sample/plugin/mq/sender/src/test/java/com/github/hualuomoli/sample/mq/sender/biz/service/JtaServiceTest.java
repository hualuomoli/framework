package com.github.hualuomoli.sample.mq.sender.biz.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.sample.mq.sender.ServiceTest;

public class JtaServiceTest extends ServiceTest {

  @Autowired
  private JtaService jtaService;

  @Test
  public void testExecute() {
    jtaService.execute();
  }

  @Test
  public void testExecuteUseService() {
    jtaService.executeUseService();
  }

  @Test(expected = Exception.class)
  public void testSecondErrorForMq() {
    jtaService.secondErrorForMq();
  }

  @Test(expected = Exception.class)
  public void testSecondErrorForDB() {
    jtaService.secondErrorForDB();
  }

  @Test(expected = Exception.class)
  public void testFirstError() {
    jtaService.firstError();
  }

}
