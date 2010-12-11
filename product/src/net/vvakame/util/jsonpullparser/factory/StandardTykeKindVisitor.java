package net.vvakame.util.jsonpullparser.factory;

import java.util.List;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.TypeKindVisitor6;

public class StandardTykeKindVisitor<R, P> extends TypeKindVisitor6<R, P> {

	ClassLoader loader = getClass().getClassLoader();

	public R visitString(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	public R visitList(DeclaredType t, P p) {
		return defaultAction(t, p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public R visitDeclared(DeclaredType t, P p) {
		Class<?> clazz;
		try {
			clazz = loader.loadClass(t.asElement().toString());
		} catch (ClassNotFoundException e) {
			Log.e(e);
			return null;
		}

		if (String.class == clazz) {
			return visitString(t, p);
		} else if (List.class == clazz) {
			return visitList(t, p);
		}

		return super.visitDeclared(t, p);
	}
}
