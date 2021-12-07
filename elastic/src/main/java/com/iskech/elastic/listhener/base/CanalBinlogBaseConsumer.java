package com.iskech.elastic.listhener.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.alibaba.otter.canal.common.utils.JsonUtils;
import com.iskech.elastic.api.model.binlog.Binlog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 监听数据库表数据变更
 *
 * @param <T>
 */
@Slf4j
public abstract class CanalBinlogBaseConsumer<T> {
  private Class<T> entityClass;

  public abstract void insert(List<T> data);

  public abstract void update(List<T> data, List<T> old, List<Set<String>> fields);

  public abstract void delete(List<T> data);

  public CanalBinlogBaseConsumer() {
    ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
    this.entityClass = (Class<T>) pt.getActualTypeArguments()[0];
  }

  /**
   * 钩子方法监听消息触发后 再调用cud方法（由子类实现）
   *
   * @param message
   */
  public void onMessage(String message) {
    log.info("kafka message:" + message);
    Binlog<T> binlog = convert(message, Binlog.class, entityClass);
    if (binlog.getType() == Binlog.BinlogEnum.INSERT) {
      insert(binlog.getData());
    } else if (binlog.getType() == Binlog.BinlogEnum.UPDATE) {
      Map<String,Object> map = JSON.parseObject(message, Map.class);
      List<Set<String>> sets = new ArrayList<>();
      if (map != null) {
        List<Map<String,Object>> results = (List<Map<String,Object>>) map.get("old");
        if (!CollectionUtils.isEmpty(results)) {
          sets = results.stream().map(o -> o.keySet()).collect(Collectors.toList());
        }
      }
      update(binlog.getData(), binlog.getOld(), sets);
    } else if (binlog.getType() == Binlog.BinlogEnum.DELETE) {
      delete(binlog.getData());
    }
  }

  protected Binlog<T> convert(String payLoad, Type... types) {
    Type type = buildType(types);
    return JSON.parseObject(payLoad, type);
  }

  private static Type buildType(Type... types) {
    Type beforeType = null;
    if (types != null && types.length > 0) {
      for (int i = types.length - 1; i > 0; i--) {
        beforeType =
            new ParameterizedTypeImpl(
                new Type[] {beforeType == null ? types[i] : beforeType}, null, types[i - 1]);
      }
      if (types.length == 1) {
        beforeType = types[0];
      }
    }
    return beforeType;
  }
}
