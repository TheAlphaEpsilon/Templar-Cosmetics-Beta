package tae.cosmetics.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import tae.cosmetics.exceptions.TAEModException;
import tae.cosmetics.util.FileHelper;

public class Setting <T extends Serializable> {

	private static final String fileName = "config.txt";
	
	private static final HashSet<Setting<?>> settings = new HashSet<>();
	private static final HashMap<Integer, String> settingValueMap = new HashMap<>();
	
	static {
		FileHelper.createFile(fileName);
		load();
	}
	
	public static void save() {
		StringBuilder builder = new StringBuilder();
		for(Setting<?> setting : settings) {
			builder.append(setting.hashCode()).append("\n").append(Serializer.bytesToString(Serializer.serializeValue(setting.value))).append("\n");
		}
		FileHelper.overwriteFile(fileName, builder.toString());
	}
	
	private static void load() {
		ArrayList<String> lines = FileHelper.readFile(fileName);
		for(int i = 0; i < lines.size() / 2; i++) {
			try{
				settingValueMap.put(Integer.parseInt(lines.get(i * 2)), lines.get(i * 2 + 1));
			} catch (Exception e) {
				new TAEModException(Setting.class, "Cannot load settings: " + e.getMessage());
			}
		}
	}
	
	private String name;
	private T value;
	
	public Setting (String name, T defaultVal) {
		this.name = name;
		this.value = defaultVal;
		
		if(settingValueMap.containsKey(hashCode())) {
			
			String serialized = settingValueMap.get(hashCode());
			
			@SuppressWarnings("unchecked")
			T deserialized = (T) Serializer.deserializeFromBytes(Serializer.stringToBytes(serialized));
			
			if(deserialized != null) {
				value = deserialized;
			}
			
		} 
		settings.add(this);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
}