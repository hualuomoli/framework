package com.github.hualuomoli.gateway.client.http;

import java.io.IOException;
import java.util.List;

import com.github.hualuomoli.gateway.client.entity.HttpHeader;

/**
 * 客户端
 */
public interface HttpInvoker {

  /**
   * 调用
   * @param url URL地址
   * @param content 请求业务内容
   * @param headers 请求header头
   * @return 响应
   * @throws IOException 异常
   */
  HttpResult invoke(String url, String content, List<HttpHeader> headers) throws IOException;

  // HTTP响应
  interface HttpResult {

    /**
     * 获取返回数据
     * @return 返回数据
     */
    String getResult();

    /**
     * 获取返回header
     * @return 返回header
     */
    List<HttpHeader> getHeaders();

  }

}
