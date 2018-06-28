package com.github.hualuomoli.sample.framework;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.github.hualuomoli.config.BaseConfig;
import com.github.hualuomoli.config.base.MybatisConfig;
import com.github.hualuomoli.sample.framework.handler.StateTypeHandler;
import com.github.hualuomoli.tool.util.EnvUtils;
import com.github.hualuomoli.tool.util.PropertyUtils;
import com.github.hualuomoli.util.ProjectConfig;

@WebAppConfiguration
@ContextConfiguration(classes = { BaseConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

  protected static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);

  @Autowired
  protected WebApplicationContext wac;

  @BeforeClass
  public static void beforeClass() {
    // 设置运行环境
    System.setProperty("env", EnvUtils.Env.TEST.name());
    EnvUtils.init("env");

    // 设置log4j
    String log4jFilename = "logs/log4j.properties";
    Properties prop = PropertyUtils.loadFirst(EnvUtils.parse(log4jFilename));
    PropertyConfigurator.configure(prop);

    // 配置参数
    ProjectConfig.init("classpath*:configs/jdbc.properties", "classpath*:configs/mybatis.properties");

    // 设置mybatis枚举转换器
    MybatisConfig.addTypeHandler(new StateTypeHandler());
  }

}
