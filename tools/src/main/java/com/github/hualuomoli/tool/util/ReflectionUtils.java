package com.github.hualuomoli.tool.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类.
 */
@SuppressWarnings("rawtypes")
public class ReflectionUtils {

  private static final String SETTER_PREFIX = "set";

  private static final String GETTER_PREFIX = "get";

  private static final String CGLIB_CLASS_SEPARATOR = "$$";

  private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

  /**
   * 调用Getter方法.
   * 支持多级，如：对象名.对象名.方法
   * @param obj 实体类
   * @param propertyName 属性名称(嵌套属性使用逗号分隔user.username)
   * @return 属性值
   */
  public static Object invokeGetter(Object obj, String propertyName) {
    Object object = obj;
    for (String name : StringUtils.split(propertyName, ".")) {
      String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
      object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
    }
    return object;
  }

  /**
   * 调用Setter方法, 仅匹配方法名。
   * 支持多级，如：对象名.对象名.方法
   * @param obj 实体类
   * @param propertyName 属性名称(嵌套属性使用逗号分隔user.username)
   * @param value 属性值
   */
  public static void invokeSetter(Object obj, String propertyName, Object value) {
    Object object = obj;
    String[] names = StringUtils.split(propertyName, ".");
    for (int i = 0; i < names.length; i++) {
      if (i < names.length - 1) {
        String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
        object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
      } else {
        String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
        invokeMethodByName(object, setterMethodName, new Object[] { value });
      }
    }
  }

  /**
   * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
   * @param obj 实体类
   * @param fieldName 属性名称(嵌套属性使用逗号分隔user.username)
   * @return 属性值
   */
  public static Object getFieldValue(final Object obj, final String fieldName) {
    Field field = getAccessibleField(obj, fieldName);

    if (field == null) {
      throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
    }

    Object result = null;
    try {
      result = field.get(obj);
    } catch (IllegalAccessException e) {
      logger.error("不可能抛出的异常{}", e.getMessage());
    }
    return result;
  }

  /**
   * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
   * @param obj 实体类
   * @param fieldName 属性名称(嵌套属性使用逗号分隔user.username)
   * @param value 执行值
   */
  public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
    Field field = getAccessibleField(obj, fieldName);

    if (field == null) {
      throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
    }

    try {
      field.set(obj, value);
    } catch (IllegalAccessException e) {
      logger.error("不可能抛出的异常:{}", e.getMessage());
    }
  }

  /**
   * 直接调用对象方法, 无视private/protected修饰符.
   * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用.
   * 同时匹配方法名+参数类型，
   * @param obj 实体类
   * @param methodName 方法名
   * @param parameterTypes 参数类型
   * @param args 参数值
   * @return 方法返回结果
   */
  public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
    Method method = getAccessibleMethod(obj, methodName, parameterTypes);
    if (method == null) {
      throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
    }

    try {
      return method.invoke(obj, args);
    } catch (Exception e) {
      throw convertReflectionExceptionToUnchecked(e);
    }
  }

  /**
   * 直接调用对象方法, 无视private/protected修饰符，
   * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用.
   * 只匹配函数名，如果有多个同名函数调用第一个。
   * @param obj 实体类
   * @param methodName 方法名
   * @param args 参数值
   * @return 方法返回结果
   */
  public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
    Method method = getAccessibleMethodByName(obj, methodName);
    if (method == null) {
      throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
    }

    try {
      return method.invoke(obj, args);
    } catch (Exception e) {
      throw convertReflectionExceptionToUnchecked(e);
    }
  }

  /**
   * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
   * 
   * 如向上转型到Object仍无法找到, 返回null.
   * @param obj 实体类
   * @param fieldName 属性名 (嵌套属性使用逗号分隔user.username)
   * @return 属性
   */
  public static Field getAccessibleField(final Object obj, final String fieldName) {
    Validate.notNull(obj, "object can't be null");
    Validate.notBlank(fieldName, "fieldName can't be blank");
    for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
      try {
        Field field = superClass.getDeclaredField(fieldName);
        makeAccessible(field);
        return field;
      } catch (NoSuchFieldException e) {// NOSONAR
        // Field不在当前类定义,继续向上转型
        continue;// new add
      }
    }
    return null;
  }

  /**
   * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
   * 如向上转型到Object仍无法找到, 返回null.
   * 匹配函数名+参数类型。
   * 
   * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
   * @param obj 实体类
   * @param methodName 方法名
   * @param parameterTypes 参数类型
   * @return 方法
   */
  public static Method getAccessibleMethod(final Object obj, final String methodName, final Class<?>... parameterTypes) {
    Validate.notNull(obj, "object can't be null");
    Validate.notBlank(methodName, "methodName can't be blank");

    for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
      try {
        Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
        makeAccessible(method);
        return method;
      } catch (NoSuchMethodException e) {
        // Method不在当前类定义,继续向上转型
        continue;// new add
      }
    }
    return null;
  }

  /**
   * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
   * 如向上转型到Object仍无法找到, 返回null.
   * 只匹配函数名。
   * 
   * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
   * @param obj 实体类
   * @param methodName 方法名
   * @return 方法
   */
  public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
    Validate.notNull(obj, "object can't be null");
    Validate.notBlank(methodName, "methodName can't be blank");

    for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
      Method[] methods = searchType.getDeclaredMethods();
      for (Method method : methods) {
        if (method.getName().equals(methodName)) {
          makeAccessible(method);
          return method;
        }
      }
    }
    return null;
  }

  /**
   * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
   * @param method 方法
   */
  public static void makeAccessible(Method method) {
    if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
      method.setAccessible(true);
    }
  }

  /**
   * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
   * @param field 属性
   */
  public static void makeAccessible(Field field) {
    if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers()))
        && !field.isAccessible()) {
      field.setAccessible(true);
    }
  }

  /**
   * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处
   * 如无法找到, 返回Object.class.
   * eg.
   * <span>public UserDao extends HibernateDao&lt;User&gt;</span>
   *
   * @param clazz The class to introspect
   * @param <T> 泛型
   * @return the first generic declaration, or Object.class if cannot be determined
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> getClassGenricType(final Class clazz) {
    return getClassGenricType(clazz, 0);
  }

  /**
   * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
   * 如无法找到, 返回Object.class.
   * 
   * 如<span>public UserDao extends HibernateDao&lt;User, Long$gt;</span>
   *
   * @param clazz clazz The class to introspect
   * @param index the Index of the generic ddeclaration,start from 0.
   * @return the index generic declaration, or Object.class if cannot be determined
   */
  public static Class getClassGenricType(final Class clazz, final int index) {

    Type genType = clazz.getGenericSuperclass();

    if (!(genType instanceof ParameterizedType)) {
      logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
      return Object.class;
    }

    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

    if (index >= params.length || index < 0) {
      logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
      return Object.class;
    }
    if (!(params[index] instanceof Class)) {
      logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
      return Object.class;
    }

    return (Class) params[index];
  }

  /**
   * 获取类
   * @param instance 实例
   * @return 类对象
   */
  public static Class<?> getUserClass(Object instance) {
    if (instance == null) {
      throw new RuntimeException("Instance must not be null");
    }
    Class clazz = instance.getClass();
    if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
      Class<?> superClass = clazz.getSuperclass();
      if (superClass != null && !Object.class.equals(superClass)) {
        return superClass;
      }
    }
    return clazz;

  }

  /**
   * 将反射时的checked exception转换为unchecked exception.
   * @param e 异常
   * @return 包装的运行时异常
   */
  public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
    if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
      return new IllegalArgumentException(e);
    } else if (e instanceof InvocationTargetException) {
      return new RuntimeException(((InvocationTargetException) e).getTargetException());
    } else if (e instanceof RuntimeException) {
      return (RuntimeException) e;
    }
    return new RuntimeException("Unexpected Checked Exception.", e);
  }
}
