package com.iskech.mybatis.customer.v1.base;

import com.iskech.mybatis.customer.v1.pool.IDataSource;
import lombok.Data;

import javax.sql.DataSource;

/**
 * 环境类
 */
@Data
public class IEnviremation {
    /**
     * 连接信息
     */
    private DataSource dateSource;
}
