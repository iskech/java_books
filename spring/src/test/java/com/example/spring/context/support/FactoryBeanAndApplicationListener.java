package com.example.spring.context.support;

import com.example.spring.provider.spring1_5.beans.factory.FactoryBean;
import com.example.spring.provider.spring1_5.context.ApplicationEvent;
import com.example.spring.provider.spring1_5.context.ApplicationListener;

/**
 * @author Juergen Hoeller
 * @since 06.10.2004
 */
public class FactoryBeanAndApplicationListener implements FactoryBean, ApplicationListener {

	public Object getObject() throws Exception {
		return "";
	}

	public Class getObjectType() {
		return String.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void onApplicationEvent(ApplicationEvent event) {
	}

}
