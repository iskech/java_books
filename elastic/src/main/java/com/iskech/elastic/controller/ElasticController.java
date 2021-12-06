package com.iskech.elastic.controller;

import com.iskech.elastic.api.model.DocBean;
import com.iskech.elastic.service.IElasticService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/elastic")
public class ElasticController {

    @Autowired
    private IElasticService elasticService;

    @PostMapping("/init")
    @ResponseBody
    public void init() {
        //elasticService.deleteIndex("ems");
      //  elasticService.createIndex();
        List<DocBean> list = new ArrayList<>();
        list.add(new DocBean(4L, "XX0193", "XX8064", "内容1", 1));
        list.add(new DocBean(5L, "XX0210", "XX7475", "内容2", 1));
        list.add(new DocBean(6L, "XX0257", "XX8097", "内容3", 1));

        elasticService.saveAll(list);

    }

    @PostMapping("/all")
    @ResponseBody
    public Iterator<DocBean> all() {
        return elasticService.findAll();
    }

}

