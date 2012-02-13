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

package net.vvakame.util.jsonpullparser.factory;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Logging facility modelled after Android's Log class.
 * @author vvakame
 */
public class Log {

	static Messager messager = null;

	static boolean debug = false;


	/**
	 * Initialization.
	 * @param msgr
	 * @author vvakame
	 */
	public static void init(Messager msgr) {
		messager = msgr;
	}

	/**
	 * Enables or disables debugging mode.
	 * @param d True to enable, false to disable
	 * @author vvakame
	 */
	public static void setDebug(boolean d) {
		debug = d;
	}

	/**
	 * Logs debug message.<br>
	 * No-op if the debugging mode ({@link #debug}) is disabled.
	 * @author vvakame
	 */
	public static void d() {
		if (!debug) {
			return;
		}
		d(getStackInfo());
	}

	/**
	 * Logs debug message.<br>
	 * No-op if the debugging mode ({@link #debug}) is disabled.
	 * @param msg Message
	 * @author vvakame
	 */
	public static void d(String msg) {
		if (!debug) {
			return;
		}
		if (messager == null) {
			return;
		}
		messager.printMessage(Diagnostic.Kind.NOTE, msg);
	}

	/**
	 * Logs warning message.
	 * @param msg Message
	 * @author vvakame
	 */
	public static void w(String msg) {
		messager.printMessage(Diagnostic.Kind.WARNING, msg);
	}

	/**
	 * Logs warning message about the given element.<br>
	 * @param msg Message
	 * @param element Offending element
	 * @author vvakame
	 */
	public static void w(String msg, Element element) {
		messager.printMessage(Diagnostic.Kind.WARNING, msg, element);
	}

	/**
	 * Logs error message.
	 * @param msg Message
	 * @author vvakame
	 */
	public static void e(String msg) {
		messager.printMessage(Diagnostic.Kind.ERROR, msg);
	}

	/**
	 * Logs error message about the given element.
	 * @param msg Message
	 * @param element Offending element
	 * @author vvakame
	 */
	public static void e(String msg, Element element) {
		messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
	}

	/**
	 * Logs error message about the given exception.
	 * @param e Offending throwable
	 * @author vvakame
	 */
	public static void e(Throwable e) {
		messager.printMessage(Diagnostic.Kind.ERROR, "exception thrown! " + e.getMessage());
	}

	static String getStackInfo() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (StackTraceElement stack : stackTrace) {
			if (Thread.class.getCanonicalName().equals(stack.getClassName())) {
				continue;
			} else if (Log.class.getCanonicalName().equals(stack.getClassName())) {
				continue;
			} else {
				return stack.getClassName() + "#" + stack.getMethodName() + "@L"
						+ stack.getLineNumber();
			}
		}
		return "unknown";
	}
}
