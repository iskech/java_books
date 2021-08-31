package com.iskech.mybatis.customer.v1.executor;

import com.iskech.mybatis.customer.v1.handler.ResultHandler;
import com.iskech.mybatis.customer.v1.mapping.IMappedStatement;
import com.iskech.mybatis.customer.v1.pool.IDataSource;

import java.sql.*;

public class IExecutor {
    private IDataSource iDataSource;

    public IExecutor(IDataSource iDataSource) {
        this.iDataSource = iDataSource;
    }

    public <T> T query(IMappedStatement iMappedStatement, Object[] args) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = iDataSource.getConnection();
            preparedStatement = connection.prepareStatement(iMappedStatement.getIbounding().getSql());
            //设置预编译语句参数
            preparedStatement.setString(1, String.valueOf(args[0]));
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            // todo 解析 返回结果集
         return (T) new ResultHandler(iMappedStatement).handleResultSets(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
}
