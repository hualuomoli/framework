package com.github.hualuomoli.sample.plugin.ws.server.config;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.hualuomoli.sample.plugin.ws.server.service.ServerService;

@Configuration(value = "com.github.hualuomoli.sample.plugin.ws.server.config.WsServiceConfig")
public class WsServiceConfig {

  @Autowired
  private ServerService serverService;

  @Bean(name = "cxf")
  public Bus bus() {
    return BusFactory.getDefaultBus();
  }

  @Bean
  public JaxWsServerFactoryBean serverService() {
    JaxWsServerFactoryBean bean = new JaxWsServerFactoryBean();

    bean.setServiceClass(ServerService.class);
    bean.setAddress("/serverService");
    bean.setServiceBean(serverService);

    // create
    bean.create();

    return bean;
  }

}
