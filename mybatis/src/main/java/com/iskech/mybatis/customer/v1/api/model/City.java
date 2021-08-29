package com.iskech.mybatis.customer.v1.api.model;

import lombok.Data;

@Data
public class City {
    private Long id;
    private String name;
    private String countryCode;
    private String district;
    private Long population;
}
