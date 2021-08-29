package com.iskech.mybatis.customer.v1.pool;

import lombok.Data;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Data
public class IDataSource extends IDataSourceInterface {
    private String driver;
    private String url;
    private String username;
    private String password;

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }


}
