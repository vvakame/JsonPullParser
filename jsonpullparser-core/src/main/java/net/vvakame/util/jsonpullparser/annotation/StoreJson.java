/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.vvakame.util.jsonpullparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to keep the original JSON data, including one we skipped on deserialization process.<br>
 * Kept in compact format (i.e. whitespaces are stripped off and entities are decoded.)
 * @author vvakame
 */
@Retention(RetentionPolicy.SOURCE)
@Target({
	ElementType.FIELD
})
public @interface StoreJson {

	/**
	 * Specifies whether parsers should error out if it turns out that the JSON logging facility should be disabled or not.<br>
	 * They should throw {@link IllegalStateException} if true, or silently disable logging and continue otherwise.
	 * @return Whether parsers should throw error if it turns out the JSON logging facility should be disabled or not.
	 * @author vvakame
	 */
	public boolean treatLogDisabledAsError() default false;
}
