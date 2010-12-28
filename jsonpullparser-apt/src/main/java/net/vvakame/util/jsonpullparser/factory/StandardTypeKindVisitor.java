package net.vvakame.util.jsonpullparser.factory;

import java.util.List;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.TypeKindVisitor6;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

public class StandardTypeKindVisitor<R, P> extends TypeKindVisitor6<R, P> {

	public R visitString(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	public R visitList(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	public R visitJsonHash(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	public R visitJsonArray(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	public R visitUndefinedClass(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public R visitDeclared(DeclaredType t, P p) {
		if (String.class.getCanonicalName().equals(t.asElement().toString())) {
			return visitString(t, p);
		} else if (List.class.getCanonicalName().equals(
				t.asElement().toString())) {
			return visitList(t, p);
		} else if (JsonHash.class.getCanonicalName().equals(
				t.asElement().toString())) {
			return visitJsonHash(t, p);
		} else if (JsonArray.class.getCanonicalName().equals(
				t.asElement().toString())) {
			return visitJsonArray(t, p);
		} else {
			return visitUndefinedClass(t, p);
		}
	}
}
