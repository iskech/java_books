package com.iskech.mybatis.customer.v1.test;

import com.iskech.mybatis.customer.v1.provider.mapper.CityMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisMapperProxyTest {

    public static void main(String[] args) throws IOException {
        //读取配置文件生产文件流对象
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        //使用构建器生产 SqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //通过SqlSessionFactory工厂获取 sqlSession实例
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //sql会话获取代理mapper 代理mapper对象注册在 knownMappers map中
        CityMapper cityMapperProxy = sqlSession.getMapper(CityMapper.class);
        //执行代理mapper实现方法
        System.out.println(cityMapperProxy.listByName("Herat"));
    }
}
