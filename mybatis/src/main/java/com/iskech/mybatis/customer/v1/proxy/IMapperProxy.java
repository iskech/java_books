package com.iskech.mybatis.customer.v1.proxy;

import com.iskech.mybatis.customer.v1.sqlsession.ISqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class IMapperProxy  implements InvocationHandler {
    private ISqlSession iSqlSession;

    public IMapperProxy(ISqlSession iSqlSession) {
        this.iSqlSession = iSqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         //方法返回类型
        Class<?> returnType = method.getReturnType();
        if(Collection.class.isAssignableFrom(returnType)){
            return selectList(method.getDeclaringClass().getName()
                    +"."+
                    method.getName(),args);
        }
        return null;
    }

    public  <T> T selectList(String key, Object[] args) {
        return iSqlSession.selectList(key,args);
    }
}
