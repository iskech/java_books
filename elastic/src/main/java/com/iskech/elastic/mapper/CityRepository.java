package com.iskech.elastic.mapper;

import com.iskech.elastic.api.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CityRepository extends ElasticsearchRepository<City, Long> {

    //默认的注释
  /*  //@Query("{\"bool\" : {\"must\" : {\"field\" : {\"content\" : \"?\"}}}}")
    Page<City> findByContent(String content, Pageable pageable);

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"firstCode.keyword\" : \"?\"}}}}")
    Page<City> findByFirstCode(String firstCode, Pageable pageable);

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"secordCode.keyword\" : \"?\"}}}}")
    Page<City> findBySecordCode(String secordCode, Pageable pageable);*/


}
