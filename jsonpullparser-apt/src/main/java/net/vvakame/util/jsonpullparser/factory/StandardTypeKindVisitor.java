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

import java.util.Date;
import java.util.List;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.TypeKindVisitor6;

import net.vvakame.apt.AptUtil;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

/**
 * A {@link TypeKindVisitor6} variant, which is also capable of recognizing {@link String}, {@link List}, {@link JsonHash} and {@link JsonArray}.
 * @author vvakame
 * @param <R>
 * @param <P>
 */
public class StandardTypeKindVisitor<R, P> extends TypeKindVisitor6<R, P> {

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitString(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitList(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitDate(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitEnum(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitBooleanWrapper(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitDoubleWrapper(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitLongWrapper(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitByteWrapper(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitCharacterWrapper(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitFloatWrapper(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitIntegerWrapper(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitShortWrapper(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitJsonHash(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitJsonArray(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * @param t
	 * @param p
	 * @return R
	 * @author vvakame
	 */
	public R visitUndefinedClass(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public R visitDeclared(DeclaredType t, P p) {
		final String fqn = t.asElement().toString();
		if (String.class.getCanonicalName().equals(fqn)) {
			return visitString(t, p);
		} else if (Date.class.getCanonicalName().equals(fqn)) {
			return visitDate(t, p);
		} else if (List.class.getCanonicalName().equals(fqn)) {
			return visitList(t, p);
		} else if (Boolean.class.getCanonicalName().equals(fqn)) {
			return visitBooleanWrapper(t, p);
		} else if (Double.class.getCanonicalName().equals(fqn)) {
			return visitDoubleWrapper(t, p);
		} else if (Long.class.getCanonicalName().equals(fqn)) {
			return visitLongWrapper(t, p);
		} else if (Byte.class.getCanonicalName().equals(fqn)) {
			return visitByteWrapper(t, p);
		} else if (Character.class.getCanonicalName().equals(fqn)) {
			return visitCharacterWrapper(t, p);
		} else if (Float.class.getCanonicalName().equals(fqn)) {
			return visitFloatWrapper(t, p);
		} else if (Integer.class.getCanonicalName().equals(fqn)) {
			return visitIntegerWrapper(t, p);
		} else if (Short.class.getCanonicalName().equals(fqn)) {
			return visitShortWrapper(t, p);
		} else if (JsonHash.class.getCanonicalName().equals(fqn)) {
			return visitJsonHash(t, p);
		} else if (JsonArray.class.getCanonicalName().equals(fqn)) {
			return visitJsonArray(t, p);
		} else if (AptUtil.isEnum(t.asElement())) {
			return visitEnum(t, p);
		} else {
			return visitUndefinedClass(t, p);
		}
	}
}
