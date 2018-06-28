package com.github.hualuomoli.tool.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认客户端
 */
public class HttpDefaultClient extends AbstractHttpCleint implements HttpClient {

  private static final Logger logger = LoggerFactory.getLogger(HttpDefaultClient.class);

  // 链接时长
  private Integer connectTimeout = 1000 * 2;
  // 从主机读取数据超时
  private Integer readTimeout = 1000 * 30;

  private String url;

  public HttpDefaultClient(String url) {
    this.url = url;
  }

  public void setConnectTimeout(Integer connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public void setReadTimeout(Integer readTimeout) {
    this.readTimeout = readTimeout;
  }

  @Override
  public String get(String content, List<Header> requestHeaders, List<Header> responseHeaders) throws IOException {

    HttpURLConnection conn = null;

    try {
      // 获取URL地址
      String url = this.url;
      if (StringUtils.isNotBlank(content)) {
        if (url.lastIndexOf("?") > 0) {
          url = url + "&" + content;
        } else {
          url = url + "?" + content;
        }
      }

      logger.debug("[get] url={}", url);
      logger.debug("[get] requestHeaders={}", requestHeaders);

      conn = this.getConnection(url);
      conn.setRequestMethod("GET");
      conn.setConnectTimeout(connectTimeout); // 连接超时时间
      conn.setReadTimeout(readTimeout); // 从主机读取数据超时时间
      conn.setDoInput(true); // 设置是否读入
      conn.setDoOutput(true); // 设置是否输出
      conn.setUseCaches(false);// 不使用缓存

      // set request header
      this.writeHttpRequestHeader(conn, requestHeaders);

      // get response header
      this.readHttpResponseHeader(conn, responseHeaders);

      // read data
      return this.readHttpResponseData(conn);
    } finally {
      this.close(conn);
    }
    // end
  }

  @Override
  public String urlencoded(String content, List<Header> requestHeaders, List<Header> responseHeaders) throws IOException {
    logger.debug("[urlencoded] url={},content={}", url, content);
    logger.debug("[urlencoded] requestHeaders={}", requestHeaders);

    HttpURLConnection conn = null;
    OutputStream os = null;

    try {
      conn = this.getConnection(url);
      conn.setRequestMethod("POST");
      conn.setConnectTimeout(connectTimeout); // 连接超时时间
      conn.setReadTimeout(readTimeout); // 从主机读取数据超时时间
      conn.setDoInput(true); // 设置是否读入
      conn.setDoOutput(true); // 设置是否输出
      conn.setUseCaches(false);// 不使用缓存

      // set request header
      this.writeHttpRequestHeader(conn, requestHeaders);
      this.writeHttpRequestContentType(conn, "application/x-www-form-urlencoded");

      // flush data
      if (StringUtils.isNotBlank(content)) {
        os = conn.getOutputStream();
        os.write(content.getBytes());
      }

      // get response header
      this.readHttpResponseHeader(conn, responseHeaders);

      // read data
      return this.readHttpResponseData(conn);
    } finally {
      this.close(os);
      this.close(conn);
    }
    // end
  }

  @Override
  public String json(String content, List<Header> requestHeaders, List<Header> responseHeaders) throws IOException {
    logger.debug("[json] url={},content={}", url, content);
    logger.debug("[json] requestHeaders={}", requestHeaders);

    HttpURLConnection conn = null;
    OutputStream os = null;

    try {
      conn = this.getConnection(url);
      conn.setRequestMethod("POST");
      conn.setConnectTimeout(connectTimeout); // 连接超时时间
      conn.setReadTimeout(readTimeout); // 从主机读取数据超时时间
      conn.setDoInput(true); // 设置是否读入
      conn.setDoOutput(true); // 设置是否输出
      conn.setUseCaches(false);// 不使用缓存

      // set request header
      this.writeHttpRequestHeader(conn, requestHeaders);
      this.writeHttpRequestContentType(conn, "application/json");

      // flush data
      if (content != null && content.length() > 0) {
        os = conn.getOutputStream();
        os.write(content.getBytes());
      }

      // get response header
      this.readHttpResponseHeader(conn, responseHeaders);

      // read data
      return this.readHttpResponseData(conn);
    } finally {
      this.close(os);
      this.close(conn);
    }
    // end
  }

  /**
   * 写入请求header
   * @param conn 连接
   * @param headers 请求header
   */
  private void writeHttpRequestHeader(HttpURLConnection conn, List<Header> headers) {
    if (headers == null || headers.size() == 0) {
      return;
    }
    for (Header header : headers) {
      String[] values = header.value;
      for (String value : values) {
        conn.addRequestProperty(header.name, value);
      }
    }
  }

  /**
   * 写入请求Content-Type
   * @param conn 连接
   * @param name 请求header名
   * @param value 请求header值
   */
  private void writeHttpRequestContentType(HttpURLConnection conn, String... values) {
    for (String value : values) {
      conn.addRequestProperty("Content-Type", value);
    }
  }

  /**
   * 读取响应Header
   * @param conn 连接
   */
  private void readHttpResponseHeader(HttpURLConnection conn, List<Header> headers) {
    if (headers == null) {
      return;
    }
    Map<String, List<String>> resHeaderMap = conn.getHeaderFields();
    for (String name : resHeaderMap.keySet()) {
      headers.add(new Header(name, resHeaderMap.get(name).toArray(new String[] {})));
    }
  }

  /**
   * 读取响应数据
   * @param conn 连接
   * @return 响应数据
   * @throws IOException 读取异常
   */
  private String readHttpResponseData(HttpURLConnection conn) throws IOException {
    InputStream is = null;
    try {
      // get response
      if (conn.getResponseCode() == 200) {
        // 成功
        is = conn.getInputStream();
        return IOUtils.toString(is, this.charset());
      } else if (conn.getResponseCode() == 404) {
        // 未找到
        throw new IOException("404");
      } else if (conn.getResponseCode() == 500) {
        // 失败
        is = conn.getErrorStream();
        throw new IOException(IOUtils.toString(is, this.charset()));
      } else {
        // 其他错误
        throw new IOException(conn.getResponseCode() + "");
      }
    } finally {
      this.close(is);
    }
  }

  @Override
  protected String charset() {
    return "UTF-8";
  }

  /**
   * 获取HTTP链接,可考虑使用连接池
   * @param urlString 链接URL
   * @return HTTP链接
   * @throws IOException 链接异常
   */
  private HttpURLConnection getConnection(String urlString) throws IOException {
    // 信任所有的https
    this.initCertification(urlString);

    URL url = new URL(urlString);
    return (HttpURLConnection) url.openConnection();
  }

  /**
   * 关闭HTTP链接
   * @param conn HTTP链接
   */
  private void close(HttpURLConnection conn) {
    if (conn == null) {
      return;
    }
    conn.disconnect();
  }

  /**
  * 关闭输入流
  * @param is 输入流
  */
  private void close(InputStream is) {
    if (is == null) {
      return;
    }
    try {
      is.close();
    } catch (IOException e) {
    }
  }

  /**
  * 关闭输出流
  * @param os 输出流
  */
  private void close(OutputStream os) {
    if (os == null) {
      return;
    }
    try {
      os.close();
    } catch (IOException e) {
    }
  }

  /**
   * 初始化证书
   * @param url 请求的URL
   */
  private void initCertification(String url) {
    if (!url.startsWith("https://")) {
      return;
    }
    try {
      HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
      SSLContext context = SSLContext.getInstance("TLS");
      context.init(null, new X509TrustManager[] { new X509TrustManager() {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
          return new X509Certificate[0];
        }

      } }, new SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
