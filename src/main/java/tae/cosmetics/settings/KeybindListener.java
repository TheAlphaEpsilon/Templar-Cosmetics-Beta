package tae.cosmetics.settings;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeybindListener {

	@SubscribeEvent
	public void onKey(KeyInputEvent event) {
		for(Keybind keybind : Keybind.getKeybindCopy()) {
			if(keybind.isPressed()) {
				keybind.onPress();
			}
		}
	}
}
