package com.iskech.mybatis.customer.v1.base;

import com.iskech.mybatis.customer.v1.mapping.IMappedStatement;
import lombok.Data;

import java.util.Map;

/**
 * 配置
 */
@Data
public class IConfiguration {
    //环境信息

    private IEnviremation iEnviremation;

    //mapper xml 映射信息

    private Map<String,IMappedStatement> mappedStatementMap;

}
