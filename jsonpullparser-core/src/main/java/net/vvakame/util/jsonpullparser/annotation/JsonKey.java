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

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.FIELD })
public @interface JsonKey {

	static class DiscardAllConverter extends TokenConverter<Void> {

		public static DiscardAllConverter getInstance() {
			return new DiscardAllConverter();
		}

		@Override
		public Void parse(JsonPullParser parser,
				OnJsonObjectAddListener listener) throws IOException,
				JsonFormatException {
			parser.discardValue();
			return null;
		}
	}

	public String value() default "";

	public Class<? extends TokenConverter<?>> converter() default DiscardAllConverter.class;
}