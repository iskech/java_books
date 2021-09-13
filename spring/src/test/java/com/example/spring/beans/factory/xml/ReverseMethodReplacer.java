/*
 * The Spring Framework is published under the terms
 * of the Apache Software License.
 */
 
package com.example.spring.beans.factory.xml;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.example.spring.provider.spring1_5.beans.factory.support.MethodReplacer;

/**
 * 
 * @author Rod Johnson
 */
public class ReverseMethodReplacer implements MethodReplacer, Serializable {

	/**
	 * @see com.example.spring.provider.spring1_5.beans.factory.support.MethodReplacer#reimplement(Object, Method, Object[])
	 */
	public Object reimplement(Object o, Method m, Object[] args) throws Throwable {
		String s = (String) args[0];
		return new StringBuffer(s).reverse().toString();
	}

}
