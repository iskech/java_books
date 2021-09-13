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

package com.example.spring.provider.spring1_5.beans.factory;

import com.example.spring.provider.spring1_5.beans.BeansException;

/**
 * Thrown when a bean doesn't match the required type.
 * @author Rod Johnson
 */
public class BeanNotOfRequiredTypeException extends BeansException {

	/** The name of the instance that was of the wrong type */
	private final String beanName;

	/** The required type */
	private final Class requiredType;

	/** The offending type */
	private final Class actualType;

	/**
	 * Create a new BeanNotOfRequiredTypeException.
	 * @param beanName the name of the bean requested
	 * @param requiredType the required type
	 * @param actualType the actual type returned, which did not match
	 * the expected type
	 */
	public BeanNotOfRequiredTypeException(String beanName, Class requiredType, Class actualType) {
		super("Bean named '" + beanName + "' must be of type [" + requiredType.getName() +
				"], but was actually of type [" + actualType.getName() + "]");
		this.beanName = beanName;
		this.requiredType = requiredType;
		this.actualType = actualType;
	}

	/**
	 * Return the name of the instance that was of the wrong type.
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * Return the required type for the bean.
	 */
	public Class getRequiredType() {
		return requiredType;
	}

	/**
	 * Return the actual type of the instance found.
	 */
	public Class getActualType() {
		return actualType;
	}

}
