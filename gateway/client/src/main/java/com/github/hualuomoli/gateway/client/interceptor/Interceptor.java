package com.github.hualuomoli.gateway.client.interceptor;

import com.github.hualuomoli.gateway.client.entity.Request;
import com.github.hualuomoli.gateway.client.entity.Response;

/**
 * 拦截器
 */
public interface Interceptor<Req extends Request, Res extends Response> {

  /**
   * 执行业务前置处理
   * @param partnerId 合作伙伴ID
   * @param request 网关请求
   */
  void preHandle(String partnerId, Req request);

  /**
   * 执行业务后置处理
   * @param partnerId 合作伙伴ID
   * @param request 网关请求
   * @param response 网关响应
   */
  void postHandle(String partnerId, Req request, Res response);

}
