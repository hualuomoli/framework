package com.github.hualuomoli.tool.http;

import java.io.IOException;
import java.util.List;

/**
 * HTTP client
 */
public interface HttpClient {

  /**
   * GET
   * @param params 请求参数
   * @return 响应结果
   * @throws IOException 调用过程中异常
   */
  String get(List<Param> params) throws IOException;

  /**
   * GET
   * @param params 请求参数
   * @param requestHeaders 请求的header
   * @param responseHeaders 响应的header
   * @return 响应结果
   * @throws IOException 调用过程中异常
   */
  String get(List<Param> params, List<Header> requestHeaders, List<Header> responseHeaders) throws IOException;

  /**
   * application/x-www-form-urlencoded
   * @param params 请求参数
   * @return 响应结果
   * @throws IOException 调用过程中异常
   */
  String urlencoded(List<Param> params) throws IOException;

  /**
   * application/x-www-form-urlencoded
   * @param params 请求参数
   * @param requestHeaders 请求的header
   * @param responseHeaders 响应的header
   * @return 响应结果
   * @throws IOException 调用过程中异常
   */
  String urlencoded(List<Param> params, List<Header> requestHeaders, List<Header> responseHeaders) throws IOException;

  /**
   * application/json
   * @param content 请求内容
   * @return 响应结果
   * @throws IOException 调用过程中异常
   */
  String json(String content) throws IOException;

  /**
   * application/json
   * @param content 请求信息
   * @param requestHeaders 请求的header
   * @param responseHeaders 响应的header
   * @return 响应结果
   * @throws IOException 调用过程中异常
   */
  String json(String content, List<Header> requestHeaders, List<Header> responseHeaders) throws IOException;

}
