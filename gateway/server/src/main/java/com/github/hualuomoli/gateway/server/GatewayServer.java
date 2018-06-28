package com.github.hualuomoli.gateway.server;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.hualuomoli.gateway.server.business.BusinessHandler;
import com.github.hualuomoli.gateway.server.entity.Request;
import com.github.hualuomoli.gateway.server.entity.Response;
import com.github.hualuomoli.gateway.server.interceptor.Interceptor;

/**
 * 网关服务器
 */
public abstract class GatewayServer<Req extends Request, Res extends Response> {

  private Class<Res> resClazz;

  private BusinessHandler businessHandler;

  private List<Interceptor<Req, Res>> interceptors = new ArrayList<Interceptor<Req, Res>>();

  public void setBusinessHandler(BusinessHandler businessHandler) {
    this.businessHandler = businessHandler;
  }

  public void setInterceptors(List<Interceptor<Req, Res>> interceptors) {
    this.interceptors = interceptors;
  }

  @SuppressWarnings("unchecked")
  public GatewayServer() {
    resClazz = (Class<Res>) this.getGenericClass(1);
  }

  /**
   * 执行
   * @param req HTTP请求
   * @param res HTTP响应
   * @return 执行结果
   */
  public Res execute(HttpServletRequest req, HttpServletResponse res) {
    // 转换请求
    Req request = this.parseRequest(req);
    // 实例化响应
    Res response = this.newInstance(resClazz);
    try {

      // 前置拦截
      for (int i = 0, size = interceptors.size(); i < size; i++) {
        interceptors.get(i).preHandle(req, res, request, response);
      }

      // 执行业务 
      String result = businessHandler.execute(req, res, request.getPartnerId(), request.getMethod(), request.getBizContent());
      response.setResult(result);

      // 后置拦截
      for (int size = interceptors.size(), i = size - 1; i >= 0; i--) {
        interceptors.get(i).postHandle(req, res, request, response);
      }

    } catch (Throwable t) {
      // 错误拦截
      for (int size = interceptors.size(), i = size - 1; i >= 0; i--) {
        interceptors.get(i).afterCompletion(req, res, request, response, t);
      }
    }

    return response;
  }

  /**
   * 实例化响应
   * @param clazz 类类型
   * @return 实例
   */
  private <R extends Response> R newInstance(Class<R> clazz) {
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    // end
  }

  /**
   * 解析HTTP请求
   * @param req HTTP请求
   * @return 请求信息
   */
  protected abstract Req parseRequest(HttpServletRequest req);

  /**
   * 获取泛型类型
   * @param index 泛型位置,从0开始
   * @return 泛型类型
   */
  protected Class<?> getGenericClass(int index) {
    Class<?> clazz = this.getClass();
    ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    Class<?> resultClazz = (Class<?>) actualTypeArguments[index];
    return resultClazz;
  }

}
