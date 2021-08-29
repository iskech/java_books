package com.iskech.mybatis.customer.v1.base;

import com.iskech.mybatis.customer.v1.pool.IDataSource;
import lombok.Data;

/**
 * 环境类
 */
@Data
public class IEnviremation {
    /**
     * 连接信息
     */
    private IDataSource dateSource;
}
