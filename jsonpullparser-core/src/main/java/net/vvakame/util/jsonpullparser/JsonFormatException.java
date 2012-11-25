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

package net.vvakame.util.jsonpullparser;

/**
 * Thrown if the parser has detected JSON format error.
 * 
 * @author vvakame
 */
public class JsonFormatException extends Exception {

	private static final long serialVersionUID = -1877852218539180703L;

	final JsonPullParser parser;


	/**
	 * the constructor.
	 * @category constructor
	 */
	@Deprecated
	public JsonFormatException() {
		super();
		this.parser = null;
	}

	/**
	 * the constructor.
	 * @param parser
	 * @category constructor
	 */
	public JsonFormatException(JsonPullParser parser) {
		super();
		this.parser = parser;
	}

	/**
	 * the constructor.
	 * @param msg
	 * @category constructor
	 */
	public JsonFormatException(String msg) {
		super(msg);
		this.parser = null;
	}

	/**
	 * the constructor.
	 * @param msg
	 * @param parser
	 * @category constructor
	 */
	public JsonFormatException(String msg, JsonPullParser parser) {
		super(msg);
		this.parser = parser;
	}

	/**
	 * the constructor.
	 * @param e
	 * @category constructor
	 */
	@Deprecated
	public JsonFormatException(Throwable e) {
		super(e);
		this.parser = null;
	}

	/**
	 * the constructor.
	 * @param e
	 * @param parser
	 * @category constructor
	 */
	public JsonFormatException(Throwable e, JsonPullParser parser) {
		super(e);
		this.parser = parser;
	}

	/**
	 * @return the parser
	 * @category accessor
	 */
	public JsonPullParser getParser() {
		return parser;
	}
}
