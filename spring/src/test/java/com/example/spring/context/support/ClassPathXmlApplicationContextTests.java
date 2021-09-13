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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.example.spring.provider.spring1_5.aop.support.AopUtils;
import com.example.spring.provider.spring1_5.beans.MutablePropertyValues;
import com.example.spring.provider.spring1_5.beans.ResourceTestBean;
import com.example.spring.provider.spring1_5.beans.TestBean;
import com.example.spring.provider.spring1_5.beans.factory.config.RuntimeBeanReference;
import com.example.spring.provider.spring1_5.beans.factory.support.DefaultListableBeanFactory;
import com.example.spring.provider.spring1_5.beans.factory.support.RootBeanDefinition;
import com.example.spring.provider.spring1_5.beans.factory.xml.XmlBeanDefinitionReader;
import com.example.spring.provider.spring1_5.context.ApplicationListener;
import com.example.spring.provider.spring1_5.core.io.ClassPathResource;
import com.example.spring.provider.spring1_5.core.io.FileSystemResource;
import com.example.spring.provider.spring1_5.core.io.Resource;
import com.example.spring.provider.spring1_5.util.FileCopyUtils;

/**
 * @author Juergen Hoeller
 */
public class ClassPathXmlApplicationContextTests extends TestCase {

	public void testMultipleConfigLocations() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] {
					"/org/springframework/context/support/contextB.xml",
					"/org/springframework/context/support/contextC.xml",
					"/org/springframework/context/support/contextA.xml"});
		assertTrue(ctx.containsBean("service"));
		assertTrue(ctx.containsBean("logicOne"));
		assertTrue(ctx.containsBean("logicTwo"));
	}

	public void testConfigLocationPattern() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"/org/springframework/context/support/context*.xml");
		assertTrue(ctx.containsBean("service"));
		assertTrue(ctx.containsBean("logicOne"));
		assertTrue(ctx.containsBean("logicTwo"));
	}

	public void testFactoryBeanAndApplicationListener() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"/org/springframework/context/support/context*.xml");
		ctx.getBeanFactory().registerSingleton("manualFBAAL", new FactoryBeanAndApplicationListener());
		assertEquals(0, ctx.getBeansOfType(ApplicationListener.class).size());
	}

	public void testMessageSourceAware() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"/org/springframework/context/support/context*.xml");
		Service service = (Service) ctx.getBean("service");
		assertEquals(ctx, service.getMessageSource());
	}

	public void testResourceArrayPropertyEditor() throws IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"/org/springframework/context/support/context*.xml");
		Service service = (Service) ctx.getBean("service");
		assertEquals(3, service.getResources().length);
		List resources = Arrays.asList(service.getResources());
		assertTrue(resources.contains(
		    new FileSystemResource(new ClassPathResource("/org/springframework/context/support/contextA.xml").getFile())));
		assertTrue(resources.contains(
		    new FileSystemResource(new ClassPathResource("/org/springframework/context/support/contextB.xml").getFile())));
		assertTrue(resources.contains(
		    new FileSystemResource(new ClassPathResource("/org/springframework/context/support/contextC.xml").getFile())));
	}

	public void testChildWithProxy() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"/org/springframework/context/support/context*.xml");
		ClassPathXmlApplicationContext child = new ClassPathXmlApplicationContext(
				new String[] {"/org/springframework/context/support/childWithProxy.xml"}, ctx);
		assertTrue(AopUtils.isAopProxy(child.getBean("assemblerOne")));
		assertTrue(AopUtils.isAopProxy(child.getBean("assemblerTwo")));
	}

	public void testSelfReference() {
		DefaultListableBeanFactory lbf = new DefaultListableBeanFactory();
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.addPropertyValue("spouse", new RuntimeBeanReference("test"));
		lbf.registerBeanDefinition("test", new RootBeanDefinition(TestBean.class, pvs));
		TestBean test = (TestBean) lbf.getBean("test");
		assertEquals(test, test.getSpouse());
	}

	public void testResourceAndInputStream() throws IOException {
		ClassPathXmlApplicationContext ctx =
		    new ClassPathXmlApplicationContext("/org/springframework/beans/factory/xml/resource.xml") {
			public Resource getResource(String location) {
				if ("classpath:org/springframework/beans/factory/xml/test.properties".equals(location)) {
					return new ClassPathResource("test.properties", ClassPathXmlApplicationContextTests.class);
				}
				return super.getResource(location);
			}
		};
		ResourceTestBean resource1 = (ResourceTestBean) ctx.getBean("resource1");
		ResourceTestBean resource2 = (ResourceTestBean) ctx.getBean("resource2");
		assertTrue(resource1.getResource() instanceof ClassPathResource);
		StringWriter writer = new StringWriter();
		FileCopyUtils.copy(new InputStreamReader(resource1.getResource().getInputStream()), writer);
		assertEquals("contexttest", writer.toString());
		writer = new StringWriter();
		FileCopyUtils.copy(new InputStreamReader(resource1.getInputStream()), writer);
		assertEquals("contexttest", writer.toString());
		writer = new StringWriter();
		FileCopyUtils.copy(new InputStreamReader(resource2.getResource().getInputStream()), writer);
		assertEquals("contexttest", writer.toString());
		writer = new StringWriter();
		FileCopyUtils.copy(new InputStreamReader(resource2.getInputStream()), writer);
		assertEquals("contexttest", writer.toString());
	}

	public void testGenericApplicationContextWithXmlBeanDefinitions() throws Exception {
		GenericApplicationContext ctx = new GenericApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
		reader.loadBeanDefinitions(new ClassPathResource("contextB.xml", getClass()));
		reader.loadBeanDefinitions(new ClassPathResource("contextC.xml", getClass()));
		reader.loadBeanDefinitions(new ClassPathResource("contextA.xml", getClass()));
		ctx.refresh();
		assertTrue(ctx.containsBean("service"));
		assertTrue(ctx.containsBean("logicOne"));
		assertTrue(ctx.containsBean("logicTwo"));
	}

}
