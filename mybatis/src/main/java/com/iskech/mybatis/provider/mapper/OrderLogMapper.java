
package com.iskech.mybatis.provider.mapper;


import com.iskech.mybatis.api.model.OrderLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* ---------------------------------------------------------------------------
* 类名称   ：OrderLogMapper.java

* ---------------------------------------------------------------------------
*/

@Mapper
public interface OrderLogMapper {

    List<OrderLog> listByCargoOrderCode(@Param("cargoOrderCode") String cargoOrderCode);
}