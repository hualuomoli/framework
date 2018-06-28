package com.github.hualuomoli.sample.dubbo.provider;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.dubbo.container.Main;
import com.github.hualuomoli.config.BaseComponentConfig;
import com.github.hualuomoli.config.ProviderConfig;
import com.github.hualuomoli.util.ProjectConfig;

@WebAppConfiguration
@ContextConfiguration(classes = { BaseComponentConfig.class, ProviderConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ProviderTest {

  protected static final Logger logger = LoggerFactory.getLogger(ProviderTest.class);

  @BeforeClass
  public static void beforeClass() {
    // 初始化参数
    ProjectConfig.init("classpath*:configs/zookeeper.properties");
  }

  @Test
  public void test() {
    logger.info("dubbo provider staretd.");

    Main.main(new String[] {});

  }

}
