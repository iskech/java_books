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

package com.example.spring.provider.spring1_5.beans.factory.config;

import com.example.spring.provider.spring1_5.beans.factory.FactoryBean;
import com.example.spring.provider.spring1_5.beans.factory.InitializingBean;
import com.example.spring.provider.spring1_5.beans.support.ArgumentConvertingMethodInvoker;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>FactoryBean which returns a value which is the result of a static or instance
 * method invocation. For most use cases it is better to just use the container's 
 * built-in factory-method support for the same purpose, since that is smarter at
 * converting arguments. This factory bean is still useful though when you need to
 * call a method which doesn't return any value (for example, a static class method
 * to force some sort of initialization to happen). This use case is not supported
 * by factory-methods, since a return value is needed to become the bean.</p.
 *
 * <p>Note that as it is expected to be used mostly for accessing
 * factory methods, this factory by default operates in a <b>singleton</b> fashion.
 * The first request to {@link #getObject} by the owning bean factory will cause
 * a method invocation, whose return value will be cached for subsequent requests.
 * An internal {@link #setSingleton singleton} property may be set to false, to
 * cause this factory to invoke the target method each time it is asked for an
 * object.</p>
 *
 * <p>A static target method may be specified by setting the
 * {@link #setTargetMethod targetMethod} property to a String representing the static
 * method name, with {@link #setTargetClass targetClass} specifying the Class that
 * the static method is defined on. Alternatively, a target instance method may be
 * specified, by setting the {@link #setTargetObject targetObject} property as the target
 * object, and the {@link #setTargetMethod targetMethod} property as the name of the
 * method to call on that target object. Arguments for the method invocation may be
 * specified by setting the args property.</p>
 *
 * <p>This class depends on {@link #afterPropertiesSet()} being called once
 * all properties have been set, as per the InitializingBean contract.</p>
 *
 * <p>An example (in an XML based bean factory definition) of a bean definition
 * which uses this class to call a static factory method:</p>
 *
 * <pre>
 * &lt;bean id="myObject" class="com.example.spring.provider.spring1_5.beans.factory.config.MethodInvokingFactoryBean">
 *   &lt;property name="staticMethod">&lt;value>com.whatever.MyClassFactory.getInstance&lt;/value>&lt;/property>
 * &lt;/bean></pre>
 *
 * <p>An example of calling a static method then an instance method to get at a
 * Java system property. Somewhat verbose, but it works.
 *
 * <pre>
 * &lt;bean id="sysProps" class="com.example.spring.provider.spring1_5.beans.factory.config.MethodInvokingFactoryBean">
 *   &lt;property name="targetClass">&lt;value>java.lang.System&lt;/value>&lt;/property>
 *   &lt;property name="targetMethod">&lt;value>getProperties&lt;/value>&lt;/property>
 * &lt;/bean>
 *
 * &lt;bean id="javaVersion" class="com.example.spring.provider.spring1_5.beans.factory.config.MethodInvokingFactoryBean">
 *   &lt;property name="targetObject">&lt;ref local='sysProps'/>&lt;/property>
 *   &lt;property name="targetMethod">&lt;value>getProperty&lt;/value>&lt;/property>
 *   &lt;property name="arguments">
 *     &lt;list>
 *       &lt;value>java.version&lt;/value>
 *     &lt;/list>
 *   &lt;/property>
 * &lt;/bean></pre>
 * 
 * @author Colin Sampaleanu
 * @author Juergen Hoeller
 * @since 2003-11-21
 */
public class MethodInvokingFactoryBean extends ArgumentConvertingMethodInvoker
		implements FactoryBean, InitializingBean {

	private boolean singleton = true;

	/** method call result in the singleton case */
	private Object singletonObject;

	/**
	 * Set if a singleton should be created, or a new object on each
	 * request else. Default is true.
	 */
	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	public void afterPropertiesSet()
			throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		prepare();
		if (this.singleton) {
			this.singletonObject = invoke();
		}
	}

	/**
	 * Will return the same value each time if the singleton property is set
	 * to true, and otherwise return the value returned from invoking the
	 * specified method.
	 */
	public Object getObject() throws InvocationTargetException, IllegalAccessException {
		if (this.singleton) {
			return this.singletonObject;
		}
		else {
			// prototype: new object on each call
			return invoke();
		}
	}

	
	public Class getObjectType() {
		Class type = getPreparedMethod().getReturnType();
		if (type.equals(void.class)) {
			type = VoidType.class;
		}
		return type;
	}

	public boolean isSingleton() {
		return singleton;
	}

}
