package net.vvakame.util.jsonpullparser.factory;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class Log {
	static Messager messager = null;

	public static void init(Messager msgr) {
		messager = msgr;
	}

	public static void d(String msg) {
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
		messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
	}
}
