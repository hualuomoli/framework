package com.github.hualuomoli.tool.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 客户端
 */
public abstract class AbstractHttpCleint implements HttpClient {

  @Override
  public String get(List<Param> params) throws IOException {
    return this.get(params, null, null);
  }

  @Override
  public String get(List<Param> params, List<Header> requestHeaders, List<Header> responseHeaders) throws IOException {
    return this.get(this.getEncoded(params), requestHeaders, responseHeaders);
  }

  @Override
  public String urlencoded(List<Param> params) throws IOException {
    return this.urlencoded(params, null, null);
  }

  @Override
  public String urlencoded(List<Param> params, List<Header> requestHeaders, List<Header> responseHeaders) throws IOException {
    return this.urlencoded(this.getEncoded(params), requestHeaders, responseHeaders);
  }

  @Override
  public String json(String content) throws IOException {
    return this.json(content, null, null);
  }

  public abstract String get(String content, List<Header> requestHeaders, List<Header> responseHeaders) throws IOException;

  public abstract String urlencoded(String content, List<Header> requestHeaders, List<Header> responseHeaders) throws IOException;

  /**
   * 数据编码
   * @return 编码
   */
  protected abstract String charset();

  /**
   * 获取编码后的信息
   * @param params 数据
   * @return 编码后的数据
   */
  protected String getEncoded(List<Param> params) {
    if (params == null || params.size() == 0) {
      return null;
    }
    StringBuilder buffer = new StringBuilder();
    for (Param param : params) {
      buffer.append("&").append(param.name).append("=").append(this.encoded(param.value));
    }
    return buffer.toString().substring(1);
  }

  /**
   * 数据编码
   * @param value 数据
   * @return 编码后的数据
   */
  protected String encoded(String value) {
    try {
      return URLEncoder.encode(value, this.charset());
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

}
