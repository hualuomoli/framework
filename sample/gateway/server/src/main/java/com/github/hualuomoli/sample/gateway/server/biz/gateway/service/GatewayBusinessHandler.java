package com.github.hualuomoli.sample.gateway.server.biz.gateway.service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.hualuomoli.gateway.server.business.AbstractBusinessHandler;
import com.github.hualuomoli.gateway.server.business.BusinessHandler;
import com.github.hualuomoli.gateway.server.business.interceptor.BusinessInterceptor;
import com.github.hualuomoli.gateway.server.business.parser.JSONParser;
import com.github.hualuomoli.sample.gateway.server.biz.gateway.enums.GatewaySubErrorEnum;
import com.github.hualuomoli.sample.gateway.server.biz.gateway.lang.GatewayBusinessException;
import com.github.hualuomoli.validator.Validator;
import com.github.hualuomoli.validator.lang.InvalidParameterException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service(value = "com.github.hualuomoli.sample.gateway.server.biz.gateway.service.GatewayBusinessHandler")
public class GatewayBusinessHandler extends AbstractBusinessHandler implements BusinessHandler {

  @Autowired
  private GatewayBusinessFunctionDealer gatewayBusinessFunctionDealer;

  @PostConstruct
  public void init() {
    // dealer
    this.setFunctionDealer(gatewayBusinessFunctionDealer);

    // 拦截器
    List<BusinessInterceptor> interceptors = Lists.newArrayList();
    interceptors.add(new ParameterValidatorBusinessInterceptor());
    this.setInterceptors(interceptors);

    // JSON转换器
    this.setJsonParser(new GatewayJSONParser());

    // 扫描的包
    this.setPackageNames(new String[] { "com.github.hualuomoli" });

  }

  // 参数验证器
  private class ParameterValidatorBusinessInterceptor implements BusinessInterceptor {

    @Override
    public void preHandle(HttpServletRequest req, HttpServletResponse res, Method method, Object handler, Object[] params) {
      if (params == null || params.length == 0) {
        return;
      }
      for (Object param : params) {
        if (param == null) {
          continue;
        }
        if (HttpServletRequest.class.isAssignableFrom(param.getClass())) {
          continue;
        }
        if (HttpServletResponse.class.isAssignableFrom(param.getClass())) {
          continue;
        }
        // 集合
        if (Collection.class.isAssignableFrom(param.getClass())) {
          Collection<?> c = (Collection<?>) param;
          for (Object p : c) {
            this.valid(p);
          }
          continue;
        }
        // 基本参数
        this.valid(param);
      }
    }

    private void valid(Object object) {
      try {
        Validator.valid(object);
      } catch (InvalidParameterException ipe) {
        throw new GatewayBusinessException(GatewaySubErrorEnum.INVALID_PARAMETER, ipe.getMessage(), ipe);
      }
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object result) {
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Throwable t) {
    }

    // end
  }

  // JSON转换器
  private class GatewayJSONParser implements JSONParser {

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
      Map<String, Object> map = Maps.newHashMap();
      JSONObject obj = JSON.parseObject(content);
      Set<String> set = obj.keySet();
      for (String key : set) {
        map.put(key, obj.get(key));
      }
      return map;
    }
  }

}
