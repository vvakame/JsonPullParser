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


	/**
	 * the constructor.
	 * @category constructor
	 */
	public JsonFormatException() {
		super();
	}

	/**
	 * the constructor.
	 * @param msg
	 * @category constructor
	 */
	public JsonFormatException(String msg) {
		super(msg);
	}

	/**
	 * the constructor.
	 * @param e
	 * @category constructor
	 */
	public JsonFormatException(Throwable e) {
		super(e);
	}
}
