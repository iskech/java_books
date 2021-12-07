package com.iskech.elastic.service;

import com.iskech.elastic.api.model.City;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

public interface ICityService {

  void createIndex();

  void deleteIndex(String index);

  void save(City city);

  void update(City city);

  void saveAll(List<City> list);

  Iterator<City> findAll();

  Page<City> findByContent(String content);

  Page<City> findByFirstCode(String firstCode);

  Page<City> findBySecordCode(String secordCode);

  Page<City> query(String key);
}
