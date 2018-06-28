package com.github.hualuomoli.gateway.server.business;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.hualuomoli.gateway.server.business.dealer.FunctionDealer;
import com.github.hualuomoli.gateway.server.business.entity.Function;
import com.github.hualuomoli.gateway.server.business.interceptor.BusinessInterceptor;
import com.github.hualuomoli.gateway.server.business.parser.JSONParser;
import com.github.hualuomoli.gateway.server.lang.NoRouterException;

/**
 * 业务处理者
 */
public class AbstractBusinessHandler implements BusinessHandler {

  private static Boolean INIT_SUCCESS = false; // 初始化成功

  // 业务拦截器
  private List<BusinessInterceptor> interceptors = new ArrayList<BusinessInterceptor>();
  // Function处理类
  private FunctionDealer functionDealer;
  // JSON转换器
  private JSONParser jsonParser;
  // 实体类包路径
  private String[] packageNames;

  public void setInterceptors(List<BusinessInterceptor> interceptors) {
    this.interceptors = interceptors;
  }

  public void setFunctionDealer(FunctionDealer functionDealer) {
    this.functionDealer = functionDealer;
  }

  public void setJsonParser(JSONParser jsonParser) {
    this.jsonParser = jsonParser;
  }

  public void setPackageNames(String[] packageNames) {
    this.packageNames = packageNames;
  }

  // 验证并初始化
  private void init() {
    if (INIT_SUCCESS) {
      return;
    }

    this.doInit();
  }

  // 执行初始化操作
  private synchronized void doInit() {
    if (INIT_SUCCESS) {
      return;
    }

    System.out.println("初始化网关functions");
    functionDealer.init();
    INIT_SUCCESS = true;
  }

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse res, String partnerId, String method, String bizContent) throws NoRouterException, Throwable {
    this.init();

    Function function = functionDealer.getFunction(method, req);
    // 处理类
    Object handler = functionDealer.getDealer(function);

    List<Object> paramList = new ArrayList<Object>();
    Method m = function.getMethod();
    Class<?>[] parameterTypes = m.getParameterTypes();
    c: for (Class<?> parameterType : parameterTypes) {

      // HTTP request
      if (HttpServletRequest.class.isAssignableFrom(parameterType)) {
        paramList.add(req);
        continue;
      }

      // HTTP response
      if (HttpServletResponse.class.isAssignableFrom(parameterType)) {
        paramList.add(res);
        continue;
      }

      // List
      if (Collection.class.isAssignableFrom(parameterType)) {
        ParameterizedType genericParameterTypes = (ParameterizedType) m.getGenericParameterTypes()[0];
        Class<?> clazz = (Class<?>) genericParameterTypes.getActualTypeArguments()[0];
        paramList.add(jsonParser.parseArray(bizContent, clazz));
        continue;
      }

      // packageName
      String name = parameterType.getName();
      for (String packageName : packageNames) {
        if (name.startsWith(packageName)) {
          paramList.add(jsonParser.parseObject(bizContent, parameterType));
          continue c;
        }
      }

      // end
      throw new RuntimeException("there is not support type " + name);
    }

    // 请求参数
    Object[] params = paramList.toArray(new Object[] {});

    // 执行业务
    return this.deal(m, handler, params, req, res);
  }

  /**
   * 业务处理
   * @param method 执行方法
   * @param handler 执行器
   * @param params 执行参数 
   * @param req HTTP请求
   * @param res HTTP响应
   * @return 业务处理结果
   */
  private String deal(Method method, Object handler, Object[] params, HttpServletRequest req, HttpServletResponse res) throws Throwable {

    // 前置拦截
    for (BusinessInterceptor interceptor : interceptors) {
      interceptor.preHandle(req, res, method, handler, params);
    }

    // 业务处理
    String result = null;
    Throwable t = null;
    try {
      Object object = method.invoke(handler, params);
      if (object != null) {
        result = jsonParser.toJsonString(object);
      }

      // 后置拦截
      for (BusinessInterceptor interceptor : interceptors) {
        interceptor.postHandle(req, res, result);
      }
      return result;
    } catch (IllegalAccessException e) {
      t = e;
    } catch (IllegalArgumentException e) {
      t = e;
    } catch (InvocationTargetException e) {
      t = e.getTargetException();
    }

    // 错误拦截
    for (BusinessInterceptor interceptor : interceptors) {
      interceptor.afterCompletion(req, res, t);
    }
    throw t;
  }

}
