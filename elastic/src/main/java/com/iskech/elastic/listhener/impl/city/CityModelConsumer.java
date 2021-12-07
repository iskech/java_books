package com.iskech.elastic.listhener.impl.city;

import com.iskech.elastic.api.model.City;
import com.iskech.elastic.contant.KafkaTopicContant;
import com.iskech.elastic.listhener.base.CanalBinlogBaseConsumer;
import com.iskech.elastic.service.ICityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class CityModelConsumer extends CanalBinlogBaseConsumer<City> {
  @Resource private ICityService cityService;

  @KafkaListener(
      topics = {KafkaTopicContant.EXAMPLE_TOPIC},
      groupId = "canal")
  @Override
  public void onMessage(String message) {
    super.onMessage(message);
  }

  @Override
  public void insert(List<City> data) {
    if (CollectionUtils.isEmpty(data)) {
      return;
    }
    for (City datum : data) {
      cityService.save(datum);
    }
  }

  @Override
  public void update(List<City> data, List<City> old, List<Set> fields) {

    System.out.println(data);
  }

  @Override
  public void delete(List<City> data) {}
}
