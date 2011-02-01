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

public class Log {

	static Messager messager = null;

	static boolean debug = false;


	public static void init(Messager msgr) {
		messager = msgr;
	}

	public static void setDebug(boolean d) {
		debug = d;
	}

	public static void d() {
		if (!debug) {
			return;
		}
		d(getStackInfo());
	}

	public static void d(String msg) {
		if (!debug) {
			return;
		}
		if (messager == null) {
			return;
		}
		messager.printMessage(Diagnostic.Kind.NOTE, msg);
	}

	public static void w(String msg) {
		messager.printMessage(Diagnostic.Kind.WARNING, msg);
	}

	public static void w(String msg, Element element) {
		messager.printMessage(Diagnostic.Kind.WARNING, msg, element);
	}

	public static void e(String msg) {
		messager.printMessage(Diagnostic.Kind.ERROR, msg);
	}

	public static void e(String msg, Element element) {
		messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
	}

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
