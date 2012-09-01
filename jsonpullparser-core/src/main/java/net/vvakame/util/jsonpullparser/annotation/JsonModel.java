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

import net.vvakame.util.jsonpullparser.JsonPullParser;

/**
 * Annotation to decorate classes should be mapped to JSON.
 * 
 * @see JsonKey
 * @author vvakame
 */
@Retention(RetentionPolicy.CLASS)
@Target({
	ElementType.TYPE
})
public @interface JsonModel {

	/**
	 * Specifies whether parsers should error out on detection of an unknown key in JSON describling this class or not.<br>
	 * They should throw {@link IllegalStateException} if true, or discard it (with {@link JsonPullParser#discardValue()}) and continue otherwise.
	 * @return Whether parsers should throw error upon detection of unknown key about this class or not.
	 * @author vvakame
	 */
	public boolean treatUnknownKeyAsError() default false;

	/**
	 * De-camelize the name of class on mapping (i.e. "source_id" becomes "sourceId", and vice versa) or not.
	 * @return True if de-camelization should be performed, false otherwise.
	 * @see JsonKey#decamelize()
	 * @author vvakame
	 */
	public boolean decamelize() default false;

	/**
	 * Modifier of generated class change to "package private" from "public". 
	 * @return True if modifier change to package private, false otherwise.
	 * @author vvakame
	 */
	public boolean genToPackagePrivate() default false;

	/**
	 * Modifier of JsonMeta class change to "package private" from "public". 
	 * @return True if modifier change to package private, false otherwise.
	 * @author vvakame
	 */
	public boolean jsonMetaToPackagePrivate() default false;

	/**
	 * Whether a dynamic JSON builder should be generated for this class or not.
	 * @return True if dynamic JSON builder should be generated, false otherwise.
	 * @author vvakame
	 */
	public boolean builder() default false;
}
