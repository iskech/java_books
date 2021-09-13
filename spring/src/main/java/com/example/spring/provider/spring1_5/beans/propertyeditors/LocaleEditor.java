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

package com.example.spring.provider.spring1_5.beans.propertyeditors;

import com.example.spring.provider.spring1_5.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Editor for <code>java.util.Locale</code>, to directly feed a Locale property.
 *
 * <p>Expects the same syntax as Locale's <code>toString</code>, i.e. language +
 * optionally country + optionally variant, separated by "_" (e.g. "en", "en_US").
 * Also accepts spaces as separators, as alternative to underscores.
 *
 * @author Juergen Hoeller
 * @since 26.05.2003
 * @see StringUtils#parseLocaleString
 */
public class LocaleEditor extends PropertyEditorSupport {

	public void setAsText(String text) {
		setValue(StringUtils.parseLocaleString(text));
	}

}
