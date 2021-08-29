package com.iskech.mybatis.provider.service;

import com.iskech.mybatis.api.model.City;
import com.iskech.mybatis.api.model.OrderLog;
import com.iskech.mybatis.provider.mapper.CityMapper;
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
