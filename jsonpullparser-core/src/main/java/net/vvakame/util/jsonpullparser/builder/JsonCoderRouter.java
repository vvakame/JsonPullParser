package net.vvakame.util.jsonpullparser.builder;

/**
 * Router for JsonModelCoder that routed by model. 
 * @author vvakame
 * @param <T>
 */
public abstract class JsonCoderRouter<T> {

	/**
	 * Resolve coder by model.
	 * @param obj
	 * @return custom coder, can't return null.
	 * @author vvakame
	 */
	public abstract JsonModelCoder<T> resolve(T obj);

	/**
	 * Call {@link #resolve(Object)} and checl not null.
	 * @param obj
	 * @return custom coder
	 * @author vvakame
	 */
	public JsonModelCoder<T> doResolve(T obj) {
		JsonModelCoder<T> coder = resolve(obj);
		if (coder == null) {
			throw new NullPointerException("resolve method must return coder.");
		}
		return coder;
	}
}
