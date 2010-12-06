package net.vvakame.util.jsonpullparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSONの配列である要素に対して付けます.
 * 
 * @author vvakame
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface JsonArray {
	public Class<?> value();
}