package com.iskech.mybatis.customer.v1.test;


import com.iskech.mybatis.customer.v1.api.model.City;
import com.iskech.mybatis.customer.v1.provider.mapper.CityMapper;
import com.iskech.mybatis.customer.v1.sqlsession.ISqlSession;
import com.iskech.mybatis.customer.v1.sqlsession.ISqlSessionFactory;
import com.iskech.mybatis.customer.v1.sqlsession.ISqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MybatisApiTest {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        //读取配置文件生产文件流对象
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        //使用构建器生产 SqlSessionFactory工厂初始化配置 configuration
        ISqlSessionFactory sqlSessionFactory = new ISqlSessionFactoryBuilder().build(resourceAsStream);
        //通过SqlSessionFactory工厂获取 sqlSession实例
        ISqlSession sqlSession = sqlSessionFactory.openSession();
        //sql会话获取代理mapper 代理mapper对象注册在 knownMappers map中
        CityMapper cityMapperProxy = sqlSession.getMapper(CityMapper.class);
        //执行sql

        //创建使用固定线程数的线程池
        ExecutorService es2 = Executors.newFixedThreadPool(6);
        for (int index = 0; index < 1500; index++) {
            es2.submit(new Runnable() {
                @Override
                public void run() {
                    List<City> cities = cityMapperProxy.listByName("Almere");
                    System.out.println(cities);
                }
            });

        }
        System.err.println("耗时:" + (System.currentTimeMillis() - startTime));

    }
}
