package com.github.hualuomoli.apidoc.entity;

import java.util.List;

/**
 * API文档
 */
public class ApiDoc {

  /** 模块 */
  private String module;
  /** 方法名 */
  private String method;
  /** 功能描述 */
  private String title;
  /** 功能详细描述 */
  private String description;
  /** 功能错误 */
  private List<Error> errors;

  /** 请求参数 */
  private List<Parameter> requests;
  /** 响应参数 */
  private List<Parameter> responses;

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Error> getErrors() {
    return errors;
  }

  public void setErrors(List<Error> errors) {
    this.errors = errors;
  }

  public List<Parameter> getRequests() {
    return requests;
  }

  public void setRequests(List<Parameter> requests) {
    this.requests = requests;
  }

  public List<Parameter> getResponses() {
    return responses;
  }

  public void setResponses(List<Parameter> responses) {
    this.responses = responses;
  }

  public String getRelativePath() {
    return method.replaceAll("[.]", "/");
  }

  @Override
  public String toString() {
    return "ApiDoc [module=" + module + ", method=" + method + ", title=" + title + ", description=" + description + ", errors=" + errors + ", requests=" + requests + ", responses=" + responses + "]";
  }

}
