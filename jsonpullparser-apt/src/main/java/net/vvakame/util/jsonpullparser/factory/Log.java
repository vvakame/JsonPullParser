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
 * AndroidのLogクラスっぽい使い方で出力を行えるようにしたクラス.
 * @author vvakame
 */
public class Log {

	static Messager messager = null;

	static boolean debug = false;


	/**
	 * 初期化.
	 * @param msgr
	 * @author vvakame
	 */
	public static void init(Messager msgr) {
		messager = msgr;
	}

	/**
	 * デバッグモードの設定.
	 * @param d デバッグモード
	 * @author vvakame
	 */
	public static void setDebug(boolean d) {
		debug = d;
	}

	/**
	 * デバッグ出力を行う.<br>
	 * {@link #debug} がfalseの場合出力を行わない.
	 * @author vvakame
	 */
	public static void d() {
		if (!debug) {
			return;
		}
		d(getStackInfo());
	}

	/**
	 * デバッグ出力を行う.<br>
	 * {@link #debug} がfalseの場合出力を行わない.
	 * @param msg 出力メッセージ
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
	 * 警告出力を行う.
	 * @param msg 出力メッセージ
	 * @author vvakame
	 */
	public static void w(String msg) {
		messager.printMessage(Diagnostic.Kind.WARNING, msg);
	}

	/**
	 * element に対してWarning出力を行う.<br>
	 * @param msg 出力メッセージ
	 * @param element 警告対象
	 * @author vvakame
	 */
	public static void w(String msg, Element element) {
		messager.printMessage(Diagnostic.Kind.WARNING, msg, element);
	}

	/**
	 * エラー出力を行う.
	 * @param msg 出力メッセージ
	 * @author vvakame
	 */
	public static void e(String msg) {
		messager.printMessage(Diagnostic.Kind.ERROR, msg);
	}

	/**
	 * element をコンパイルエラーにする.
	 * @param msg 出力メッセージ
	 * @param element コンパイルエラー対象
	 * @author vvakame
	 */
	public static void e(String msg, Element element) {
		messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
	}

	/**
	 * エラー出力を行う.
	 * @param e 発生した例外
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
