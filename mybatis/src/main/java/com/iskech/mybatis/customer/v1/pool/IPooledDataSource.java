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


    protected final Queue<IPooledConnection> idleConnections = new LinkedList<IPooledConnection>();
    protected final Queue<IPooledConnection> activeConnections = new LinkedList<IPooledConnection>();
    private String driver;
    private String url;
    private String username;
    private String password;
    // OPTIONAL CONFIGURATION FIELDS
    protected int poolMaximumActiveConnections = 10;
    protected int poolMaximumIdleConnections = 5;

    protected int poolTimeToWait = 20000;


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
            IPooledConnection iPooledConnection = new IPooledConnection(conn, this);
            idleConnections.add(iPooledConnection);
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        IPooledConnection connection = null;
        if (idleConnections.isEmpty()) {
            //空闲不存在 创建一个新的连接 添加至激活尾部并返回
            if (activeConnections.size() < poolMaximumActiveConnections) {
                Connection conn = DriverManager.getConnection(url, username, password);
                connection = new IPooledConnection(conn, this);
            } else {
                //尝试移除 链接超时链接 移除后创建新链接
                IPooledConnection iPooledConnection = ((List<IPooledConnection>) activeConnections).get(0);
                if((System.currentTimeMillis() - iPooledConnection.getLastUsedTimestamp() -poolMaximumActiveConnections)>0){
                    activeConnections.remove(iPooledConnection);
                    Connection conn = DriverManager.getConnection(url, username, password);
                    connection = new IPooledConnection(conn, this);

                }
                //没有可移除超时链接 只能线程等待
            }
        } else {
            IPooledConnection poll = idleConnections.poll();
            activeConnections.add(poll);
            connection = poll;
        }
        activeConnections.add(connection);
        return connection.getRealConnection();
    }

    public void backConnection(IPooledConnection connection) {
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
