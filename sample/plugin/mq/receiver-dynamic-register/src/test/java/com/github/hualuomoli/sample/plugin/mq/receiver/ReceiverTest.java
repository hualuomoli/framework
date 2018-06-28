package com.github.hualuomoli.sample.plugin.mq.receiver;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.hualuomoli.config.BaseComponentConfig;
import com.github.hualuomoli.config.ConnectionConfig;
import com.github.hualuomoli.util.ProjectConfig;

@WebAppConfiguration
@ContextConfiguration(classes = { BaseComponentConfig.class, ConnectionConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ReceiverTest {

  @BeforeClass
  public static void beforeClass() {
    ProjectConfig.init("classpath*:configs/activemq.properties");
  }

  @Test
  public void test() throws IOException {
    System.out.println("mq listener started.");
    System.in.read();
  }
}
