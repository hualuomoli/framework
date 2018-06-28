<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.query.mapper.${javaName}QueryMapper">

  <!-- 返回的列 -->
  <sql id="columns">
  <#list columns as column>
    `${column.dbName}` as "${column.javaName}"<#if columns?size - column_index gt 1>,</#if>
  </#list>
  </sql>
  
  <!-- 查询的列 -->
  <sql id="querys">
  <#list columns as column>
    <#if !column.primary>
      <#if column.enum>
      <#-- 枚举 -->
    <if test="${column.javaName} != null">
      and `${column.dbName}` = ${r"#{"}${column.javaName}${r"}"}
    </if>
    <if test="${column.javaName}Ins != null">
      <foreach collection="${column.javaName}Ins" item="obj" open="and (" close=")" separator="or">
        `${column.dbName}` = ${r"#{"}obj${r"}"}
      </foreach>
    </if>
    <if test="${column.javaName}NotIns != null">
      <foreach collection="${column.javaName}NotIns" item="obj" open="and (" close=")" separator="and">
        `${column.dbName}` <![CDATA[ <> ]]> ${r"#{"}obj${r"}"}
      </foreach>
    </if>
      <#elseif column.javaTypeName == 'java.lang.String'>
      <#-- 字符串 -->
    <if test="${column.javaName} != null and ${column.javaName} != ''">
      and `${column.dbName}` = ${r"#{"}${column.javaName}${r"}"}
    </if>
    <if test="${column.javaName}LeftLike != null and ${column.javaName}LeftLike != ''"> 
      and `${column.dbName}` like CONCAT(${r"#{"}${column.javaName}LeftLike${r"}"}, '%')
    </if>
    <if test="${column.javaName}RightLike != null and ${column.javaName}RightLike != ''"> 
      and `${column.dbName}` like CONCAT('%', ${r"#{"}${column.javaName}RightLike${r"}"})
    </if>
    <if test="${column.javaName}Like != null and ${column.javaName}Like != ''"> 
      and `${column.dbName}` like CONCAT('%', ${r"#{"}${column.javaName}Like${r"}"}, '%')
    </if>
    <if test="${column.javaName}NotEquals != null and ${column.javaName}NotEquals != ''"> 
      and `${column.dbName}` <![CDATA[ <> ]]> ${r"#{"}${column.javaName}NotEquals${r"}"}
    </if>
    <if test="${column.javaName}Ins != null">
      <foreach collection="${column.javaName}Ins" item="obj" open="and (" close=")" separator="or">
        `${column.dbName}` = ${r"#{"}obj${r"}"}
      </foreach>
    </if>
    <if test="${column.javaName}NotIns != null">
      <foreach collection="${column.javaName}NotIns" item="obj" open="and (" close=")" separator="and">
        `${column.dbName}` <![CDATA[ <> ]]> ${r"#{"}obj${r"}"}
      </foreach>
    </if>
      <#elseif column.javaTypeName == 'java.lang.Integer'>
      <#-- Integer -->
    <if test="${column.javaName} != null">
      and `${column.dbName}` = ${r"#{"}${column.javaName}${r"}"}
    </if>
    <if test="${column.javaName}GreaterThan != null"> 
      and `${column.dbName}` <![CDATA[ > ]]> ${r"#{"}${column.javaName}GreaterThan${r"}"}
    </if>
    <if test="${column.javaName}GreaterEqual != null"> 
      and `${column.dbName}` <![CDATA[ >= ]]> ${r"#{"}${column.javaName}GreaterEqual${r"}"}
    </if>
    <if test="${column.javaName}LessThan != null"> 
      and `${column.dbName}` <![CDATA[ < ]]> ${r"#{"}${column.javaName}LessThan${r"}"}
    </if>
    <if test="${column.javaName}LessEqual != null"> 
      and `${column.dbName}` <![CDATA[ <= ]]> ${r"#{"}${column.javaName}LessEqual${r"}"}
    </if>
    <if test="${column.javaName}NotEquals != null and ${column.javaName}NotEquals != ''"> 
      and `${column.dbName}` <![CDATA[ <> ]]> ${r"#{"}${column.javaName}NotEquals${r"}"}
    </if>
    <if test="${column.javaName}Ins != null">
      <foreach collection="${column.javaName}Ins" item="obj" open="and (" close=")" separator="or">
        `${column.dbName}` = ${r"#{"}obj${r"}"}
      </foreach>
    </if>
    <if test="${column.javaName}NotIns != null">
      <foreach collection="${column.javaName}NotIns" item="obj" open="and (" close=")" separator="and">
        `${column.dbName}` <![CDATA[ <> ]]> ${r"#{"}obj${r"}"}
      </foreach>
    </if>
      <#elseif column.javaTypeName == 'java.lang.Long'>
      <#-- Long -->
    <if test="${column.javaName} != null">
      and `${column.dbName}` = ${r"#{"}${column.javaName}${r"}"}
    </if>
    <if test="${column.javaName}GreaterThan != null"> 
      and `${column.dbName}` <![CDATA[ > ]]> ${r"#{"}${column.javaName}GreaterThan${r"}"}
    </if>
    <if test="${column.javaName}GreaterEqual != null"> 
      and `${column.dbName}` <![CDATA[ >= ]]> ${r"#{"}${column.javaName}GreaterEqual${r"}"}
    </if>
    <if test="${column.javaName}LessThan != null"> 
      and `${column.dbName}` <![CDATA[ < ]]> ${r"#{"}${column.javaName}LessThan${r"}"}
    </if>
    <if test="${column.javaName}LessEqual != null"> 
      and `${column.dbName}` <![CDATA[ <= ]]> ${r"#{"}${column.javaName}LessEqual${r"}"}
    </if>
    <if test="${column.javaName}NotEquals != null and ${column.javaName}NotEquals != ''"> 
      and `${column.dbName}` <![CDATA[ <> ]]> ${r"#{"}${column.javaName}NotEquals${r"}"}
    </if>
    <if test="${column.javaName}Ins != null">
      <foreach collection="${column.javaName}Ins" item="obj" open="and (" close=")" separator="or">
        `${column.dbName}` = ${r"#{"}obj${r"}"}
      </foreach>
    </if>
    <if test="${column.javaName}NotIns != null">
      <foreach collection="${column.javaName}NotIns" item="obj" open="and (" close=")" separator="and">
        `${column.dbName}` <![CDATA[ <> ]]> ${r"#{"}obj${r"}"}
      </foreach>
    </if>
      <#elseif column.javaTypeName == 'java.lang.Double'>
      <#-- Double -->
    <if test="${column.javaName} != null">
      and `${column.dbName}` = ${r"#{"}${column.javaName}${r"}"}
    </if>
    <if test="${column.javaName}GreaterThan != null"> 
      and `${column.dbName}` <![CDATA[ > ]]> ${r"#{"}${column.javaName}GreaterThan${r"}"}
    </if>
    <if test="${column.javaName}GreaterEqual != null"> 
      and `${column.dbName}` <![CDATA[ >= ]]> ${r"#{"}${column.javaName}GreaterEqual${r"}"}
    </if>
    <if test="${column.javaName}LessThan != null"> 
      and `${column.dbName}` <![CDATA[ < ]]> ${r"#{"}${column.javaName}LessThan${r"}"}
    </if>
    <if test="${column.javaName}LessEqual != null"> 
      and `${column.dbName}` <![CDATA[ <= ]]> ${r"#{"}${column.javaName}LessEqual${r"}"}
    </if>
    <if test="${column.javaName}NotEquals != null and ${column.javaName}NotEquals != ''"> 
      and `${column.dbName}` <![CDATA[ <> ]]> ${r"#{"}${column.javaName}NotEquals${r"}"}
    </if>
    <if test="${column.javaName}Ins != null">
      <foreach collection="${column.javaName}Ins" item="obj" open="and (" close=")" separator="or">
        `${column.dbName}` = ${r"#{"}obj${r"}"}
      </foreach>
    </if>
    <if test="${column.javaName}NotIns != null">
      <foreach collection="${column.javaName}NotIns" item="obj" open="and (" close=")" separator="and">
        `${column.dbName}` <![CDATA[ <> ]]> ${r"#{"}obj${r"}"}
      </foreach>
    </if>
      <#elseif column.javaTypeName == 'java.util.Date'>
      <#-- Date -->
    <if test="${column.javaName} != null">
      and `${column.dbName}` = ${r"#{"}${column.javaName}${r"}"}
    </if>
    <if test="${column.javaName}GreaterThan != null"> 
      and `${column.dbName}` <![CDATA[ > ]]> ${r"#{"}${column.javaName}GreaterThan${r"}"}
    </if>
    <if test="${column.javaName}GreaterEqual != null"> 
      and `${column.dbName}` <![CDATA[ >= ]]> ${r"#{"}${column.javaName}GreaterEqual${r"}"}
    </if>
    <if test="${column.javaName}LessThan != null"> 
      and `${column.dbName}` <![CDATA[ < ]]> ${r"#{"}${column.javaName}LessThan${r"}"}
    </if>
    <if test="${column.javaName}LessEqual != null"> 
      and `${column.dbName}` <![CDATA[ <= ]]> ${r"#{"}${column.javaName}LessEqual${r"}"}
    </if>
      <#else>
      <#-- 其他 -->
      </#if>
    </#if>
  </#list>
  </sql>
  
  <!-- 查询列表 -->
  <select id="findList" resultType="${packageName}.entity.${javaName}">
    select
      <include refid="columns" />
    from `${tableName}`
    <where>
      <include refid="querys" />
    </where>
  </select>
  
</mapper>