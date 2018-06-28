package com.github.hualuomoli.gateway.server.business;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.hualuomoli.gateway.server.lang.NoRouterException;

/**
 * 业务处理器
 */
public interface BusinessHandler {

  /**
   * 处理业务逻辑
   * @param req HTTP请求
   * @param res HTTP响应
   * @param partnerId 合作伙伴ID
   * @param method 请求方法
   * @param bizContent 请求业务内容
   * @return 处理结果
   * @throws NoRouterException 路由未找到
   * @throws Throwable 业务处理失败
   */
  String execute(HttpServletRequest req, HttpServletResponse res, String partnerId, String method, String bizContent) throws NoRouterException, Throwable;

}
