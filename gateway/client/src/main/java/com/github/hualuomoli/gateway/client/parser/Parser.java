package com.github.hualuomoli.gateway.client.parser;

import com.github.hualuomoli.gateway.client.entity.Request;
import com.github.hualuomoli.gateway.client.entity.Response;

/**
 * 请求响应解析器
 */
public interface Parser<Req extends Request, Res extends Response> {

  /**
   * 获取请求内容
   * @param request 请求
   * @return 请求内容字符串
   */
  String getRequestContent(Req request);

  /**
   * 解析响应结果
   * @param result 响应结果字符串
   * @param responseClazz 响应结果类类型
   * @return 响应结果
   */
  Res parse(String result, Class<Res> responseClazz);

}
