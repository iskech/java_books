package com.iskech.mybatis.customer.v1.handler;

import java.util.ArrayList;
import java.util.List;

public class IDefaultResultHandler  {

  private final List<Object> list;

  public IDefaultResultHandler() {
    list = new ArrayList<Object>();
  }

  public List<Object> getResultList() {
    return list;
  }

}