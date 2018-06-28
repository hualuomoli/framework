package com.github.hualuomoli.framework.mvc.util.servlet;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Spring context工具
 * {@link org.springframework.web.context.ContextLoaderListener}
 */
public class ContextUtils {

  /**
   * 获取Context
   * @return context
   */
  public static WebApplicationContext getContext() {
    return ContextLoader.getCurrentWebApplicationContext();
  }

  // 这个是3.0规范的
  //  public static WebApplicationContext getContext() {
  //    HttpServletRequest request = ServletUtils.getRequest();
  //    ServletContext servletContext = request.getServletContext();
  //    return RequestContextUtils.getWebApplicationContext(request, servletContext);
  //  }

}
