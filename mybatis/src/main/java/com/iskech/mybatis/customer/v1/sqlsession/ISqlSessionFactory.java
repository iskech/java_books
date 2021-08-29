package com.iskech.mybatis.customer.v1.sqlsession;

import com.iskech.mybatis.customer.v1.base.IConfiguration;

/**
 * sqlSession工厂
 */
public class ISqlSessionFactory {
    private IConfiguration iConfiguration;

    public ISqlSessionFactory(IConfiguration iConfiguration) {
        this.iConfiguration=iConfiguration;
    }

    public ISqlSession openSession() {
        return new ISqlSession(iConfiguration);
    }
}
