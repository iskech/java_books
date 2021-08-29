package com.iskech.mybatis.customer.v1.mapping;

import lombok.Data;

/**
 * sql信息
 */
@Data
public class IBounding {
    /**
     * sql
     */
    private String sql ;
    /**
     * sql 类型
     */
    private String sqlType;

    /**
     * 参数？
     */
}
