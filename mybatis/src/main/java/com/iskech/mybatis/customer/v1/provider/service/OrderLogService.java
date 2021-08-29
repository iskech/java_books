package com.iskech.mybatis.customer.v1.provider.service;

import com.iskech.mybatis.customer.v1.api.model.OrderLog;
import com.iskech.mybatis.customer.v1.provider.mapper.OrderLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderLogService {
    @Resource
    private OrderLogMapper orderLogMapper;
    public List<OrderLog> list(String cargoOrderCode) {
        return orderLogMapper.listByCargoOrderCode(cargoOrderCode);
    }
}
