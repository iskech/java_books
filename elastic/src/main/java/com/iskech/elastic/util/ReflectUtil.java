package com.iskech.elastic.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.Map;
import java.util.Set;

/** 反射加载类 */
public class ReflectUtil {

  /**
   * 获取类型
   *
   * @param clazz
   * @param index
   * @return
   * @throws Exception
   */
  public static Class<?> getClassType(final Class<?> clazz) throws Exception {
    Type genericType = clazz.getGenericSuperclass();
    if (!(genericType instanceof ParameterizedType)) {
      return Object.class;
    }
    Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();

    if (!(params[0] instanceof Class)) {
      return Object.class;
    }
    return (Class<?>) params[0];
  }

  /**
   * 设置属性值
   *
   * @param clazz 目标类
   * @param params 参数值（注：params中key值必须为目标类中的属性名，value为属性值。此方法目前仅支持单类属性值设置）
   * @return
   * @throws Exception
   */
  public static Object setFieldValue(final Class<?> clazz, Map<String, Object> params)
      throws Exception {
    Object obj = null;
    if (clazz == null) {
      throw new Exception("The Operation Error! Cause By: clazz is null");
    }
    if (params != null) {
      Class<?> objClass = getClassType(clazz);
      if (objClass == null) {
        throw new Exception("Error getting object instance! Cause By: objClass is null");
      }
      obj = clazz.newInstance();
      if (obj != null) {
        Set<String> keys = params.keySet();
        for (String key : keys) {
          String value = (String) params.get(key);
          if (value == null) {
            continue;
          }
          Field field = clazz.getDeclaredField(key);
          if (field != null) {
            field.setAccessible(true);
            String fieldType = field.getType().getTypeName();
            if ("java.lang.Integer".equals(fieldType)) {
              field.set(obj, Integer.valueOf(value));
            } else if ("java.lang.Long".equals(fieldType)) {
              field.set(obj, Long.valueOf(value));
            } else if ("java.lang.Double".equals(fieldType)) {
              field.set(obj, Double.valueOf(value));
            } else if ("java.util.Date".equals(fieldType)) {
              field.set(obj, Date.parse(value));
            } else {
              field.set(obj, value);
            }
          }
        }
      }
    }
    return obj;
  }
}
