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

package com.example.spring.context.support;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.example.spring.provider.spring1_5.beans.MutablePropertyValues;
import com.example.spring.provider.spring1_5.beans.TestBean;
import com.example.spring.provider.spring1_5.beans.factory.support.PropertiesBeanDefinitionReader;
import com.example.spring.provider.spring1_5.context.ACATest;
import com.example.spring.provider.spring1_5.context.AbstractApplicationContextTests;
import com.example.spring.provider.spring1_5.context.BeanThatListens;
import com.example.spring.provider.spring1_5.context.ConfigurableApplicationContext;
import com.example.spring.provider.spring1_5.core.io.ClassPathResource;

/**
 * Tests for static application context.
 * @author Rod Johnson
 */
public class StaticApplicationContextTests extends AbstractApplicationContextTests {

	protected StaticApplicationContext sac;

	/** Run for each test */
	protected ConfigurableApplicationContext createContext() throws Exception {
		StaticApplicationContext parent = new StaticApplicationContext();
		Map m = new HashMap();
		m.put("name", "Roderick");
		parent.registerPrototype("rod", TestBean.class, new MutablePropertyValues(m));
		m.put("name", "Albert");
		parent.registerPrototype("father", TestBean.class, new MutablePropertyValues(m));
		parent.refresh();
		parent.addListener(parentListener) ;

		parent.getStaticMessageSource().addMessage("code1", Locale.getDefault(), "message1");

		this.sac = new StaticApplicationContext(parent);
		sac.registerSingleton("beanThatListens", BeanThatListens.class, new MutablePropertyValues());
		sac.registerSingleton("aca", ACATest.class, new MutablePropertyValues());
		sac.registerPrototype("aca-prototype", ACATest.class, new MutablePropertyValues());
		PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(sac.getDefaultListableBeanFactory());
		reader.loadBeanDefinitions(new ClassPathResource("testBeans.properties", getClass()));
		sac.refresh();
		sac.addListener(listener);

		sac.getStaticMessageSource().addMessage("code2", Locale.getDefault(), "message2");

		return sac;
	}

	/** Overridden */
	public void testCount() {
		assertCount(15);
	}

}
