package com.github.hualuomoli.tool.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class工具
 */
public class ClassUtils {

  /**
   * 获取类的所有属性及所有父属性
   * @param clazz 类
   * @return 类的属性及所有父属性
   */
  public static List<Field> getFields(Class<?> clazz) {
    return getFields(clazz, null);
  }

  /**
   * 获取类的所有属性及所有父属性
   * @param clazz 类
   * @param names 已经存在的属性
   * @return 类的属性及所有父属性
   */
  private static List<Field> getFields(Class<?> clazz, Set<String> names) {
    List<Field> fieldList = new ArrayList<Field>();
    if (clazz == null) {
      return fieldList;
    }
    if (names == null) {
      names = new HashSet<String>();
    }
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      if (names.contains(field.getName())) {
        continue;
      }
      if (field.getName().startsWith("this$")) {
        continue;
      }
      fieldList.add(field);
      names.add(field.getName());
    }
    fieldList.addAll(getFields(clazz.getSuperclass(), names));
    return fieldList;
  }

  /**
   * 获取属性值
   * @param name 属性名
   * @param object 数据
   * @return 值
   */
  public static Object getFieldValue(String name, Object object) {
    try {
      return getMethod(name, object.getClass(), Type.GETTER).invoke(object);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e.getTargetException());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取属性值
   * @param field 属性
   * @param object 数据
   * @return 值
   */
  public static Object getFieldValue(Field field, Object object) {
    try {
      return getMethod(field, object.getClass(), Type.GETTER).invoke(object);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e.getTargetException());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 设置值
   * @param name 属性名
   * @param object 数据
   * @param value 值
   */
  public static void setFieldValue(String name, Object object, Object value) {
    try {
      getMethod(name, object.getClass(), Type.SETTER).invoke(object, new Object[] { value });
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e.getTargetException());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 设置值
   * @param field 属性
   * @param object 数据
   * @param value 值
   */
  public static void setFieldValue(Field field, Object object, Object value) {
    try {
      getMethod(field, object.getClass(), Type.SETTER).invoke(object, new Object[] { value });
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e.getTargetException());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取方法
   * @param name 属性名称
   * @param clazz 类
   * @param type 方法类型 getter or setter
   * @return 方法
   */
  public static Method getMethod(String name, Class<?> clazz, Type type) {
    return getMethod(getField(name, clazz), clazz, type);
  }

  /**
   * 获取方法
   * @param field 属性
   * @param clazz 类
   * @param type 方法类型 getter or setter
   * @return 方法
   */
  public static Method getMethod(Field field, Class<?> clazz, Type type) {
    try {
      String name = field.getName();
      String upper = name.substring(0, 1).toUpperCase();
      if (name.length() > 1) {
        upper += name.substring(1);
      }
      String methodName = null;
      switch (type) {
      case GETTER:
        if (boolean.class.isAssignableFrom(field.getType())) {
          methodName = "is" + upper;
        } else {
          methodName = "get" + upper;
        }
        return clazz.getMethod(methodName);
      case SETTER:
        methodName = "set" + upper;
        return clazz.getMethod(methodName, new Class[] { field.getType() });
      default:
        throw new RuntimeException();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    // end
  }

  /**
   * 获取属性
   * @param name 属性名称
   * @param clazz 类
   * @return 属性
   */
  public static Field getField(String name, Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    // from this  
    try {
      Field field = clazz.getDeclaredField(name);
      return field != null ? field : getField(name, clazz.getSuperclass());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private enum Type {
    GETTER, SETTER;
  }

}
