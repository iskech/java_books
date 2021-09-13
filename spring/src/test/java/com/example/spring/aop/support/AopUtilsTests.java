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

package com.example.spring.aop.support;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import org.aopalliance.aop.Advice;

import com.example.spring.provider.spring1_5.aop.ClassFilter;
import com.example.spring.provider.spring1_5.aop.MethodMatcher;
import com.example.spring.provider.spring1_5.aop.Pointcut;
import com.example.spring.provider.spring1_5.aop.interceptor.ExposeInvocationInterceptor;
import com.example.spring.provider.spring1_5.aop.interceptor.NopInterceptor;
import com.example.spring.provider.spring1_5.aop.target.EmptyTargetSource;
import com.example.spring.provider.spring1_5.beans.DerivedTestBean;
import com.example.spring.provider.spring1_5.beans.IOther;
import com.example.spring.provider.spring1_5.beans.ITestBean;
import com.example.spring.provider.spring1_5.beans.TestBean;
import com.example.spring.provider.spring1_5.util.SerializationTestUtils;

/**
 * @author Rod Johnson
 */
public class AopUtilsTests extends TestCase {

	public void testGetAllInterfaces() {
		DerivedTestBean testBean = new DerivedTestBean();
		List ifcs = Arrays.asList(AopUtils.getAllInterfaces(testBean));
		assertEquals("Correct number of interfaces", 8, ifcs.size());
		assertTrue("Contains Serializable", ifcs.contains(Serializable.class));
		assertTrue("Contains ITestBean", ifcs.contains(ITestBean.class));
		assertTrue("Contains IOther", ifcs.contains(IOther.class));
	}
	
	public void testPointcutCanNeverApply() {
		class TestPointcut extends StaticMethodMatcherPointcut {
			public boolean matches(Method method, Class clazzy) {
				return false;
			}
		}
	
		Pointcut no = new TestPointcut();
		assertFalse(AopUtils.canApply(no, Object.class));
	}

	public void testPointcutAlwaysApplies() {
		assertTrue(AopUtils.canApply(new DefaultPointcutAdvisor(new NopInterceptor()), Object.class));
		assertTrue(AopUtils.canApply(new DefaultPointcutAdvisor(new NopInterceptor()), TestBean.class));
	}

	public void testPointcutAppliesToOneMethodOnObject() {
		class TestPointcut extends StaticMethodMatcherPointcut {
			public boolean matches(Method method, Class clazz) {
				return method.getName().equals("hashCode");
			}
		}

		Pointcut pc = new TestPointcut();
	
		// will return true if we're not proxying interfaces
		assertTrue(AopUtils.canApply(pc, Object.class));
	}

	/**
	 * Test that when we serialize and deserialize various canonical instances
	 * of AOP classes, they return the same instance, not a new instance
	 * that's subverted the singleton construction limitation.
	 */
	public void testCanonicalFrameworkClassesStillCanonicalOnDeserialization() throws Exception {
		assertSame(MethodMatcher.TRUE, SerializationTestUtils.serializeAndDeserialize(MethodMatcher.TRUE));
		assertSame(ClassFilter.TRUE, SerializationTestUtils.serializeAndDeserialize(ClassFilter.TRUE));
		assertSame(Pointcut.TRUE, SerializationTestUtils.serializeAndDeserialize(Pointcut.TRUE));
		assertSame(EmptyTargetSource.INSTANCE, SerializationTestUtils.serializeAndDeserialize(EmptyTargetSource.INSTANCE));
		assertSame(Pointcuts.SETTERS, SerializationTestUtils.serializeAndDeserialize(Pointcuts.SETTERS));
		assertSame(Pointcuts.GETTERS, SerializationTestUtils.serializeAndDeserialize(Pointcuts.GETTERS));
		assertSame(ExposeInvocationInterceptor.INSTANCE,
				SerializationTestUtils.serializeAndDeserialize(ExposeInvocationInterceptor.INSTANCE));
	}

	public void testDynamicSuperclasses() {
		DynamicMethodMatcherPointcut mmpc = new DynamicMethodMatcherPointcut() {
			public boolean matches(Method m, Class targetClass, Object[] args) {
				throw new UnsupportedOperationException();
			}
		};
		assertSame(mmpc, mmpc.getMethodMatcher());
		assertSame(ClassFilter.TRUE, mmpc.getClassFilter());
		
		DynamicMethodMatcherPointcutAdvisor a = new DynamicMethodMatcherPointcutAdvisor() {
			public boolean matches(Method m, Class targetClass, Object[] args) {
				throw new UnsupportedOperationException();
			}
		};
		Advice advice = new NopInterceptor();
		a.setAdvice(advice);
		assertSame(a, a.getMethodMatcher());
		assertSame(ClassFilter.TRUE, a.getClassFilter());
		assertSame(a, a.getPointcut());
		assertSame(advice, a.getAdvice());
	}

}
