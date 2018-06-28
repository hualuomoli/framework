package com.github.hualuomoli.gateway.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.hualuomoli.gateway.server.entity.Request;
import com.github.hualuomoli.gateway.server.entity.Response;
import com.github.hualuomoli.gateway.server.lang.NoPartnerException;
import com.github.hualuomoli.gateway.server.lang.SecurityException;

/**
 * 拦截器,用于权限验证/数据加解密
 */
public interface Interceptor<Req extends Request, Res extends Response> {

  /**
   * 执行业务前置处理
   * @param req HTTP请求
   * @param res HTTP响应
   * @param request 网关请求
   * @param response 网关响应
   * @throws NoPartnerException 合作伙伴未注册
   * @throws SecurityException 安全错误
   */
  void preHandle(HttpServletRequest req, HttpServletResponse res, Req request, Res response) throws NoPartnerException, SecurityException;

  /**
   * 执行业务后置处理
   * @param req HTTP请求
   * @param res HTTP响应
   * @param request 网关请求
   * @param response 网关响应
   */
  void postHandle(HttpServletRequest req, HttpServletResponse res, Req request, Res response);

  /**
   * 执行业务后置处理
   * @param req HTTP请求
   * @param res HTTP响应
   * @param request 网关请求
   * @param response 网关响应
   * @param t 错误信息
   */
  void afterCompletion(HttpServletRequest req, HttpServletResponse res, Req request, Res response, Throwable t);

}
