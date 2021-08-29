package com.iskech.mybatis.provider.controller;

import com.iskech.mybatis.api.model.OrderLog;
import com.iskech.mybatis.provider.service.OrderLogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderLogController {
    @Resource
    private OrderLogService orderLogService;
    @PostMapping("/list")
    public List<OrderLog> list(@RequestBody OrderLog orderLog){
        return orderLogService.list(orderLog.getCargoOrderCode());
    }

}
