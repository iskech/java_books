package com.iskech.mybatis.customer.v1.mapping;

import lombok.Data;
import org.apache.ibatis.mapping.ResultMap;

import java.util.List;
import java.util.Map;

/**
 * 映射mapper xml 信息
 */
@Data
public class IMappedStatement {
    /**
     * 每一个xml sql 配置映射唯一id namespace.id
     */
    private String id;

    /**
     * sql
     */
    private IBounding ibounding;

    /**
     * parameterType 入参类型
     */
    private String parameterType;

    /**
     * resultType 返回结果类型
     */
    private String resultType;
    /**
     * resultMap属性 返回结果映射类型
     */
    private List<IResultMap> resultMaps;
    /**
     * resultMap标签 返回结果集映射
     */
    private Map<String,IResultMapping> resultMappings;

}
