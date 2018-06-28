package com.github.hualuomoli.sample.plugin.ws.consumer.util;

import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AuthInterceptorUtils {

  /**
   * 获取权限拦截器
   * @return 权限拦截器
   */
  public static Interceptor<SoapMessage> getAuthInterceptor(final Map<String, String> map, final String qname) {
    return new AbstractPhaseInterceptor<SoapMessage>(Phase.WRITE) {

      @Override
      public void handleMessage(SoapMessage soapmessage) throws Fault {
        final Document document = DOMUtils.createDocument();

        // root
        final Element root = document.createElementNS(qname, "auth");

        // add element
        for (String key : map.keySet()) {
          final Element e = document.createElement(key);
          e.setTextContent(map.get(key));
          root.appendChild(e);
        }

        soapmessage.getHeaders().add(new SoapHeader(new QName(""), root));
      }

    };
  }

}
