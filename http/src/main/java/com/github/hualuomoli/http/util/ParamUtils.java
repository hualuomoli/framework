package com.github.hualuomoli.http.util;

import com.github.hualuomoli.http.entity.Param;
import com.github.hualuomoli.logger.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 参数工具类
 */
public class ParamUtils {

    private static final String TAG = "com.github.hualuomoli.http.util.ParamUtils";

    /**
     * 解析
     * @param entity 实体类参数
     * @return 参数
     */
    public static List<Param> parse(Object entity) {
        return parse("", entity, null, null);
    }

    /**
     * 解析
     * @param entity 实体类参数
     * @param dateAnnotationClass 时间注解类
     * @param dateAnnotationMethodName 格式化字符串定义方法
     * @return 参数
     */
    public static List<Param> parse(Object entity, Class<? extends Annotation> dateAnnotationClass, String dateAnnotationMethodName) {
        return parse("", entity, dateAnnotationClass, dateAnnotationMethodName);
    }

    /**
     * 解析
     * @param entityPrefix 实体类前缀
     * @param entity 实体类参数
     * @param dateAnnotationClass 时间注解类
     * @param dateAnnotationMethodName 格式化字符串定义方法
     * @return 参数
     */
    private static List<Param> parse(String entityPrefix, Object entity, Class<? extends Annotation> dateAnnotationClass, String dateAnnotationMethodName) {

        List<Param> params = new ArrayList<Param>();
        Class<?> clazz = entity.getClass();

        List<Field> fields = getFields(clazz, new HashSet<String>());
        for (Field field : fields) {
            String name = field.getName();
            String methodName = null;
            String prefix = null;
            if (field.getType() == boolean.class) {
                prefix = "is";
            } else {
                prefix = "get";
            }
            methodName = prefix + name.substring(0, 1).toUpperCase();
            if (name.length() >= 2) {
                methodName = methodName + name.substring(1);
            }
            Method method = null;
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                // 没有定义这个方法
                Logger.warn(TAG, "there is no method {} defined in class {}", methodName, clazz);
                continue;
            }

            Object value = null;
            try {
                value = method.invoke(entity);
                if(value == null) {
                    continue;
                }
            } catch (IllegalAccessException e) {
                Logger.warn(TAG, "the method {} defined in class {} is no access", methodName, clazz, e);
                continue;
            } catch (InvocationTargetException e) {
                Logger.warn(TAG, "the method {} defined in class {} get value error.", methodName, clazz, e);
                continue;
            }

            String v = null;
            // 设置参数
            if (value instanceof String) {
                v = (String) value;
                params.add(new Param(entityPrefix + name, v));
            } else if (value instanceof Integer) {
                v = Integer.toString((Integer) value);
                params.add(new Param(entityPrefix + name, v));
            } else if (value instanceof Long) {
                v = Long.toString((Long) value);
                params.add(new Param(entityPrefix + name, v));
            } else if (value instanceof Float) {
                v = Float.toString((Float) value);
                params.add(new Param(entityPrefix + name, v));
            } else if (value instanceof Double) {
                v = Double.toString((Double) value);
                params.add(new Param(entityPrefix + name, v));
            } else if (value instanceof Boolean) {
                v = Boolean.toString((Boolean) value);
                params.add(new Param(entityPrefix + name, v));
            } else if (value instanceof Enum<?>) {
                v = ((Enum<?>) value).name();
                params.add(new Param(entityPrefix + name, v));
            } else if (value instanceof Date) {
                v = parse(field, (Date) value, dateAnnotationClass, dateAnnotationMethodName);
                params.add(new Param(entityPrefix + name, v));
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                Collection<?> c = (Collection<?>) value;
                int index = 0;
                for (Object o : c) {
                    params.addAll(parse(entityPrefix + name + "[" + index + "]", o, dateAnnotationClass, dateAnnotationMethodName));
                    index++;
                }
            } else {
                params.addAll(parse(name + ".", value, dateAnnotationClass, dateAnnotationMethodName));
            }
            // end for each
        }

        return params;
    }

    /**
     * 获取实体类属性
     * @param clazz 类
     * @param ignores 忽略的属性
     * @return 属性列表
     */
    private static List<Field> getFields(Class<?> clazz, Set<String> ignores) {
        List<Field> fieldList = new ArrayList<Field>();

        if (clazz == null) {
            return fieldList;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (ignores.contains(name)) {
                continue;
            }
            ignores.add(name);
            fieldList.add(field);
        }
        fieldList.addAll(getFields(clazz.getSuperclass(), ignores));
        return fieldList;
    }

    /**
     * 格式化时间
     * @param field Field
     * @param date 时间
     * @param dateAnnotationClass 时间注解类
     * @param dateAnnotationMethodName 格式化字符串定义方法
     * @return 时间
     */
    private static String parse(Field field, Date date, Class<? extends Annotation> dateAnnotationClass, String dateAnnotationMethodName) {
        if (dateAnnotationClass == null || dateAnnotationMethodName == null || dateAnnotationMethodName.trim().length() == 0) {
            return Long.toString(date.getTime());
        }

        Annotation anno = field.getAnnotation(dateAnnotationClass);
        if (anno == null) {
            return Long.toString(date.getTime());
        }

        try {
            String pattern = (String) anno.getClass().getMethod(dateAnnotationMethodName).invoke(anno);
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            Logger.error(TAG, e.getMessage(), e);
            return Long.toString(date.getTime());
        }

    }


}
