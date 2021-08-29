package com.iskech.mybatis.provider.test;

import com.iskech.mybatis.provider.mapper.CityMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcTest {
    public static final String URL = "jdbc:mysql://localhost:3306/world";
    public static final String USER = "iskech";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) throws Exception {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        String sql = "select * from city where name like '%Herat%'";
        //执行预编译语句
        ResultSet rs = stmt.executeQuery(sql);
        //如果有数据，rs.next()返回true
        while(rs.next()){
            //解析返回结果集
            System.out.println(rs.getString("name"));
        }
    }
}
