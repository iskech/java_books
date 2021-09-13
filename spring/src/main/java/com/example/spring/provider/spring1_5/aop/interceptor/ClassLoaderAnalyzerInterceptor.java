/*
 * Copyright 2002-2004 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.example.spring.provider.spring1_5.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.example.spring.provider.spring1_5.util.ClassLoaderUtils;

import java.io.Serializable;

/**
 * Trivial classloader analyzer interceptor.
 * @author Rod Johnson
 * @author Dmitriy Kopylenko
 * @see com.example.spring.provider.spring1_5.util.ClassLoaderUtils
 */
public class ClassLoaderAnalyzerInterceptor implements MethodInterceptor, Serializable {

	/** Static to avoid serializing the logger */
	protected static final Log logger = LogFactory.getLog(ClassLoaderAnalyzerInterceptor.class);

	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (logger.isInfoEnabled()) {
			logger.info(
			    ClassLoaderUtils.showClassLoaderHierarchy(
			        invocation.getThis(),
			        invocation.getThis().getClass().getName(),
			        "\n",
			        "-"));
		}
		return invocation.proceed();
	}

}
