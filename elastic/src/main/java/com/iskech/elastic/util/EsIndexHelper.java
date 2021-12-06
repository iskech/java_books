package com.iskech.elastic.util;

import com.alibaba.fastjson.JSONObject;
import com.iskech.elastic.api.model.DocBean;
import org.apache.commons.codec.binary.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EsIndexHelper {

  public static void main(String[] args) {
    generator7(DocBean.class, "docBean");
  }

  /**
   * 生成类对应 es索引json
   *
   * <p>{ "properties":{ "indexName":{ "properties"{
   *
   * <p>} } }
   *
   * <p>}
   *
   * @param clazz
   */
  public static void generator7(Class<?> clazz, String index) {
    Field[] fields = clazz.getDeclaredFields();
    Map<String, Object> map = new HashMap<>();
    Map<String, Object> settings = new HashMap<>();
    settings.put("number_of_shards", 5);
    settings.put("number_of_replicas", 1);
    map.put("settings", settings);
    Map<String, Map<String, Object>> outProperties = new HashMap<>();
    Map<String, Object> mappings = new HashMap<>();
    Map<String, Object> indexMap = new HashMap<>();
    Map<String, Object> properties = new LinkedHashMap<>();
    for (Field field : fields) {
      Map<String, Object> property = new HashMap<>();
      if (StringUtils.equals("java.sql.Timestamp", field.getGenericType().getTypeName())) {
        property.put("type", "date");
        property.put("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");
      } else if (StringUtils.equals("java.lang.Integer", field.getGenericType().getTypeName())) {
        property.put("type", "integer");
      } else if (StringUtils.equals("java.lang.String", field.getGenericType().getTypeName())) {
        property.put("type", "keyword");
      } else if (StringUtils.equals("java.lang.Long", field.getGenericType().getTypeName())) {
        property.put("type", "long");
      } else if (StringUtils.equals(
          BigDecimal.class.getName(), field.getGenericType().getTypeName())) {
        property.put("type", "double");
      } else if (StringUtils.equals(Date.class.getName(), field.getGenericType().getTypeName())) {
        property.put("type", "date");
        property.put("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");
      }
      properties.put(field.getName(), property);
    }

    indexMap.put("properties", properties);
    outProperties.put(index, indexMap);
    mappings.put("properties", outProperties);
    map.put("mappings", mappings);

    System.err.println(JSONObject.toJSONString(map, true));
  }

  /**
   * 生成类对应 es索引json
   *
   * @param clazz
   */
  public static void generator6(Class<?> clazz, String index) {
    Field[] fields = clazz.getDeclaredFields();
    Map<String, Object> map = new HashMap<>();
    Map<String, Object> settings = new HashMap<>();
    settings.put("number_of_shards", 5);
    settings.put("number_of_replicas", 1);
    map.put("settings", settings);

    Map<String, Object> mappings = new HashMap<>();
    Map<String, Object> indexMap = new HashMap<>();
    Map<String, Object> properties = new LinkedHashMap<>();
    for (Field field : fields) {
      Map<String, Object> property = new HashMap<>();
      if (StringUtils.equals("java.sql.Timestamp", field.getGenericType().getTypeName())) {
        property.put("type", "date");
        property.put("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");
      } else if (StringUtils.equals("java.lang.Integer", field.getGenericType().getTypeName())) {
        property.put("type", "integer");
      } else if (StringUtils.equals("java.lang.String", field.getGenericType().getTypeName())) {
        property.put("type", "keyword");
      } else if (StringUtils.equals("java.lang.Long", field.getGenericType().getTypeName())) {
        property.put("type", "long");
      } else if (StringUtils.equals(
          BigDecimal.class.getName(), field.getGenericType().getTypeName())) {
        property.put("type", "double");
      } else if (StringUtils.equals(Date.class.getName(), field.getGenericType().getTypeName())) {
        property.put("type", "date");
        property.put("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");
      }
      properties.put(field.getName(), property);
    }

    indexMap.put("properties", properties);
    mappings.put(index, indexMap);
    map.put("mappings", mappings);

    System.err.println(JSONObject.toJSONString(map, true));
  }

  /**
   * 驼峰转下划线
   *
   * @param param
   * @return
   */
  public String camelToUnderline(String param) {
    char UNDERLINE = '_';
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    int len = param.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (Character.isUpperCase(c)) {
        sb.append(UNDERLINE);
        sb.append(Character.toLowerCase(c));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }
}
