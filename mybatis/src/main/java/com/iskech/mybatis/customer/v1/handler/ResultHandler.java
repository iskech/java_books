package com.iskech.mybatis.customer.v1.handler;


import com.iskech.mybatis.customer.v1.mapping.IMappedStatement;
import com.iskech.mybatis.customer.v1.mapping.IResultMap;
import com.iskech.mybatis.customer.v1.mapping.IResultMapping;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultHandler {
    private final IMappedStatement mappedStatement;

    public ResultHandler(IMappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    public List<Object> handleResultSets(Statement stmt) throws SQLException {
        //最终封装返回结果集
        List resultList = new ArrayList<Object>();
        //jdbc 返回结果集
        ResultSet resultSet = stmt.getResultSet();
        //
        List<IResultMap> resultMaps = mappedStatement.getResultMaps();

        //
        IResultMap iResultMap = resultMaps.get(0);
        while (resultSet.next()) {
            //解析每一行数据
            parseResultSet(resultSet, resultList, iResultMap);
        }


        return resultList;
    }

    private void parseResultSet(ResultSet resultSet, List resultList, IResultMap iResultMap) {
        //使用反射创建 返回对象
        Object resultObj = null;
        Class<?> resultClazz = iResultMap.getType();
        try {
            resultObj = resultClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //设置处理各属性值
        for (IResultMapping resultMapping : iResultMap.getResultMappings()) {
            String property = resultMapping.getProperty();
            String column = resultMapping.getColumn();
            ITypeHandler<?> typeHandler = resultMapping.getTypeHandler();
            try {
                Object result = typeHandler.getResult(resultSet, column);
                String setName = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
                try {
                    Method declaredMethod = resultClazz.getDeclaredMethod(setName, Long.class);
                    declaredMethod.invoke(resultObj, result);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resultList.add(resultObj);
    }

    private void builderObj(Object resultObj, Object result) {

    }
}
