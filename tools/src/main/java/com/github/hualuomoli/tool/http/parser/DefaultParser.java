package com.github.hualuomoli.tool.http.parser;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.hualuomoli.tool.http.Param;
import com.github.hualuomoli.tool.util.ClassUtils;

public class DefaultParser implements Parser {

  private static final Formater DEFAULT_FORMATER = new DefaultFormater();

  @Override
  public List<Param> parse(Object object) {
    return this.parse(object, DEFAULT_FORMATER);
  }

  @Override
  public List<Param> parse(Object object, Formater formater) {
    if (object == null) {
      return new ArrayList<Param>();
    }
    return this.parse(null, object, formater);
  }

  @SuppressWarnings("unchecked")
  private List<Param> parse(String prefix, Object object, Formater formater) {
    List<Param> params = new ArrayList<Param>();

    if (object == null) {
      return params;
    }

    Class<?> clazz = object.getClass();

    // String
    if (String.class.isAssignableFrom(clazz)) {
      params.add(new Param(prefix, (String) object));
      return params;
    }

    // Integer
    if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz)) {
      params.add(new Param(prefix, String.valueOf(object)));
      return params;
    }

    // Long
    if (Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)) {
      params.add(new Param(prefix, String.valueOf(object)));
      return params;
    }

    // Double
    if (Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz)) {
      params.add(new Param(prefix, String.valueOf(object)));
      return params;
    }

    // Boolean
    if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz)) {
      params.add(new Param(prefix, String.valueOf(object)));
      return params;
    }

    // Date 
    if (Date.class.isAssignableFrom(clazz)) {
      params.add(new Param(prefix, formater.format((Date) object, null, null)));
      return params;
    }

    // Enum
    if (Enum.class.isAssignableFrom(clazz)) {
      params.add(new Param(prefix, ((Enum<?>) object).name()));
      return params;
    }

    // Map
    if (Map.class.isAssignableFrom(clazz)) {
      Map<String, Object> map = (Map<String, Object>) object;
      for (String key : map.keySet()) {
        params.addAll(this.parse(this.getPrefix(prefix, key), map.get(key), formater));
      }
      return params;
    }

    // object
    List<Field> fields = ClassUtils.getFields(object.getClass());
    for (Field field : fields) {
      Object value = ClassUtils.getFieldValue(field, object);
      if (value == null) {
        continue;
      }
      params.addAll(this.parse(this.getPrefix(prefix, field.getName()), value, formater));
    }

    return params;

  }

  private String getPrefix(String prefix, String key) {
    if (prefix == null) {
      return key;
    }
    return prefix + "." + key;
  }

  private static class DefaultFormater implements Formater {

    @Override
    public String format(Date date, Field field, Object handler) {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) date);
    }

  }

}
