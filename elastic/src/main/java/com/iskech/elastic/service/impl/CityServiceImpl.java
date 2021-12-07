package com.iskech.elastic.service.impl;

import com.iskech.elastic.api.model.City;
import com.iskech.elastic.mapper.CityRepository;
import com.iskech.elastic.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Service
public class CityServiceImpl implements ICityService {
  @Resource
  private CityRepository cityRepository;

  @Override
  public void createIndex() {}

  @Override
  public void deleteIndex(String index) {}

  @Override
  public void save(City city) {
    cityRepository.save(city);
  }

  @Override
  public void update(City city) {
    cityRepository.save(city);
  }

  @Override
  public void saveAll(List<City> list) {}

  @Override
  public Iterator<City> findAll() {
    return null;
  }

  @Override
  public Page<City> findByContent(String content) {
    return null;
  }

  @Override
  public Page<City> findByFirstCode(String firstCode) {
    return null;
  }

  @Override
  public Page<City> findBySecordCode(String secordCode) {
    return null;
  }

  @Override
  public Page<City> query(String key) {
    return null;
  }
}
