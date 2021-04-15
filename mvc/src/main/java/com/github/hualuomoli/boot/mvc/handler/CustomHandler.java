package com.github.hualuomoli.boot.mvc.handler;

import org.springframework.lang.Nullable;

import java.lang.reflect.Field;

public interface CustomHandler {

    /**
     * 是否是客户自定义的类
     *
     * @param clazz 类型
     * @return 如果是客户定义的实体类返回true, 否则返回false
     */
    boolean customClass(Class<?> clazz);

    /**
     * 参数名
     *
     * @param field 属性
     * @return 属性对应参数名
     */
    @Nullable
    String parameterName(Field field);

}
