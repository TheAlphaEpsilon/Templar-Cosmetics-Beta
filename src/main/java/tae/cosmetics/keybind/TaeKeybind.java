package tae.cosmetics.keybind;

import org.lwjgl.input.Keyboard;

import tae.cosmetics.Globals;

public class TaeKeybind implements Globals {
	
	private int key;
	private boolean onlyInGame;
	
	public TaeKeybind(int key, boolean onlyInGame) {
		this.key = key;
		this.onlyInGame = onlyInGame;
	}
	
	public boolean isDown() {
		boolean isDown = Keyboard.isKeyDown(key);
		if(onlyInGame) {
			return isDown && mc.currentScreen == null;
		}
		return isDown;
	}
	
}
