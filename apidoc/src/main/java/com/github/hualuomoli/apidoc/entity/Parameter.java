package com.github.hualuomoli.apidoc.entity;

import java.util.List;

import com.github.hualuomoli.apidoc.enums.ParameterTypeEnum;

/**
 * 参数
 */
public class Parameter {

  /** 参数类型 */
  private ParameterTypeEnum parameterType;

  /** 参数名 */
  private String name;
  /** 参数类型 */
  private String type;
  /** 是否必填 */
  private boolean required;
  /** 数据最大长度 */
  private Integer maxLength;
  /** 描述 */
  private String description;
  /** 示例 */
  private String sample;
  /** 数据级别 */
  private Integer level;

  // 子参数
  private List<Parameter> parameters;

  public ParameterTypeEnum getParameterType() {
    return parameterType;
  }

  public void setParameterType(ParameterTypeEnum parameterType) {
    this.parameterType = parameterType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public Integer getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(Integer maxLength) {
    this.maxLength = maxLength;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSample() {
    return sample;
  }

  public void setSample(String sample) {
    this.sample = sample;
  }

  public List<Parameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  @Override
  public String toString() {
    return "Parameter [parameterType=" + parameterType + ", name=" + name + ", type=" + type + ", required=" + required + ", maxLength=" + maxLength + ", description=" + description + ", sample="
        + sample + ", level=" + level + ", parameters=" + parameters + "]";
  }

}
