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

import com.example.spring.provider.spring1_5.beans.BeansException;
import com.example.spring.provider.spring1_5.beans.factory.config.BeanPostProcessor;

/**
 * Subinterface of BeanPostProcessor that adds a before-destruction callback.
 *
 * <p>The typical usage will be to invoke custom destruction callbacks on
 * specific bean types, matching corresponding initialization callbacks.
 *
 * @author Juergen Hoeller
 * @since 1.0.1
 * @see com.example.spring.provider.spring1_5.web.struts.ActionServletAwareProcessor
 */
public interface DestructionAwareBeanPostProcessor extends BeanPostProcessor {

	/**
	 * Apply this BeanPostProcessor to the given bean instance before
	 * its destruction. Can invoke custom destruction callbacks.
	 * <p>Like DisposableBean's <code>destroy</code> and a custom destroy method,
	 * this callback just applies to singleton beans in the factory (including
	 * inner beans).
	 * @param bean the new bean instance
	 * @param beanName the name of the bean
	 * @throws BeansException in case of errors
	 * @see com.example.spring.provider.spring1_5.beans.factory.DisposableBean
	 * @see com.example.spring.provider.spring1_5.beans.factory.support.AbstractBeanDefinition#setDestroyMethodName
	 */
	void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException;

}
