/**
 * Copyright 2009-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iskech.mybatis.customer.v1.pool;


import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;


/**
 * This is a simple, synchronous, thread-safe database connection pool.
 *
 * @author Clinton Begin
 */
public class IPooledDataSource implements DataSource {


    protected final Queue<Connection> idleConnections = new LinkedList<Connection>();
    protected final Queue<Connection> activeConnections = new LinkedList<Connection>();
    private String driver;
    private String url;
    private String username;
    private String password;
    // OPTIONAL CONFIGURATION FIELDS
    protected int poolMaximumActiveConnections = 10;
    protected int poolMaximumIdleConnections = 5;


    public IPooledDataSource(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        //初始化连接池
        //1.加载驱动程序
        Class.forName(driver);
        for (int index = 0; index < poolMaximumIdleConnections; index++) {
            //2. 获得数据库连接
            Connection conn = DriverManager.getConnection(url, username, password);
            idleConnections.add(conn);
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        if (idleConnections.isEmpty()) {
            //空闲不存在 创建一个新的连接 添加至激活尾部并返回
            Connection conn = DriverManager.getConnection(url, username, password);
            activeConnections.add(conn);
            return conn;
        } else {
            //空闲存在弹出第一个 并添加至激活尾部 后返回
            Connection poll = idleConnections.poll();
            activeConnections.add(poll);
            return poll;
        }
    }

    public void backConnection(Connection connection) {
        idleConnections.add(connection);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
