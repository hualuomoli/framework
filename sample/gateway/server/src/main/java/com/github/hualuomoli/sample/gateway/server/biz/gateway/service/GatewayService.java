package com.github.hualuomoli.sample.gateway.server.biz.gateway.service;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.hualuomoli.gateway.client.lang.BusinessException;
import com.github.hualuomoli.gateway.server.GatewayServer;
import com.github.hualuomoli.gateway.server.interceptor.Interceptor;
import com.github.hualuomoli.gateway.server.lang.NoPartnerException;
import com.github.hualuomoli.gateway.server.lang.NoRouterException;
import com.github.hualuomoli.gateway.server.lang.SecurityException;
import com.github.hualuomoli.sample.gateway.server.biz.gateway.entity.GatewayServerRequest;
import com.github.hualuomoli.sample.gateway.server.biz.gateway.entity.GatewayServerResponse;
import com.github.hualuomoli.sample.gateway.server.biz.gateway.enums.ResponseCodeEnum;
import com.github.hualuomoli.sample.gateway.server.key.Key;
import com.github.hualuomoli.tool.security.AES;
import com.github.hualuomoli.tool.security.RSA;
import com.github.hualuomoli.tool.util.ClassUtils;
import com.github.hualuomoli.validator.lang.InvalidParameterException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Service(value = "com.github.hualuomoli.sample.gateway.server.biz.gateway.service.GatewayService")
public class GatewayService extends GatewayServer<GatewayServerRequest, GatewayServerResponse> {

  private static final Logger logger = LoggerFactory.getLogger(GatewayService.class);

  @Autowired
  private GatewayBusinessHandler gatewayBusinessHandler;

  @PostConstruct
  public void init() {
    // 业务处理器
    this.setBusinessHandler(gatewayBusinessHandler);
    // 拦截器
    List<Interceptor<GatewayServerRequest, GatewayServerResponse>> interceptors = Lists.newArrayList();
    interceptors.add(new EncryptInterceptor());
    interceptors.add(new SignInterceptor());
    interceptors.add(new ApplicationInterceptor());
    interceptors.add(new LogInterceptor());
    super.setInterceptors(interceptors);
  }

  @Override
  protected GatewayServerRequest parseRequest(HttpServletRequest req) {
    Enumeration<String> names = req.getParameterNames();
    Map<String, String> map = Maps.newHashMap();
    while (names.hasMoreElements()) {
      String name = names.nextElement();
      map.put(name, req.getParameter(name));
    }
    return JSON.parseObject(JSON.toJSONString(map), GatewayServerRequest.class);
  }

  // 加密、解密拦截器
  private class EncryptInterceptor implements Interceptor<GatewayServerRequest, GatewayServerResponse> {

    @Override
    public void preHandle(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response)
        throws NoPartnerException, SecurityException {
      request.setBizContent(AES.decrypt(Key.SALT, request.getBizContent()));
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response) {
      response.setResult(AES.encrypt(Key.SALT, response.getResult()));
    }
    // end

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response, Throwable t) {
    }

  }

  // 签名、验签拦截器
  private class SignInterceptor implements Interceptor<GatewayServerRequest, GatewayServerResponse> {

    @Override
    public void preHandle(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response)
        throws NoPartnerException, SecurityException {
      String origin = this.getSignOrigin(request, Sets.newHashSet("sign"));
      logger.debug("服务端端请求签名原文={}", origin);

      if (!RSA.verify(Key.CLIENT_PUBLIC_KEY, origin, request.getSign())) {
        throw new SecurityException("签名不合法");
      }
      // end
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response) {
      String origin = this.getSignOrigin(response, Sets.newHashSet("sign"));
      String sign = RSA.sign(Key.SERVER_PRIVATE_KEY, origin);
      logger.debug("服务端端响应签名原文={}", origin);

      response.setSign(sign);
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response, Throwable t) {
      if (t instanceof NoPartnerException) {
        return;
      }
      this.postHandle(req, res, request, response);
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
    // end

  }

  // 应用拦截器
  private class ApplicationInterceptor implements Interceptor<GatewayServerRequest, GatewayServerResponse> {

    @Override
    public void preHandle(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response)
        throws NoPartnerException, SecurityException {
      logger.debug("请求业务内容={}", request.getBizContent());
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response) {
      response.setCode(ResponseCodeEnum.SUCCESS);
      response.setMessage("执行成功");
      response.setSubCode(ResponseCodeEnum.SUCCESS.name());
      response.setSubMessage("业务处理成功");

      logger.debug("响应业务结果={}", response.getResult());
    }
    // end

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response, Throwable t) {
      Class<?> clazz = t.getClass();

      if (NoPartnerException.class.isAssignableFrom(clazz)) {
        response.setCode(ResponseCodeEnum.NO_PARTNER);
        response.setMessage("合作伙伴未注册");
      } else if (SecurityException.class.isAssignableFrom(clazz)) {
        response.setCode(ResponseCodeEnum.INVALID_SECURITY);
        response.setMessage("安全认证失败");
      } else if (NoRouterException.class.isAssignableFrom(clazz)) {
        response.setCode(ResponseCodeEnum.NO_ROUTER);
        response.setMessage("请求方法未注册");
      } else if (BusinessException.class.isAssignableFrom(clazz)) {
        response.setCode(ResponseCodeEnum.BUSINESS);
        response.setMessage("业务处理失败");
      } else if (IllegalAccessException.class.isAssignableFrom(clazz)) {
        response.setCode(ResponseCodeEnum.SYSTEM);
        response.setMessage("方法执行-没有访问权限");
      } else if (IllegalArgumentException.class.isAssignableFrom(clazz)) {
        response.setCode(ResponseCodeEnum.SYSTEM);
        response.setMessage("方法执行-参数不合法");
      } else if (InvalidParameterException.class.isAssignableFrom(clazz)) {
        response.setCode(ResponseCodeEnum.SYSTEM);
        response.setMessage("参数不合法");
      } else {
        response.setCode(ResponseCodeEnum.SYSTEM);
        response.setMessage("系统错误");
      }
      // end
    }
    // end
  }

  // 日志拦截器
  private class LogInterceptor implements Interceptor<GatewayServerRequest, GatewayServerResponse> {

    @Override
    public void preHandle(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response)
        throws NoPartnerException, SecurityException {
      if (!logger.isDebugEnabled()) {
        return;
      }
      logger.debug("请求业务内容={}", request.getBizContent());
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response) {
      if (!logger.isDebugEnabled()) {
        return;
      }
      logger.debug("响应业务内容={}", response.getResult());
    }
    // end

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, GatewayServerRequest request, GatewayServerResponse response, Throwable t) {
      if (!logger.isDebugEnabled()) {
        return;
      }
      logger.debug("处理失败,失败原因={}", t.getMessage(), t);
    }

  }

}
