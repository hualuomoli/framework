package com.github.hualuomoli.config.mvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.hualuomoli.framework.mvc.version.method.RequestMappingVersionHandlerMapping;

@Configuration(value = "com.github.hualuomoli.config.mvc.WebMvcSupportConfig")
public class WebMvcSupportConfig extends WebMvcConfigurationSupport {

  private static final Logger logger = LoggerFactory.getLogger(WebMvcSupportConfig.class);

  // 多版本支持
  @Bean
  @Override
  public RequestMappingHandlerMapping requestMappingHandlerMapping() {
    return super.requestMappingHandlerMapping();
  }

  @Override
  protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
    return new RequestMappingVersionHandlerMapping();
  }

  // 静态资源
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    System.out.println("初始化静态文件处理器");
    registry.addResourceHandler("/static/**").addResourceLocations("/static/");
  }

  // 拦截器
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    System.out.println("初始化mvc 拦截器");
    registry.addInterceptor(new LogInterceptor());
  }

  //配置消息转换器
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    System.out.println("初始化mvc 消息转换器");

    // 字符串转换器
    converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

    // json转换器
    MappingJackson2HttpMessageConverter jsonConvert = new MappingJackson2HttpMessageConverter();
    jsonConvert.setObjectMapper(new JackJsonMapper());
    jsonConvert.setPrettyPrint(true);
    converters.add(jsonConvert);
  }

  //日志拦截器
  private static class LogInterceptor extends HandlerInterceptorAdapter {

    private static final String TAB = "  ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      if (logger.isDebugEnabled()) {
        this.showRequestInformation(request);
      }
      return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /** 输出请求信息 */
    private void showRequestInformation(HttpServletRequest req) {
      // show request
      logger.debug("request message");
      logger.debug(TAB + "characterEncoding {}", req.getCharacterEncoding());
      logger.debug(TAB + "contextPath {}", req.getContextPath());
      logger.debug(TAB + "servletPath {}", req.getServletPath());
      logger.debug(TAB + "requestedSessionId {}", req.getRequestedSessionId());
      logger.debug(TAB + "url {}", req.getRequestURL());
      logger.debug(TAB + "uri {}", req.getRequestURI());
      logger.debug(TAB + "method {}", req.getMethod());
      logger.debug(TAB + "headers");
      Enumeration<String> headerNames = req.getHeaderNames();
      while (headerNames.hasMoreElements()) {
        String name = headerNames.nextElement();
        logger.debug(TAB + TAB + "{} = {}", name, req.getHeader(name));
      }
      logger.debug(TAB + "parameters");
      Enumeration<String> parameterNames = req.getParameterNames();
      while (parameterNames.hasMoreElements()) {
        String name = parameterNames.nextElement();
        String[] values = req.getParameterValues(name);
        logger.debug(TAB + TAB + "{} = {}", name, values);
      }
    }

  }

  // 简单处理ObjectMapper
  @SuppressWarnings("serial")
  public static class JackJsonMapper extends ObjectMapper {
    public JackJsonMapper() {
      // 设置输出时包含属性的风格
      this.setSerializationInclusion(Include.NON_NULL);
      this.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
      this.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
      // 允许单引号、允许不带引号的字段名称
      this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
      this.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
      // enum
      this.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
      this.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
      // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
      this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      // 空值处理为空串
      // this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
      // @Override
      // public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
      // JsonProcessingException {
      // jgen.writeString("");
      // }
      // });
      // 进行HTML解码。
      this.registerModule(new SimpleModule().addSerializer(String.class, new JsonSerializer<String>() {
        @Override
        public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
          if (value != null) {
            jgen.writeString(StringEscapeUtils.unescapeHtml4(value));
          }
        }
      }));
      // 设置时区
      this.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }
  }

  // end

}
