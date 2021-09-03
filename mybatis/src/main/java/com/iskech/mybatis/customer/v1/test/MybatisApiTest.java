package com.iskech.mybatis.customer.v1.test;


import com.iskech.mybatis.customer.v1.api.model.City;
import com.iskech.mybatis.customer.v1.provider.mapper.CityMapper;
import com.iskech.mybatis.customer.v1.sqlsession.ISqlSession;
import com.iskech.mybatis.customer.v1.sqlsession.ISqlSessionFactory;
import com.iskech.mybatis.customer.v1.sqlsession.ISqlSessionFactoryBuilder;
import com.iskech.mybatis.customer.v1.util.ThreadUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MybatisApiTest {

    public static void main(String[] args) throws IOException, InterruptedException {


        //读取配置文件生产文件流对象
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        //使用构建器生产 SqlSessionFactory工厂初始化配置 configuration
        ISqlSessionFactory sqlSessionFactory = new ISqlSessionFactoryBuilder().build(resourceAsStream);
        //通过SqlSessionFactory工厂获取 sqlSession实例
        ISqlSession sqlSession = sqlSessionFactory.openSession();
        //sql会话获取代理mapper 代理mapper对象注册在 knownMappers map中
        CityMapper cityMapperProxy = sqlSession.getMapper(CityMapper.class);
        //执行sql

        CountDownLatch countDownLatch = new CountDownLatch(15000);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //创建使用固定线程数的线程池
        ThreadPoolExecutor executor = ThreadUtils.getExecutor();
        long startTime = System.currentTimeMillis();
        for (int index = 0; index < 15001; index++) {
            List<City> cities = cityMapperProxy.listByName("Almere");
            countDownLatch.countDown();
            System.out.println(countDownLatch.getCount());
           /* executor.execute(new Runnable() {
                @Override
                public void run() {

                }
            });*/
        }
        countDownLatch.await();
        System.err.println("耗时:" + (System.currentTimeMillis() - startTime));
        executor.shutdown();


    }
}
