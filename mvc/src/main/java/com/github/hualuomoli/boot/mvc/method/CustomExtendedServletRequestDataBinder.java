package com.github.hualuomoli.boot.mvc.method;

import com.github.hualuomoli.boot.mvc.handler.CustomHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.core.ResolvableType;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomExtendedServletRequestDataBinder extends ExtendedServletRequestDataBinder {

    private final Logger logger = LoggerFactory.getLogger(CustomExtendedServletRequestDataBinder.class);

    private final Map<Class<?>, Map<String, Field>> CACHE = new HashMap<Class<?>, Map<String, Field>>();
    private static CustomHandler customHandler;

    public static void setCustomHandler(CustomHandler customHandler) {
        CustomExtendedServletRequestDataBinder.customHandler = customHandler;
    }

    public CustomExtendedServletRequestDataBinder(Object target, String objectName) {
        super(target, objectName);
    }

    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);
        // 增加客户自定义绑定
        this.replaceCustomParameters(mpvs, getTarget().getClass());
    }

    /**
     * 替换客户定义的参数值
     *
     * @param mpvs  参数值集合
     * @param clazz 类型
     */
    private void replaceCustomParameters(MutablePropertyValues mpvs, Class<?> clazz) {

        PropertyValue[] propertyValues = mpvs.getPropertyValues();
        for (int i = 0; i < propertyValues.length; i++) {
            PropertyValue propertyValue = propertyValues[i];

            // handle
            PropertyValue parsePropertyValue = this.handle(propertyValue, clazz);

            // 没有转换
            if (parsePropertyValue == null || parsePropertyValue == propertyValue) {
                continue;
            }

            // 重新设置
            mpvs.setPropertyValueAt(parsePropertyValue, i);
        }

    }

    /**
     * 处理绑定的参数值(如果参数名需要转换返回一个新的，否则返回null)
     *
     * @param propertyValue 参数值
     * @param clazz         类型
     * @return 转换后的参数值
     */
    private PropertyValue handle(PropertyValue propertyValue, Class<?> clazz) {
        // 参数名
        String parameterName = propertyValue.getName();
        // 参数值
        Object parameterValue = propertyValue.getValue();

        // 转换
        String parseParameterName = this.parseParameterName(parameterName, clazz);

        // 不需要转换
        if (parameterName.equals(parseParameterName)) {
            return propertyValue;
        }

        logger.debug("parse parameter name {} to {}", parameterName, parseParameterName);
        return new PropertyValue(parseParameterName, parameterValue);
    }

    /**
     * 解析参数转换器
     *
     * @param parameterName 参数名
     * @param clazz         类型
     * @return 转换后的参数名
     */
    public String parseParameterName(String parameterName, Class<?> clazz) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+[a-zA-Z0-9_]+");

        // 分隔的内容
        List<String> contents = Arrays.asList(pattern.split(parameterName));

        List<String> groups = new ArrayList<String>();
        Matcher matcher = pattern.matcher(parameterName);
        while (matcher.find()) {
            String group = matcher.group();
            groups.add(group);
        }

        // 转换成一个数据的内容
        if (contents.size() == 0) {
            contents = Arrays.asList("");
        }

        // 替换拼接
        StringBuilder buffer = new StringBuilder();
        int length = groups.size();
        Class<?> currentClass = clazz;
        String fieldName = null;

        List<Field> fields = null;
        String content = null;
        String group = null;
        for (int i = 0; i < length; i++) {
            content = contents.get(i);
            group = groups.get(i);
            fieldName = group;

            // 处理可处理的类
            if (customHandler.customClass(currentClass)) {
                // 获取参数对应的属性
                fields = this.getFields(currentClass);
                currentClass = null;

                for (Field field : fields) {
                    if (group.equals(Optional.ofNullable(customHandler.parameterName(field)).orElse(field.getName()))) {
                        fieldName = field.getName();
                        currentClass = field.getType();

                        // Collection
                        if (Collection.class.isAssignableFrom(currentClass)) {
                            currentClass = ResolvableType.forField(field).getGeneric(0).resolve();
                        }

                        break;
                    }
                }
            }

            // 拼接
            buffer.append(content).append(fieldName);
        } // end for

        // add last content
        if (contents.size() > groups.size()) {
            buffer.append(contents.get(groups.size()));
        }

        // end
        return buffer.toString();
    }

    /**
     * 获取类的所有属性(包含父类)
     *
     * @param clazz 类型
     * @return 所有属性
     */
    private List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        Set<String> childFieldNames = new HashSet<String>();

        this.addFields(clazz, fields, childFieldNames);

        return fields;
    }

    /**
     * 添加类的属性(含父类)
     *
     * @param clazz           类型
     * @param fields          属性集合
     * @param childFieldNames 子类已经存在的属性名
     */
    private void addFields(Class<?> clazz, List<Field> fields, Set<String> childFieldNames) {

        // root class
        if (clazz == null || clazz == Object.class) {
            return;
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (childFieldNames.contains(field.getName())) {
                continue;
            }

            fields.add(field);
            childFieldNames.add(field.getName());
        }

        // add super class
        addFields(clazz.getSuperclass(), fields, childFieldNames);
    }

}
