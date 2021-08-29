package com.iskech.mybatis.customer.v1.mapping;

import lombok.Data;

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
    public IBounding ibounding;

    /**
     * parameterType 入参类型
     */
    public String parameterType;

    /**
     * resultType 返回结果类型
     */
    public String resultType;


}
