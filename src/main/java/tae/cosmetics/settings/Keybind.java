package tae.cosmetics.settings;

import java.util.HashMap;
import java.util.HashSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import tae.cosmetics.exceptions.TAEModException;
import tae.cosmetics.util.FileHelper;

public class Keybind {

	private static final String fileName = "keybinds.txt";
	
	private static final HashSet<Keybind> keybinds = new HashSet<>();
	
	private static final HashMap<Integer, Integer> keyBindValueMap = new HashMap<>();
	
	static {
		FileHelper.createFile(fileName);
		load();
	}
	
	private static void load() {
		for(String s : FileHelper.readFile(fileName)) {
			String[] pair = s.split(":");
			try {
				keyBindValueMap.put(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
			} catch (Exception e) {
				new TAEModException(Keybind.class, "Cannot parse keybind: " + e.getMessage()).post();
			}
		}
	}
	
	public static void save() {
		StringBuilder builder = new StringBuilder();
		for(Keybind keybind : keybinds) {
			builder.append(keybind.hashCode()+":"+keybind.keyBinding.getKeyCode()+"\n");
		}
		FileHelper.overwriteFile(fileName, builder.toString());
	}
	
	public static HashSet<Keybind> getKeybindCopy() {
		return new HashSet<Keybind>(keybinds);
	}
	
	private KeyBinding keyBinding;
	private String name;
	private Runnable onPress;
	
	public Keybind(String name, int keyCode, Runnable onPress) {
		
		this.onPress = onPress;
		this.name = name;
		
		if(keyBindValueMap.containsKey(hashCode())) {
			keyCode = keyBindValueMap.get(hashCode());
		}
		
		keyBinding = new KeyBinding(name, keyCode, "Templar Cosmetics");
		
		keybinds.add(this);
		
	    ClientRegistry.registerKeyBinding(keyBinding);

	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	public void onPress() {
		onPress.run();
	}
	
	public void updateBinding(int keyCode) {
		keyBinding.setKeyCode(keyCode);
		KeyBinding.resetKeyBindingArrayAndHash();
	}
	
	public boolean isKeyDown() {
		return keyBinding.isKeyDown();
	}
	
	public boolean isPressed() {
		return keyBinding.isPressed();
	}
	
	public boolean isDownOverride() {
		return Keyboard.isKeyDown(keyBinding.getKeyCode());
	}
	
	public int getInt() {
		return keyBinding.getKeyCode();
	}
	
}
