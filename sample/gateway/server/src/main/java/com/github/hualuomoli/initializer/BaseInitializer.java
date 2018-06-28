package com.github.hualuomoli.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.github.hualuomoli.config.BaseConfig;
import com.github.hualuomoli.config.MvcConfig;

/**
 * 初始化servet
 */
public class BaseInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {

    AnnotationConfigWebApplicationContext rootContext = _getRootContext();

    // spring context
    servletContext.addListener(new ContextLoaderListener(rootContext));

    // 设置转发servlet
    servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext)).addMapping("/");

  }

  // 获取Spring实例
  private AnnotationConfigWebApplicationContext _getRootContext() {
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(BaseConfig.class, MvcConfig.class);
    return rootContext;
  }

}
