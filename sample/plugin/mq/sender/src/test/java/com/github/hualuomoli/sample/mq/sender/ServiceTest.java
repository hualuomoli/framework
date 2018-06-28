package com.github.hualuomoli.sample.mq.sender;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.hualuomoli.config.BaseConfig;
import com.github.hualuomoli.util.ProjectConfig;

@WebAppConfiguration
@ContextConfiguration(classes = { BaseConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

  protected static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);

  @BeforeClass
  public static void beforeClass() {
    ProjectConfig.init("classpath*:configs/jdbc.properties", "classpath*:configs/mybatis.properties", "classpath*:configs/activemq.properties");
  }

}
