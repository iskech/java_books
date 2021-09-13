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

package com.example.spring.provider.spring1_5.beans.factory.access;

import com.example.spring.provider.spring1_5.beans.BeansException;
import com.example.spring.provider.spring1_5.beans.factory.BeanFactory;
import com.example.spring.provider.spring1_5.beans.factory.NoSuchBeanDefinitionException;
import com.example.spring.provider.spring1_5.beans.factory.access.BootstrapException;
import com.example.spring.provider.spring1_5.beans.factory.support.DefaultListableBeanFactory;
import com.example.spring.provider.spring1_5.beans.factory.support.PropertiesBeanDefinitionReader;

/**
 * One singleton to rule them all. Reads System properties, which
 * must contain the definition of a bootstrap bean factory using
 * the Properties syntax supported by PropertiesBeanDefinitionReader.
 *
 * <oo>The name of the bootstrap factory must be "bootstrapBeanFactory".
 * 
 * Thus a typical definition might be:
 * <code>
 * bootstrapBeanFactory.class=com.mycompany.MyBeanFactory
 * </code>
 *
 * <p>Use as follows:
 * <code>
 * BeanFactory bf = BeanFactoryBootstrap.getInstance().getBeanFactory();
 * </code>
 *
 * @author Rod Johnson
 * @since December 2, 2002
 * @see PropertiesBeanDefinitionReader
 */
public class BeanFactoryBootstrap {

	public static final String BEAN_FACTORY_BEAN_NAME = "bootstrapBeanFactory";

	private static com.example.spring.provider.spring1_5.beans.factory.access.BeanFactoryBootstrap instance;

	private static BeansException startupException;

	private static void initializeSingleton() {
		try {
			instance = new com.example.spring.provider.spring1_5.beans.factory.access.BeanFactoryBootstrap();
		}
		catch (BeansException ex) {
			startupException = ex;
		}
	}

	// Do initialization when this class is loaded to avoid
	// potential concurrency issues or the need to synchronize later
	static {
		initializeSingleton();
	}

	/**
	 * Return the singleton instance of the bootstrap factory
	 * @return BeanFactoryBootstrap
	 * @throws BeansException
	 */
	public static com.example.spring.provider.spring1_5.beans.factory.access.BeanFactoryBootstrap getInstance() throws BeansException {
		if (startupException != null)
			throw startupException;
		// Really an assertion
		if (instance == null)
			throw new BootstrapException("Anomaly: instance and exception null", null);
		return instance;
	}
	
	/**
	 * <b>For testing only. Cleans and reinitalizes the instance.
	 * Do not use in a production application!</b>
	 */
	protected static void reinitialize() {
		instance = null;
		startupException = null;
		initializeSingleton();
	}


	/** The Singleton instance */
	private BeanFactory bootstrapFactory;
	
	/**
	 * Apply rules to load factory.
	 */
	private BeanFactoryBootstrap() throws BeansException {
		DefaultListableBeanFactory startupFactory = new DefaultListableBeanFactory();
		PropertiesBeanDefinitionReader propReader = new PropertiesBeanDefinitionReader(startupFactory);
		try {
			propReader.registerBeanDefinitions(System.getProperties());
			this.bootstrapFactory = (BeanFactory) startupFactory.getBean(BEAN_FACTORY_BEAN_NAME);
		}
		catch (ClassCastException ex) {
			throw new BootstrapException("Bootstrap bean factory class does not implement BeanFactory interface", ex);
		}
		catch (NoSuchBeanDefinitionException ex) {
			throw new BootstrapException("No bean named '" + BEAN_FACTORY_BEAN_NAME + "' in system properties: [" + startupFactory + "]", null);
		}
		catch (BeansException ex) {
			throw new BootstrapException("Failed to bootstrap bean factory", ex);
		}
	}

	/**
	 * Return the BeanFactory managed by the Bootstrap.
	 */
	public BeanFactory getBeanFactory() {
		return bootstrapFactory;
	}
	
}
