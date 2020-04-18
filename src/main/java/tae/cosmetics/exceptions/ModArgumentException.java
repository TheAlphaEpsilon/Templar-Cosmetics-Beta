package tae.cosmetics.exceptions;

public class ModArgumentException extends TAEModException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6741444896157788784L;

	public ModArgumentException(Class<?> mod) {
		super(mod);
	}

	public ModArgumentException(Class<?> mod, String s) {
		super(mod, s);
	}
}
