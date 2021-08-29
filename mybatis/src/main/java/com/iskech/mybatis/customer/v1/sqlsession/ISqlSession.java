package com.iskech.mybatis.customer.v1.sqlsession;

import com.iskech.mybatis.customer.v1.base.IConfiguration;
import com.iskech.mybatis.customer.v1.executor.IExecutor;
import com.iskech.mybatis.customer.v1.mapping.IMappedStatement;
import com.iskech.mybatis.customer.v1.proxy.IMapperProxy;

import java.lang.reflect.Proxy;

/**
 * sql会话
 */
public class ISqlSession {
    private IExecutor iExecutor;
    private IConfiguration iConfiguration;

    public ISqlSession(IConfiguration iConfiguration) {
        this.iConfiguration = iConfiguration;
        this.iExecutor = new IExecutor(iConfiguration.getIEnviremation().getDateSource());
    }

    /**
     * 获取mapper代理对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader()
                , new Class<?>[]{clazz}
                , new IMapperProxy(this));
    }

    public <T> T selectList(String key, Object[] args) {
        //获取mappedStatement mapper映射对象
        IMappedStatement iMappedStatement = iConfiguration.getMappedStatementMap().get(key);
        return iExecutor.query(iMappedStatement, args);
    }
}
