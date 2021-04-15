package com.github.hualuomoli.boot.mvc.method;

import com.github.hualuomoli.boot.mvc.handler.CustomHandler;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.core.ResolvableType;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CustomExtendedServletRequestDataBinder extends ExtendedServletRequestDataBinder {

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
        this.addCustomParameters(mpvs, request, getTarget().getClass(), "", "");
    }

    private void addCustomParameters(MutablePropertyValues mpvs, ServletRequest request, Class<?> clazz, String originParentName, String targetParentName) {

        List<Field> fields = this.getFields(clazz);
        for (Field field : fields) {

            // 包含当前属性
            if (mpvs.contains(targetParentName + field.getName())) {
                continue;
            }

            // 获取自定义属性名
            String customFieldName = Optional.ofNullable(customHandler.parameterName(field)).orElse(field.getName());

            // 客户定义实体类
            if (customHandler.customClass(field.getType())) {
                this.addCustomParameters(mpvs, request, field.getType(), originParentName + customFieldName + ".", targetParentName + field.getName() + ".");
                continue;
            }

            // 集合
            if (Collection.class.isAssignableFrom(field.getType())) {
                Class<?> type = ResolvableType.forField(field).getGeneric(0).resolve();

                // 当前项目
                if (customHandler.customClass(type)) {

                    // 集合的最大下标
                    int maxIndex = this.getMaxIndex(mpvs, request, originParentName + customFieldName);
                    if (maxIndex == 0) {
                        continue;
                    }

                    // 遍历处理
                    for (int i = 0; i <= maxIndex; i++) {
                        this.addCustomParameters(mpvs, request, type, originParentName + customFieldName + "[" + i + "].", targetParentName + field.getName() + "[" + i + "].");
                    }

                    continue;
                }
            }

            // 获取自定义属性值
            PropertyValue propertyValue = mpvs.getPropertyValue(originParentName + customFieldName);
            if (propertyValue == null) {
                continue;
            }

            // 添加到属性值中
            mpvs.add(targetParentName + field.getName(), propertyValue.getValue());
        }


        // end
    }

    private int getMaxIndex(MutablePropertyValues mpvs, ServletRequest request, String originParentName) {
        int maxIndex = 0;

        PropertyValue[] propertyValues = mpvs.getPropertyValues();
        int index = -1;
        for (PropertyValue propertyValue : propertyValues) {
            String name = propertyValue.getName();
            if (name.startsWith(originParentName + "[")) {
                index = Integer.parseInt(name.substring(originParentName.length() + 1, name.indexOf("]", originParentName.length() + 1)));
                if (index > maxIndex) {
                    maxIndex = index;
                }
            }
        }

        return maxIndex;
    }


    private List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        Set<String> ignores = new HashSet<String>();
        this.addFields(fields, clazz, ignores);

        return fields;
    }

    private void addFields(List<Field> fields, Class<?> clazz, Set<String> ignores) {
        if (clazz == null || clazz == Object.class) {
            return;
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {

            // child contain this field
            if (ignores.contains(field.getName())) {
                continue;
            }

            // ad current field
            fields.add(field);
            ignores.add(field.getName());
        }

        // add super
        this.addFields(fields, clazz.getSuperclass(), ignores);
    }

}
