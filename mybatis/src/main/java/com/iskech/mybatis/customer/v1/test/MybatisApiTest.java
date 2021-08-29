package com.iskech.mybatis.customer.v1.test;


import com.iskech.mybatis.customer.v1.sqlsession.ISqlSessionFactory;
import com.iskech.mybatis.customer.v1.sqlsession.ISqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisApiTest {

    public static void main(String[] args) throws IOException {
        //读取配置文件生产文件流对象
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        //使用构建器生产 SqlSessionFactory工厂初始化配置 configuration
        ISqlSessionFactory sqlSessionFactory = new ISqlSessionFactoryBuilder().build(resourceAsStream);

        //通过SqlSessionFactory工厂获取 sqlSession实例
       // SqlSession sqlSession = sqlSessionFactory.openSession();
        /*/


        HashMap<String, String> params = new HashMap<>();
        params.put("name","Herat");
        //statement= namespace.id 即为命名空间+sql id
        //执行sql
        List<City> cities = sqlSession.selectList("com.iskech.mybatis.provider.mapper.CityMapper.listByName", params);*/

    }
}
