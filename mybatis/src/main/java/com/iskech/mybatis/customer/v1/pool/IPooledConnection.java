/**
 *    Copyright 2009-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.iskech.mybatis.customer.v1.pool;

import lombok.Data;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.reflection.ExceptionUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
@Data
class IPooledConnection  {

  private static final String CLOSE = "close";
  private static final Class<?>[] IFACES = new Class<?>[] { Connection.class };


  private final IPooledDataSource dataSource;
  private final Connection realConnection;
  private long checkoutTimestamp;
  private long createdTimestamp;
  private long lastUsedTimestamp;


  /*
   * Constructor for SimplePooledConnection that uses the Connection and PooledDataSource passed in
   *
   * @param connection - the connection that is to be presented as a pooled connection
   * @param dataSource - the dataSource that the connection is from
   */
  public IPooledConnection(Connection connection, IPooledDataSource dataSource) {

    this.realConnection = connection;
    this.dataSource = dataSource;
    this.createdTimestamp = System.currentTimeMillis();
    this.lastUsedTimestamp = System.currentTimeMillis();

  }

  public Connection getRealConnection() {
    return realConnection;
  }
}
