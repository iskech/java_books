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

package com.example.spring.provider.spring1_5.aop.framework.autoproxy.metadata;

import com.example.spring.provider.spring1_5.aop.framework.autoproxy.target.AbstractPrototypeBasedTargetSourceCreator;
import com.example.spring.provider.spring1_5.aop.target.AbstractPrototypeBasedTargetSource;
import com.example.spring.provider.spring1_5.aop.target.PrototypeTargetSource;
import com.example.spring.provider.spring1_5.beans.factory.BeanFactory;
import com.example.spring.provider.spring1_5.metadata.Attributes;

import java.util.Collection;

/**
 * PrototypeTargetSourceCreator driven by metadata. Creates a prototype
 * only if there's a PrototypeAttribute associated with the class.
 * @author Rod Johnson
 * @see com.example.spring.provider.spring1_5.aop.target.PrototypeTargetSource
 */
public class AttributesPrototypeTargetSourceCreator extends AbstractPrototypeBasedTargetSourceCreator {

	private final Attributes attributes;

	public AttributesPrototypeTargetSourceCreator(Attributes attributes) {
		this.attributes = attributes;
	}

	protected AbstractPrototypeBasedTargetSource createPrototypeTargetSource(Object bean, String beanName,
																																					 BeanFactory bf) {
		Class beanClass = bean.getClass();
		// See if there's a pooling attribute
		Collection atts = attributes.getAttributes(beanClass, PrototypeAttribute.class);
		if (atts.isEmpty()) {
			// No pooling attribute: don't create a custom TargetSource
			return null;
		}
		else {
			return new PrototypeTargetSource();
		}
	}

}
