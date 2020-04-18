package tae.cosmetics.exceptions;

public class InitException extends TAEModException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7010462662435379112L;

	public InitException(Class<?> mod) {
		super(mod);
	}

	public InitException(Class<?> mod, String s) {
		super(mod, s);
	}
}
