package com.github.hualuomoli.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.github.hualuomoli.config.BaseConfig;
import com.github.hualuomoli.config.MvcConfig;

/**
 * 初始化servet
 */
@Order(2)
public class BaseInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {

    AnnotationConfigWebApplicationContext rootContext = _getRootContext();

    // spring context
    servletContext.addListener(new ContextLoaderListener(rootContext));
    // servlet request
    servletContext.addListener(new RequestContextListener());

    // 设置转发servlet
    // rootContext可以不使用上面定义的,可以重新获取一个
    // rootContext = _getRootContext();
    servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext)).addMapping("/");

  }

  // 获取Spring实例
  private AnnotationConfigWebApplicationContext _getRootContext() {
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(BaseConfig.class, MvcConfig.class);
    return rootContext;
  }

}
