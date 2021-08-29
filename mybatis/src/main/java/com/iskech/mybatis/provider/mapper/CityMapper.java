
package com.iskech.mybatis.provider.mapper;


import com.iskech.mybatis.api.model.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* ---------------------------------------------------------------------------
* 类名称   ：OrderLogMapper.java

* ---------------------------------------------------------------------------
*/

@Mapper
public interface CityMapper {

    List<City> listByName(@Param("name") String name);
}