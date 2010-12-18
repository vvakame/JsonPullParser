package net.vvakame.util.jsonpullparser;

/**
 * JSONとして構文的に誤りがあった場合に投げられる.
 * 
 * @author vvakame
 */
public class JsonFormatException extends Exception {
	private static final long serialVersionUID = -1877852218539180703L;

	/**
	 * {@inheritDoc}
	 */
	public JsonFormatException() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public JsonFormatException(String msg) {
		super(msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public JsonFormatException(Throwable e) {
		super(e);
	}
}
