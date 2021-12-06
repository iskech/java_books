package com.iskech.elastic.util;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.iskech.elastic.api.model.binlog.Binlog;

import java.lang.reflect.Field;
import java.util.*;

public class BinlogHelper {

  public static List<Binlog> coverCanalToBinlog(CanalEntry.RowChange rowChange, Class clazz) {
    ArrayList arrayList = new ArrayList();
    List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
    for (CanalEntry.RowData rowData : rowDatasList) {
      Binlog binlog = new Binlog();
      binlog.setDatabase(rowChange.getDdlSchemaName());
      binlog.setTable(rowChange.getDdlSchemaName());
      binlog.setWhetherDdl(rowChange.getIsDdl());
      CanalEntry.EventType eventType = rowChange.getEventType();
      switch (eventType) {
        case INSERT:
          binlog.setType(Binlog.BinlogEnum.INSERT);
          break;
        case UPDATE:
          binlog.setType(Binlog.BinlogEnum.UPDATE);
          break;
        case DELETE:
          binlog.setType(Binlog.BinlogEnum.DELETE);
          break;
        default:
          break;
      }
      List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
      ArrayList<Object> beforeList = new ArrayList<>();
      ArrayList<Object> afterList = new ArrayList<>();
      binlog.setData(afterList);
      binlog.setOld(beforeList);
      coverColumnToBean(beforeColumnsList, clazz, beforeList);
      List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
      coverColumnToBean(afterColumnsList, clazz, afterList);
      arrayList.add(binlog);
    }

    return arrayList;
  }

  private static void coverColumnToBean(
      List<CanalEntry.Column> columnList, Class clazz, ArrayList<Object> beforeList) {
    HashMap<String, Object> param = new HashMap<>();
    Field[] declaredFields = clazz.getDeclaredFields();
    for (Field declaredField : declaredFields) {
      String fieldName = declaredField.getName();
      Optional<CanalEntry.Column> first =
          columnList.stream().filter(item -> Objects.equals(item.getName(), fieldName)).findFirst();
      if (first.isPresent()) {
        CanalEntry.Column column = first.get();
        String value = column.getValue();
        param.put(fieldName, value);
      }
    }
    try {
      Object obj = ReflectUtil.setFieldValue(clazz, param);
      beforeList.add(obj);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 根据属性名设置属性值
   *
   * @param fieldName
   * @param object
   * @return
   */
  private static void setFieldValueByFieldName(String fieldName, Object object, String value) {
    try {
      // 获取obj类的字节文件对象
      Class c = object.getClass();
      // 获取该类的成员变量
      Field f = c.getDeclaredField(fieldName);
      // 取消语言访问检查
      f.setAccessible(true);
      Class<?> type = f.getType();

      // 给变量赋值
      f.set(object, value);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
