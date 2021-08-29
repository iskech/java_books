package com.iskech.mybatis.customer.v1.pool;

import lombok.Data;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Data
public class IDataSource extends IDataSourceInterface {
    private String driver;
    private String url;
    private String username;
    private String password;

    @SneakyThrows
    @Override
    public Connection getConnection() throws SQLException {
        //1.加载驱动程序
        Class.forName(driver);
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }


}
