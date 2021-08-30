package com.iskech.mybatis.customer.v1.test;


import com.iskech.mybatis.customer.v1.api.model.OrderLog;
import com.iskech.mybatis.customer.v1.provider.mapper.OrderLogMapper;
import com.iskech.mybatis.customer.v1.sqlsession.ISqlSession;
import com.iskech.mybatis.customer.v1.sqlsession.ISqlSessionFactory;
import com.iskech.mybatis.customer.v1.sqlsession.ISqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisApiTest {

    public static void main(String[] args) throws IOException {
        //读取配置文件生产文件流对象
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        //使用构建器生产 SqlSessionFactory工厂初始化配置 configuration
        ISqlSessionFactory sqlSessionFactory = new ISqlSessionFactoryBuilder().build(resourceAsStream);

        //通过SqlSessionFactory工厂获取 sqlSession实例
        ISqlSession sqlSession = sqlSessionFactory.openSession();

        //sql会话获取代理mapper 代理mapper对象注册在 knownMappers map中
        OrderLogMapper cityMapperProxy = sqlSession.getMapper(OrderLogMapper.class);
        //执行sql
        List<OrderLog> cities = cityMapperProxy.listByCargoOrderCode("1");

        System.out.println(cities);


    }
}
