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

import com.example.spring.provider.spring1_5.beans.MutablePropertyValues;
import com.example.spring.provider.spring1_5.beans.factory.config.BeanFactoryPostProcessor;
import com.example.spring.provider.spring1_5.beans.factory.config.ConfigurableListableBeanFactory;
import com.example.spring.provider.spring1_5.beans.factory.config.ConstructorArgumentValues;
import com.example.spring.provider.spring1_5.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * A BeanDefinition describes a bean instance, which has property values,
 * constructor argument values, and further information supplied by
 * concrete implementations.
 *
 * <p>This is just a minimal interface: The main intention is to allow
 * BeanFactoryPostProcessors (like PropertyPlaceholderConfigurer) to
 * access and modify property values.
 *
 * @author Juergen Hoeller
 * @since 19.03.2004
 * @see ConfigurableListableBeanFactory#getBeanDefinition
 * @see BeanFactoryPostProcessor
 * @see PropertyPlaceholderConfigurer
 * @see com.example.spring.provider.spring1_5.beans.factory.support.RootBeanDefinition
 * @see com.example.spring.provider.spring1_5.beans.factory.support.ChildBeanDefinition
 */
public interface BeanDefinition {

	/**
	 * Return the class defined for the bean, if any.
	 * Only returns the class for the particular bean definition -
	 * does not merge child definitions with parents!
	 * @deprecated Use BeanFactory's <code>getType</code> method instead,
	 * which properly resolves child bean definitions and
	 * asks FactoryBeans for the type of object they create.
	 * @see com.example.spring.provider.spring1_5.beans.factory.BeanFactory#getType
	 */
	Class getBeanClass();

	/**
	 * Return whether this bean is "abstract", i.e. not meant to be instantiated.
	 */
	boolean isAbstract();

	/**
	 * Return whether this a <b>Singleton</b>, with a single, shared instance
	 * returned on all calls.
	 */
	boolean isSingleton();

	/**
	 * Return whether this bean should be lazily initialized, i.e. not
	 * eagerly instantiated on startup. Only applicable to a singleton bean.
	 */
	boolean isLazyInit();

	/**
	 * Return the PropertyValues to be applied to a new instance of the bean, if any.
	 * Can be modified during bean factory post-processing.
	 * @return the PropertyValues object, or null
	 */
	MutablePropertyValues getPropertyValues();

	/**
	 * Return the constructor argument values for this bean, if any.
	 * Can be modified during bean factory post-processing.
	 * @return the ConstructorArgumentValues object, or null
	 */
	ConstructorArgumentValues getConstructorArgumentValues();

	/**
	 * Return a description of the resource that this bean definition
	 * came from (for the purpose of showing context in case of errors).
	 */
	String getResourceDescription();

}
