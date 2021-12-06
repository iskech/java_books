package com.iskech.elastic.api.model.binlog;

import lombok.Data;

import java.util.List;

/**
 * binlog数据封装
 *
 * @param <T> 具体model
 */
@Data
public class Binlog<T> {
  /** 数据库 */
  private String database;
  /** 表名 */
  private String table;
  /** 是否 ddl */
  private Boolean whetherDdl;
  /** 操作类型 */
  BinlogEnum type;
  /** 新数据 */
  private List<T> data;
  /** 修改前数据 */
  private List<T> old;

  /** binlog 操作类型 */
  public enum BinlogEnum {
    INSERT,
    DELETE,
    UPDATE
  }
}
