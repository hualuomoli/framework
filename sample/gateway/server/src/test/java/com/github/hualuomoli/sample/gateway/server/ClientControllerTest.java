package com.github.hualuomoli.sample.gateway.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.hualuomoli.gateway.client.GatewayGenericClient;
import com.github.hualuomoli.gateway.client.entity.HttpHeader;
import com.github.hualuomoli.gateway.client.entity.Request;
import com.github.hualuomoli.gateway.client.http.HttpInvoker;
import com.github.hualuomoli.gateway.client.interceptor.Interceptor;
import com.github.hualuomoli.gateway.client.parser.GenericParser;
import com.github.hualuomoli.gateway.client.parser.JSONParser;
import com.github.hualuomoli.gateway.client.parser.Parser;
import com.github.hualuomoli.sample.gateway.server.anno.ApiMethod;
import com.github.hualuomoli.sample.gateway.server.entity.GatewayClientRequest;
import com.github.hualuomoli.sample.gateway.server.entity.GatewayClientResponse;
import com.github.hualuomoli.sample.gateway.server.key.Key;
import com.github.hualuomoli.tool.security.AES;
import com.github.hualuomoli.tool.security.RSA;
import com.github.hualuomoli.tool.util.ClassUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ClientControllerTest {

  protected static final Logger logger = LoggerFactory.getLogger(ClientControllerTest.class);

  private static final String serverURL = "http://localhost/gateway";

  protected GatewayGenericClient<GatewayClientRequest, GatewayClientResponse> client;
  protected HttpInvoker httpInvoker;

  @Before
  public void before() {

    String partnerId = "tester";

    client = new GatewayGenericClient<GatewayClientRequest, GatewayClientResponse>(serverURL, partnerId) {
    };
    client.setJsonParser(new JSONParser() {

      @Override
      public String toJsonString(Object object) {
        if (object == null) {
          return null;
        }
        if (String.class.isAssignableFrom(object.getClass())) {
          return (String) object;
        }
        return JSON.toJSONString(object);
      }

      @Override
      public <T> T parseObject(String content, Class<T> clazz) {
        if (content == null) {
          return null;
        }
        return JSON.parseObject(content, clazz);
      }

      @Override
      public <T> List<T> parseArray(String content, Class<T> clazz) {
        if (content == null) {
          return null;
        }
        return JSON.parseArray(content, clazz);
      }

      @Override
      public Map<String, Object> parse(String content) {
        if (content == null) {
          return null;
        }
        Map<String, Object> map = Maps.newHashMap();
        JSONObject json = JSON.parseObject(content);
        Set<String> keys = json.keySet();
        for (String key : keys) {
          map.put(key, json.get(key));
        }
        return map;
      }
    });
    client.setGenericParser(new GenericParser() {

      @Override
      public String getMethod(Object object) {
        return object.getClass().getAnnotation(ApiMethod.class).value();
      }

      @Override
      public PageName getPageName() {
        return new PageName() {

          @Override
          public String pageSize() {
            return "pageSize";
          }

          @Override
          public String pageNumber() {
            return "pageNumber";
          }

          @Override
          public String datas() {
            return "dataList";
          }

          @Override
          public String count() {
            return "count";
          }
        };
      }
    });
    client.setParser(new Parser<GatewayClientRequest, GatewayClientResponse>() {

      @Override
      public String getRequestContent(GatewayClientRequest request) {
        GatewayClientRequest req = (GatewayClientRequest) request;
        StringBuilder buffer = new StringBuilder();
        Class<? extends Request> clazz = req.getClass();
        List<Field> fields = ClassUtils.getFields(clazz);
        for (Field field : fields) {
          Object obj = ClassUtils.getFieldValue(field, req);
          if (obj == null) {
            continue;
          }
          String value = obj.toString();
          buffer.append("&").append(field.getName()).append("=").append(this.encoded(value));
        }
        return buffer.toString().substring(1);
      }

      private String encoded(String value) {
        try {
          return URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }

      @Override
      public GatewayClientResponse parse(String result, Class<GatewayClientResponse> responseClazz) {
        return JSON.parseObject(result, responseClazz);
      }
    });
    client.setHttpInvoker(httpInvoker = new HttpInvoker() {

      @Override
      public HttpResult invoke(String url, String content, List<HttpHeader> headers) throws IOException {
        HttpURLConnection conn = null;
        OutputStream os = null;
        InputStream is = null;

        try {
          // 信任所有的https
          this.initCertification(url);

          conn = (HttpURLConnection) new URL(url).openConnection();
          conn.setRequestMethod("POST");
          conn.setConnectTimeout(2000); // 连接超时时间
          conn.setReadTimeout(2000); // 从主机读取数据超时时间
          conn.setDoInput(true); // 设置是否读入
          conn.setDoOutput(true); // 设置是否输出
          conn.setUseCaches(false);// 不使用缓存

          // set request header
          for (HttpHeader header : headers) {
            List<String> values = header.getHeaderValues();
            for (String value : values) {
              conn.addRequestProperty(header.getHeaderName(), value);
            }
          }
          conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");

          // flush data
          if (StringUtils.isNotBlank(content)) {
            os = conn.getOutputStream();
            os.write(content.getBytes());
          }

          // get response header
          final List<HttpHeader> responseHeaders = Lists.newArrayList();
          Map<String, List<String>> resHeaderMap = conn.getHeaderFields();
          for (String headerName : resHeaderMap.keySet()) {
            headers.add(new HttpHeader(headerName, resHeaderMap.get(headerName)));
          }

          if (conn.getResponseCode() == 200) {
            is = conn.getInputStream();
            final String result = IOUtils.toString(is, "UTF-8");
            return new HttpResult() {

              @Override
              public String getResult() {
                return result;
              }

              @Override
              public List<HttpHeader> getHeaders() {
                return responseHeaders;
              }
            };
          } else if (conn.getResponseCode() == 500) {
            // 失败
            is = conn.getErrorStream();
            throw new IOException(IOUtils.toString(is, "UTF-8"));
          } else {
            // 其他错误
            throw new IOException(conn.getResponseCode() + "");
          }
        } finally {
          if (is != null) {
            is.close();
          }
          if (os != null) {
            os.close();
          }
          if (conn != null) {
            conn.disconnect();
          }
        }

        // end
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

    });

    // 日志
    client.addInterceptor(new Interceptor<GatewayClientRequest, GatewayClientResponse>() {

      @Override
      public void preHandle(String partnerId, GatewayClientRequest request) {
        GatewayClientRequest req = (GatewayClientRequest) request;
        logger.debug("请求业务内容={}", req.getBizContent());
      }

      @Override
      public void postHandle(String partnerId, GatewayClientRequest request, GatewayClientResponse response) {
        logger.debug("响应业务内容={}", response.getResult());
      }
    });

    // 添加其他信息
    client.addInterceptor(new Interceptor<GatewayClientRequest, GatewayClientResponse>() {

      @Override
      public void preHandle(String partnerId, GatewayClientRequest request) {
        request.setVersion("1.0.0");
      }

      @Override
      public void postHandle(String partnerId, GatewayClientRequest request, GatewayClientResponse response) {
      }
    });

    // 签名
    client.addInterceptor(new Interceptor<GatewayClientRequest, GatewayClientResponse>() {

      @Override
      public void preHandle(String partnerId, GatewayClientRequest request) {
        String origin = this.getSignOrigin(request, Sets.newHashSet("sign"));
        logger.debug("客户端请求签名原文={}", origin);
        request.setSign(RSA.sign(Key.CLIENT_PRIVATE_KEY, origin));
      }

      @Override
      public void postHandle(String partnerId, GatewayClientRequest request, GatewayClientResponse response) {
        String origin = this.getSignOrigin(response, Sets.newHashSet("sign"));
        logger.debug("客户端响应签名原文={}", origin);
        Validate.isTrue(RSA.verify(Key.SERVER_PUBLIC_KEY, origin, response.getSign()));
      }

      private String getSignOrigin(Object obj, Set<String> ignores) {
        Class<? extends Object> clazz = obj.getClass();
        List<Field> fields = ClassUtils.getFields(clazz);
        Collections.sort(fields, new Comparator<Field>() {

          @Override
          public int compare(Field o1, Field o2) {
            return o1.getName().compareTo(o2.getName());
          }
        });

        StringBuilder buffer = new StringBuilder();
        for (Field field : fields) {
          if (ignores.contains(field.getName())) {
            continue;
          }
          Object value = ClassUtils.getFieldValue(field, obj);
          if (value == null) {
            continue;
          }
          buffer.append("&").append(field.getName()).append("=").append(value.toString());
        }

        return buffer.toString().substring(1);
      }

    });
    client.addInterceptor(new Interceptor<GatewayClientRequest, GatewayClientResponse>() {

      @Override
      public void preHandle(String partnerId, GatewayClientRequest request) {
        request.setBizContent(AES.encrypt(Key.SALT, request.getBizContent()));
      }

      @Override
      public void postHandle(String partnerId, GatewayClientRequest request, GatewayClientResponse response) {
        response.setResult(AES.decrypt(Key.SALT, response.getResult()));
      }
    });
    // end
  }

}
