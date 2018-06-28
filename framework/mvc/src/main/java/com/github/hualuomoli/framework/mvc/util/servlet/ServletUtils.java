package com.github.hualuomoli.framework.mvc.util.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * servlet工具
 * {@link org.springframework.web.context.request.RequestContextListener}
 */
public class ServletUtils {

  /**
   * 获取HTTP请求
   * @return HTTP请求
   */
  public static HttpServletRequest getRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }

  /**
   * 获取HTTP响应
   * @return HTTP响应
   */
  public static HttpServletResponse getResponse() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
  }

  /**
   * 获取servlet上下文
   * @return serlvet上下文
   */
  public static ServletContext getServletContext() {
    return ServletUtils.getRequest().getServletContext();
  }

}
