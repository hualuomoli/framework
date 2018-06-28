package com.github.hualuomoli.ws.consumer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;

import com.github.hualuomoli.ws.consumer.lang.SoapException;

public class SoapUtils {

  /**
   * 获取服务
   * @param address wsdl地址
   * @param clazz 服务类型
   * @return 服务
   * @throws SoapException 获取服务错误
   */
  public static <T> T getService(String address, Class<T> clazz) throws SoapException {
    List<Interceptor<SoapMessage>> list = new ArrayList<Interceptor<SoapMessage>>();
    return getService(address, clazz, list);
  }

  /**
   * 获取服务
   * @param address wsdl地址
   * @param clazz 服务类型
   * @param interceptor 拦截器
   * @return 服务
   * @throws SoapException 获取服务错误
   */
  public static <T> T getService(String address, Class<T> clazz, Interceptor<SoapMessage> interceptor) throws SoapException {
    List<Interceptor<SoapMessage>> list = new ArrayList<Interceptor<SoapMessage>>();
    list.add(interceptor);
    return getService(address, clazz, list);
  }

  /**
   * 获取服务
   * @param address wsdl地址
   * @param clazz 服务类型
   * @param interceptors 拦截器
   * @return 服务
   * @throws SoapException 获取服务错误
   */
  @SuppressWarnings("unchecked")
  public static <T> T getService(String address, Class<T> clazz, Interceptor<SoapMessage>... interceptors) throws SoapException {
    List<Interceptor<SoapMessage>> list = new ArrayList<Interceptor<SoapMessage>>();
    for (Interceptor<SoapMessage> interceptor : interceptors) {
      list.add(interceptor);
    }
    return getService(address, clazz, list);
  }

  /**
   * 获取服务
   * @param address wsdl地址
   * @param clazz 服务类型
   * @param interceptors 拦截器
   * @return 服务
   * @throws SoapException 获取服务错误
   */
  public static <T> T getService(String address, Class<T> clazz, List<Interceptor<SoapMessage>> interceptors) throws SoapException {

    try {
      JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
      factory.setServiceClass(clazz);
      factory.setAddress(address);

      // 添加输出 Interceptor
      if (interceptors != null && interceptors.size() > 0) {
        List<Interceptor<? extends Message>> outInterceptors = new ArrayList<Interceptor<? extends Message>>();
        for (Interceptor<SoapMessage> interceptor : interceptors) {
          outInterceptors.add(interceptor);
        }
        factory.setOutInterceptors(outInterceptors);
      }

      return factory.create(clazz);
    } catch (Exception e) {
      throw new SoapException(e);
    }

  }

}
