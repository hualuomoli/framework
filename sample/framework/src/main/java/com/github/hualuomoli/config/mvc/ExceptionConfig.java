package com.github.hualuomoli.config.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;

/**
 * 配置异常处理
 * {@linkplain HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, HttpServletResponse, Object, Exception)}
 */
@Configuration(value = "com.github.hualuomoli.config.mvc.ExceptionConfig")
@ControllerAdvice
public class ExceptionConfig {

  static {
    System.out.println("初始化mvc 错误处理");
  }

  // 请求方法不允许
  @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public ModelAndView methodNotAllowed(HttpServletResponse response, HttpRequestMethodNotSupportedException e) {
    return this.getModelAndView("/error/methodNotAllowed", e.getMessage());
  }

  // 请求协议不允许
  @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  public ModelAndView unsupportedMediaType(HttpServletResponse response, HttpMediaTypeNotSupportedException e) {
    return this.getModelAndView("/error/unsupportedMediaType", e.getMessage());
  }

  // 空指针异常
  @ExceptionHandler(value = NullPointerException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView nullPointer(HttpServletResponse response, NullPointerException e) {
    return this.getModelAndView("/error/exception", e.getMessage());
  }

  // 参数不合法异常
  @ExceptionHandler(value = IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView illegalArgument(HttpServletResponse response, IllegalArgumentException e) {
    return this.getModelAndView("/error/exception", e.getMessage());
  }

  // 异常
  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView exception(HttpServletResponse response, Exception e) {
    return this.getModelAndView("/error/exception", e.getMessage());
  }

  // 获取视图,并绑定错误信息
  private ModelAndView getModelAndView(String viewName, String message) {
    Map<String, String> model = Maps.newHashMap();
    model.put("message", message);
    return new ModelAndView(viewName, model);
  }

}
