package tae.cosmetics.exceptions;

import tae.cosmetics.ColorCode;
import tae.cosmetics.Globals;
import tae.cosmetics.OnLogin;
import tae.cosmetics.util.PlayerUtils;

public class TAEModException extends Exception implements Globals {

	private static final long serialVersionUID = -1L;

	private Class<?> mod;
	private String message = "";
	
	public TAEModException(Class<?> mod) {
		super();
		this.mod = mod;
	}

	public TAEModException(Class<?> mod, String s) {
		super(s);
		if(s != null) {
			message = s;
		}
		this.mod = mod;
		
		if(mc.player == null) {
			OnLogin.addError(this);
		}
		
	}
	
	public String getClassName() {
		return mod.getName();
	}
	
	public void post() {
		PlayerUtils.sendMessage("Error: Class " + mod.toString() + " : " + message,ColorCode.RED);
	}
	
}
