package com.iskech.elastic.listhener.impl.city;


import com.iskech.elastic.api.model.City;
import com.iskech.elastic.listhener.base.CanalBinlogBaseConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class CityModelConsumer extends CanalBinlogBaseConsumer<City> {

    @Override
    public void onMessage(String message){
        super.onMessage(message);
    }


    @Override
    public void insert(List<City> data) {

    }

    @Override
    public void update(List<City> data, List<City> old, List<Set> fields) {

    }

    @Override
    public void delete(List<City> data) {

    }
}
