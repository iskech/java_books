package com.iskech.elastic.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "mybatis-city_v1", shards = 1, replicas = 0)
public class City {

  @Id private Long id;

  @Field(name = "city.name", type = FieldType.Keyword)
  private String name;

  @Field(name = "city.district", type = FieldType.Keyword)
  private String district;

  @Field(name = "city.countryCode", type = FieldType.Text)
  private String countryCode;

  @Field(name = "city.population", type = FieldType.Long)
  private Long population;
}
