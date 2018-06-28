package ${packageName}.query.entity;

import ${packageName}.entity.${javaName};

// ${comment!''}
public class ${javaName}Query extends ${javaName} {

  <#list columns as column>
    <#if !column.primary>
  /** ${column.comment!''} */
      <#if column.enum>
      <#-- 枚举 -->
  private ${column.javaTypeName}[] ${column.javaName}Ins;
  private ${column.javaTypeName}[] ${column.javaName}NotIns;
      <#elseif column.javaTypeName == 'java.lang.String'>
      <#-- 字符串 -->
  private ${column.javaTypeName} ${column.javaName}LeftLike;
  private ${column.javaTypeName} ${column.javaName}RightLike;
  private ${column.javaTypeName} ${column.javaName}Like;
  private ${column.javaTypeName} ${column.javaName}NotEquals;
  private ${column.javaTypeName}[] ${column.javaName}Ins;
  private ${column.javaTypeName}[] ${column.javaName}NotIns;
      <#elseif column.javaTypeName == 'java.lang.Integer'>
      <#-- Integer -->
  private ${column.javaTypeName} ${column.javaName}GreaterThan;
  private ${column.javaTypeName} ${column.javaName}GreaterEqual;
  private ${column.javaTypeName} ${column.javaName}LessEqual;
  private ${column.javaTypeName} ${column.javaName}LessThan;
  private ${column.javaTypeName} ${column.javaName}NotEquals;
  private ${column.javaTypeName}[] ${column.javaName}Ins;
  private ${column.javaTypeName}[] ${column.javaName}NotIns;
      <#elseif column.javaTypeName == 'java.lang.Long'>
      <#-- Long -->
  private ${column.javaTypeName} ${column.javaName}GreaterThan;
  private ${column.javaTypeName} ${column.javaName}GreaterEqual;
  private ${column.javaTypeName} ${column.javaName}LessEqual;
  private ${column.javaTypeName} ${column.javaName}LessThan;
  private ${column.javaTypeName} ${column.javaName}NotEquals;
  private ${column.javaTypeName}[] ${column.javaName}Ins;
  private ${column.javaTypeName}[] ${column.javaName}NotIns;
      <#elseif column.javaTypeName == 'java.lang.Double'>
      <#-- Double -->
  private ${column.javaTypeName} ${column.javaName}GreaterThan;
  private ${column.javaTypeName} ${column.javaName}GreaterEqual;
  private ${column.javaTypeName} ${column.javaName}LessEqual;
  private ${column.javaTypeName} ${column.javaName}LessThan;
  private ${column.javaTypeName} ${column.javaName}NotEquals;
  private ${column.javaTypeName}[] ${column.javaName}Ins;
  private ${column.javaTypeName}[] ${column.javaName}NotIns;
      <#elseif column.javaTypeName == 'java.util.Date'>
      <#-- Date -->
  private ${column.javaTypeName} ${column.javaName}GreaterThan;
  private ${column.javaTypeName} ${column.javaName}GreaterEqual;
  private ${column.javaTypeName} ${column.javaName}LessEqual;
  private ${column.javaTypeName} ${column.javaName}LessThan;
      <#else>
      <#-- 其他 -->
  // TODO
      </#if>
    </#if>
  </#list>

  public ${javaName}Query() {
  }

  // getter and setter
  <#list columns as column>
    <#if !column.primary>

      <#if column.enum>
      <#-- 枚举 -->
  public ${column.javaTypeName}[] get${column.javaName?cap_first}Ins() {
    return ${column.javaName}Ins;
  }

  public void set${column.javaName?cap_first}Ins(${column.javaTypeName}[] ${column.javaName}Ins) {
    this.${column.javaName}Ins = ${column.javaName}Ins;
  }

  public ${column.javaTypeName}[] get${column.javaName?cap_first}NotIns() {
    return ${column.javaName}NotIns;
  }

  public void set${column.javaName?cap_first}NotIns(${column.javaTypeName}[] ${column.javaName}NotIns) {
    this.${column.javaName}NotIns = ${column.javaName}NotIns;
  }
      <#elseif column.javaTypeName == 'java.lang.String'>
      <#-- 字符串 -->
  public ${column.javaTypeName} get${column.javaName?cap_first}LeftLike() {
    return ${column.javaName}LeftLike;
  }

  public void set${column.javaName?cap_first}LeftLike(${column.javaTypeName} ${column.javaName}LeftLike) {
    this.${column.javaName}LeftLike = ${column.javaName}LeftLike;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}RightLike() {
    return ${column.javaName}RightLike;
  }

  public void set${column.javaName?cap_first}RightLike(${column.javaTypeName} ${column.javaName}RightLike) {
    this.${column.javaName}RightLike = ${column.javaName}RightLike;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}Like() {
    return ${column.javaName}Like;
  }

  public void set${column.javaName?cap_first}Like(${column.javaTypeName} ${column.javaName}Like) {
    this.${column.javaName}Like = ${column.javaName}Like;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}NotEquals() {
    return ${column.javaName}NotEquals;
  }

  public void set${column.javaName?cap_first}NotEquals(${column.javaTypeName} ${column.javaName}NotEquals) {
    this.${column.javaName}NotEquals = ${column.javaName}NotEquals;
  }

  public ${column.javaTypeName}[] get${column.javaName?cap_first}Ins() {
    return ${column.javaName}Ins;
  }

  public void set${column.javaName?cap_first}Ins(${column.javaTypeName}[] ${column.javaName}Ins) {
    this.${column.javaName}Ins = ${column.javaName}Ins;
  }

  public ${column.javaTypeName}[] get${column.javaName?cap_first}NotIns() {
    return ${column.javaName}NotIns;
  }

  public void set${column.javaName?cap_first}NotIns(${column.javaTypeName}[] ${column.javaName}NotIns) {
    this.${column.javaName}NotIns = ${column.javaName}NotIns;
  }
      <#elseif column.javaTypeName == 'java.lang.Integer'>
      <#-- Integer -->
  public ${column.javaTypeName} get${column.javaName?cap_first}GreaterThan() {
    return ${column.javaName}GreaterThan;
  }

  public void set${column.javaName}GreaterThan(${column.javaTypeName} ${column.javaName}GreaterThan) {
    this.${column.javaName}GreaterThan = ${column.javaName}GreaterThan;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}GreaterEqual() {
    return ${column.javaName}GreaterEqual;
  }

  public void set${column.javaName?cap_first}GreaterEqual(${column.javaTypeName} ${column.javaName}GreaterEqual) {
    this.${column.javaName}GreaterEqual = ${column.javaName}GreaterEqual;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}LessEqual() {
    return ${column.javaName}LessEqual;
  }

  public void set${column.javaName?cap_first}LessEqual(${column.javaTypeName} ${column.javaName}LessEqual) {
    this.${column.javaName}LessEqual = ${column.javaName}LessEqual;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}LessThan() {
    return ${column.javaName}LessThan;
  }

  public void set${column.javaName?cap_first}LessThan(${column.javaTypeName} ${column.javaName}LessThan) {
    this.${column.javaName}LessThan = ${column.javaName}LessThan;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}NotEquals() {
    return ${column.javaName}NotEquals;
  }

  public void set${column.javaName?cap_first}NotEquals(${column.javaTypeName} ${column.javaName}NotEquals) {
    this.${column.javaName}NotEquals = ${column.javaName}NotEquals;
  }

  public ${column.javaTypeName}[] get${column.javaName?cap_first}Ins() {
    return ${column.javaName}Ins;
  }

  public void set${column.javaName?cap_first}Ins(${column.javaTypeName}[] ${column.javaName}Ins) {
    this.${column.javaName}Ins = ${column.javaName}Ins;
  }

  public ${column.javaTypeName}[] get${column.javaName?cap_first}NotIns() {
    return ${column.javaName}NotIns;
  }

  public void set${column.javaName?cap_first}NotIns(${column.javaTypeName}[] ${column.javaName}NotIns) {
    this.${column.javaName}NotIns = ${column.javaName}NotIns;
  }
      <#elseif column.javaTypeName == 'java.lang.Long'>
      <#-- Long -->
  public ${column.javaTypeName} get${column.javaName?cap_first}GreaterThan() {
    return ${column.javaName}GreaterThan;
  }

  public void set${column.javaName}GreaterThan(${column.javaTypeName} ${column.javaName}GreaterThan) {
    this.${column.javaName}GreaterThan = ${column.javaName}GreaterThan;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}GreaterEqual() {
    return ${column.javaName}GreaterEqual;
  }

  public void set${column.javaName?cap_first}GreaterEqual(${column.javaTypeName} ${column.javaName}GreaterEqual) {
    this.${column.javaName}GreaterEqual = ${column.javaName}GreaterEqual;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}LessEqual() {
    return ${column.javaName}LessEqual;
  }

  public void set${column.javaName?cap_first}LessEqual(${column.javaTypeName} ${column.javaName}LessEqual) {
    this.${column.javaName}LessEqual = ${column.javaName}LessEqual;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}LessThan() {
    return ${column.javaName}LessThan;
  }

  public void set${column.javaName?cap_first}LessThan(${column.javaTypeName} ${column.javaName}LessThan) {
    this.${column.javaName}LessThan = ${column.javaName}LessThan;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}NotEquals() {
    return ${column.javaName}NotEquals;
  }

  public void set${column.javaName?cap_first}NotEquals(${column.javaTypeName} ${column.javaName}NotEquals) {
    this.${column.javaName}NotEquals = ${column.javaName}NotEquals;
  }

  public ${column.javaTypeName}[] get${column.javaName?cap_first}Ins() {
    return ${column.javaName}Ins;
  }

  public void set${column.javaName?cap_first}Ins(${column.javaTypeName}[] ${column.javaName}Ins) {
    this.${column.javaName}Ins = ${column.javaName}Ins;
  }

  public ${column.javaTypeName}[] get${column.javaName?cap_first}NotIns() {
    return ${column.javaName}NotIns;
  }

  public void set${column.javaName?cap_first}NotIns(${column.javaTypeName}[] ${column.javaName}NotIns) {
    this.${column.javaName}NotIns = ${column.javaName}NotIns;
  }
      <#elseif column.javaTypeName == 'java.lang.Double'>
      <#-- Double -->
  public ${column.javaTypeName} get${column.javaName?cap_first}GreaterThan() {
    return ${column.javaName}GreaterThan;
  }

  public void set${column.javaName}GreaterThan(${column.javaTypeName} ${column.javaName}GreaterThan) {
    this.${column.javaName}GreaterThan = ${column.javaName}GreaterThan;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}GreaterEqual() {
    return ${column.javaName}GreaterEqual;
  }

  public void set${column.javaName?cap_first}GreaterEqual(${column.javaTypeName} ${column.javaName}GreaterEqual) {
    this.${column.javaName}GreaterEqual = ${column.javaName}GreaterEqual;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}LessEqual() {
    return ${column.javaName}LessEqual;
  }

  public void set${column.javaName?cap_first}LessEqual(${column.javaTypeName} ${column.javaName}LessEqual) {
    this.${column.javaName}LessEqual = ${column.javaName}LessEqual;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}LessThan() {
    return ${column.javaName}LessThan;
  }

  public void set${column.javaName?cap_first}LessThan(${column.javaTypeName} ${column.javaName}LessThan) {
    this.${column.javaName}LessThan = ${column.javaName}LessThan;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}NotEquals() {
    return ${column.javaName}NotEquals;
  }

  public void set${column.javaName?cap_first}NotEquals(${column.javaTypeName} ${column.javaName}NotEquals) {
    this.${column.javaName}NotEquals = ${column.javaName}NotEquals;
  }

  public ${column.javaTypeName}[] get${column.javaName?cap_first}Ins() {
    return ${column.javaName}Ins;
  }

  public void set${column.javaName?cap_first}Ins(${column.javaTypeName}[] ${column.javaName}Ins) {
    this.${column.javaName}Ins = ${column.javaName}Ins;
  }

  public ${column.javaTypeName}[] get${column.javaName?cap_first}NotIns() {
    return ${column.javaName}NotIns;
  }

  public void set${column.javaName?cap_first}NotIns(${column.javaTypeName}[] ${column.javaName}NotIns) {
    this.${column.javaName}NotIns = ${column.javaName}NotIns;
  }
      <#elseif column.javaTypeName == 'java.util.Date'>
      <#-- Date -->
  public ${column.javaTypeName} get${column.javaName?cap_first}GreaterThan() {
    return ${column.javaName}GreaterThan;
  }

  public void set${column.javaName}GreaterThan(${column.javaTypeName} ${column.javaName}GreaterThan) {
    this.${column.javaName}GreaterThan = ${column.javaName}GreaterThan;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}GreaterEqual() {
    return ${column.javaName}GreaterEqual;
  }

  public void set${column.javaName?cap_first}GreaterEqual(${column.javaTypeName} ${column.javaName}GreaterEqual) {
    this.${column.javaName}GreaterEqual = ${column.javaName}GreaterEqual;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}LessEqual() {
    return ${column.javaName}LessEqual;
  }

  public void set${column.javaName?cap_first}LessEqual(${column.javaTypeName} ${column.javaName}LessEqual) {
    this.${column.javaName}LessEqual = ${column.javaName}LessEqual;
  }

  public ${column.javaTypeName} get${column.javaName?cap_first}LessThan() {
    return ${column.javaName}LessThan;
  }

  public void set${column.javaName?cap_first}LessThan(${column.javaTypeName} ${column.javaName}LessThan) {
    this.${column.javaName}LessThan = ${column.javaName}LessThan;
  }
      <#else>
      <#-- 其他 -->
  // TODO
      </#if>
    </#if>
  </#list>

}
