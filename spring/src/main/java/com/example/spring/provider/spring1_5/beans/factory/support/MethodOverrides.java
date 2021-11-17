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

package org.springframework.beans.factory.support;

import org.springframework.beans.factory.support.MethodOverride;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Set of method overrides, determining which, if any, methods on a
 * managed object the Spring IoC container will override at runtime.
 * @author Rod Johnson
 */
public class MethodOverrides {

	private final Set overrides = new HashSet();
	
	/**
	 * Set of method names that are overloaded
	 */
	private final Set overloadedMethodNames = new HashSet();

	/**
	 * Create new MethodOverrides.
	 */
	public MethodOverrides() {
	}

	/**
	 * Deep copy constructor.
	 */
	public MethodOverrides(org.springframework.beans.factory.support.MethodOverrides other) {
		addOverrides(other);
	}

	/**
	 * Copy all given method overrides into this object.
	 */
	public void addOverrides(org.springframework.beans.factory.support.MethodOverrides other) {
		if (other != null) {
			this.overrides.addAll(other.getOverrides());
			this.overloadedMethodNames.addAll(other.overloadedMethodNames);
		}
	}

	/**
	 * Add the given method override.
	 */
	public void addOverride(MethodOverride override) {
		this.overrides.add(override);
	}

	/**
	 * Return all method overrides contained by this object.
	 */
	public Set getOverrides() {
		return overrides;
	}

	public void addOverloadedMethodName(String methodName) {
		this.overloadedMethodNames.add(methodName);
	}
	
	public boolean isOverloadedMethodName(String methodName) {
		return this.overloadedMethodNames.contains(methodName);
	}
	
	/**
	 * Return whether the set of method overrides is empty.
	 */
	public boolean isEmpty() {
		return this.overrides.isEmpty();
	}
	
	/**
	 * Return the override for the given method, if any.
	 * @param method method to check for overrides for
	 * @return the method override, or null if none
	 */
	public MethodOverride getOverride(Method method) {
		for (Iterator it = this.overrides.iterator(); it.hasNext();) {
			MethodOverride methodOverride = (MethodOverride) it.next();
			if (methodOverride.matches(method, this)) {
				return methodOverride;
			}			
		}
		return null;
	}

}