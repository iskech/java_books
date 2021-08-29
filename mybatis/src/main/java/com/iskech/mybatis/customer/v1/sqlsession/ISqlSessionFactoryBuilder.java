package com.iskech.mybatis.customer.v1.sqlsession;

import com.iskech.mybatis.customer.v1.base.IConfiguration;
import com.iskech.mybatis.customer.v1.builder.IXMLConfigBuilder;
import com.iskech.mybatis.customer.v1.parsing.IXPathParser;

import java.io.InputStream;

/**
 * sqlSessionFactory 构建器
 */
public class ISqlSessionFactoryBuilder {
    private IConfiguration iConfiguration;

    /**
     * 构建SqlSessionFactory 工厂
     *
     * @param resourceAsStream 配置流
     * @return
     */
    public ISqlSessionFactory build(InputStream resourceAsStream) {
        //初始化配置即为mybatis.xml
        IConfiguration iConfiguration = parseConfiguration(resourceAsStream);
        return new ISqlSessionFactory(iConfiguration);
    }

    /**
     * 解析配置流封装configuration
     *
     * @param resourceAsStream
     * @return
     */
    private IConfiguration parseConfiguration(InputStream resourceAsStream) {
        IXPathParser ixPathParser = new IXPathParser(resourceAsStream);
        return new IXMLConfigBuilder(ixPathParser).parse();
    }
}
