package com.iskech.mybatis.provider.controller;

import com.iskech.mybatis.api.model.City;
import com.iskech.mybatis.provider.service.CityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
    @Resource
    private CityService cityService;
    @PostMapping("/list")
    public List<City> list(@RequestBody City city){
        return cityService.list(city.getName());
    }

}
