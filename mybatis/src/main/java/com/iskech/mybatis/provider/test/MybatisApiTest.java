package com.iskech.mybatis.provider.test;

import com.iskech.mybatis.api.model.City;
import com.iskech.mybatis.provider.mapper.CityMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class MybatisApiTest {

    public static void main(String[] args) throws IOException {
        //读取配置文件生产文件流对象
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        //使用构建器生产 SqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //通过SqlSessionFactory工厂获取 sqlSession实例
        SqlSession sqlSession = sqlSessionFactory.openSession();
        HashMap<String, String> params = new HashMap<>();
        params.put("name","Herat");
        //statement= namespace.id 即为命名空间+sql id
        //执行sql
        List<City> cities = sqlSession.selectList("com.iskech.mybatis.provider.mapper.CityMapper.listByName", params);

    }
}
