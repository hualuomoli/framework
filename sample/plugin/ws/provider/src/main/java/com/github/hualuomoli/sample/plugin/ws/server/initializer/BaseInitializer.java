package com.github.hualuomoli.sample.plugin.ws.server.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.github.hualuomoli.sample.plugin.ws.server.config.BaseComponentConfig;
import com.github.hualuomoli.sample.plugin.ws.server.config.WsServiceConfig;

/**
 * 初始化servet
 */
@Order(2)
public class BaseInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {

    AnnotationConfigWebApplicationContext rootContext = _getRootContext();

    // 设置Spring监听器
    servletContext.addListener(new ContextLoaderListener(rootContext));

    // 设置转发servlet
    servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext)).addMapping("/");
    // cxf
    servletContext.addServlet("CXFServlet", new CXFServlet()).addMapping("/ws/*");
  }

  // 获取Spring实例
  private AnnotationConfigWebApplicationContext _getRootContext() {
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(BaseComponentConfig.class, WsServiceConfig.class);
    return rootContext;
  }

}
