
package com.iskech.mybatis.customer.v1.provider.mapper;




import com.iskech.mybatis.customer.v1.api.model.OrderLog;

import java.util.List;


/**
* ---------------------------------------------------------------------------
* 类名称   ：OrderLogMapper.java

* ---------------------------------------------------------------------------
*/

public interface OrderLogMapper {

    List<OrderLog> listByCargoOrderCode(String cargoOrderCode);
}