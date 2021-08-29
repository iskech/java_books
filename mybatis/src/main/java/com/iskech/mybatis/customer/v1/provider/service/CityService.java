package com.iskech.mybatis.customer.v1.provider.service;

import com.iskech.mybatis.customer.v1.api.model.City;
import com.iskech.mybatis.customer.v1.provider.mapper.CityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CityService {
    @Resource
    private CityMapper cityMapper;
    public List<City> list(String name) {
        return cityMapper.listByName(name);
    }
}
