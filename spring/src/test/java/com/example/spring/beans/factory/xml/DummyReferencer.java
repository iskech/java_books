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

package com.example.spring.beans.factory.xml;

import org.springframework.beans.TestBean;
import org.springframework.beans.factory.DummyFactory;

/**
 * @author Juergen Hoeller
 * @since 21.07.2003
 */
public class DummyReferencer {

	private TestBean testBean1;

	private TestBean testBean2;

	private DummyFactory dummyFactory;

	public TestBean getTestBean1() {
		return testBean1;
	}

	public void setTestBean1(TestBean testBean1) {
		this.testBean1 = testBean1;
	}

	public TestBean getTestBean2() {
		return testBean2;
	}

	public void setTestBean2(TestBean testBean2) {
		this.testBean2 = testBean2;
	}

	public DummyFactory getDummyFactory() {
		return dummyFactory;
	}

	public void setDummyFactory(DummyFactory dummyFactory) {
		this.dummyFactory = dummyFactory;
	}

}