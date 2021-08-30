package com.iskech.mybatis.customer.v1.handler;



import com.iskech.mybatis.customer.v1.base.IConfiguration;
import com.iskech.mybatis.customer.v1.executor.IExecutor;
import com.iskech.mybatis.customer.v1.mapping.*;
import org.apache.ibatis.executor.result.DefaultResultHandler;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IDefaultResultSetHandler {
    private final IExecutor executor;
    private final IConfiguration configuration;
    private final IMappedStatement mappedStatement;

    public IDefaultResultSetHandler(IExecutor executor, IConfiguration configuration, IMappedStatement mappedStatement) {
        this.executor = executor;
        this.configuration = configuration;
        this.mappedStatement = mappedStatement;
    }

    public List<Object> handleResultSets(Statement stmt) throws SQLException {


        final List<Object> multipleResults = new ArrayList<Object>();

        int resultSetCount = 0;
        IResultSetWrapper rsw = getFirstResultSet(stmt);

        List<IResultMap> resultMaps = mappedStatement.getResultMaps();
        int resultMapCount = resultMaps.size();
       // validateResultMapsCount(rsw, resultMapCount);
        while (rsw != null && resultMapCount > resultSetCount) {
            IResultMap resultMap = resultMaps.get(resultSetCount);
            handleResultSet(rsw, resultMap, multipleResults, null);
            rsw = getNextResultSet(stmt);
            cleanUpAfterHandlingResultSet();
            resultSetCount++;
        }

        String[] resultSets = mappedStatement.getResultSets();
        if (resultSets != null) {
            while (rsw != null && resultSetCount < resultSets.length) {
                IResultMapping parentMapping = nextResultMaps.get(resultSets[resultSetCount]);
                if (parentMapping != null) {
                    String nestedResultMapId = parentMapping.getNestedResultMapId();
                    IResultMap resultMap = configuration.getResultMap(nestedResultMapId);
                    handleResultSet(rsw, resultMap, null, parentMapping);
                }
                rsw = getNextResultSet(stmt);
                cleanUpAfterHandlingResultSet();
                resultSetCount++;
            }
        }

        return collapseSingleResultList(multipleResults);
    }

    private IResultSetWrapper getFirstResultSet(Statement stmt) throws SQLException {
        ResultSet rs = stmt.getResultSet();
        while (rs == null) {
            // move forward to get the first resultset in case the driver
            // doesn't return the resultset as the first result (HSQLDB 2.1)
            if (stmt.getMoreResults()) {
                rs = stmt.getResultSet();
            } else {
                if (stmt.getUpdateCount() == -1) {
                    // no more results. Must be no resultset
                    break;
                }
            }
        }
        return rs != null ? new IResultSetWrapper(rs, configuration) : null;
    }

    private void handleResultSet(IResultSetWrapper rsw, IResultMap resultMap, List<Object> multipleResults, IResultMapping parentMapping) throws SQLException {
        try {
            if (parentMapping != null) {
                handleRowValues(rsw, resultMap, null, IRowBounds.DEFAULT, parentMapping);
            } else {
                if (resultHandler == null) {
                    DefaultResultHandler defaultResultHandler = new IDefaultResultHandler(objectFactory);
                    handleRowValues(rsw, resultMap, defaultResultHandler, rowBounds, null);
                    multipleResults.add(defaultResultHandler.getResultList());
                } else {
                    handleRowValues(rsw, resultMap, resultHandler, rowBounds, null);
                }
            }
        } finally {
            // issue #228 (close resultsets)
            closeResultSet(rsw.getResultSet());
        }
    }

}
