
package com.iskech.mybatis.customer.v1.provider.mapper;




import com.iskech.mybatis.customer.v1.api.model.City;

import java.util.List;


/**
* ---------------------------------------------------------------------------
* 类名称   ：OrderLogMapper.java

* ---------------------------------------------------------------------------
*/

public interface CityMapper {

    List<City> listByName( String name);
}