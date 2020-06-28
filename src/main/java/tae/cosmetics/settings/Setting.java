package tae.cosmetics.settings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
			builder.append(setting.hashCode()).append("\n").append(bytesToString(setting.serializeValue())).append("\n");
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
	
	private static byte[] stringToBytes(String s) {
		
		String[] writtenBytes = s.split(",");
		byte[] bytes = new byte[writtenBytes.length];
		for(int i = 0; i < writtenBytes.length; i++) {
			bytes[i] = Byte.parseByte(writtenBytes[i]);
		}
		
		return bytes;
		
	}
	
	private static String bytesToString(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < bytes.length; i++) {
			builder.append(Byte.toString(bytes[i]));
			if(i != bytes.length - 1) {
				builder.append(',');
			}
		}
		return builder.toString();
	}
	
	private String name;
	private T value;
	
	public Setting (String name, T defaultVal) {
		this.name = name;
		this.value = defaultVal;
		
		if(settingValueMap.containsKey(hashCode())) {
			
			String serialized = settingValueMap.get(hashCode());
			
			T deserialized = deserializeFromBytes(stringToBytes(serialized));
			
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
	
	@SuppressWarnings("unchecked")
	private T deserializeFromBytes(byte[] bytes) {
		
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new ByteArrayInputStream(bytes));
			T obj = (T) ois.readObject();
			ois.close();
			return obj;
		} catch (Exception e) {
			new TAEModException(Setting.class, "Cannot deserialize setting " + name + ": " + e.getMessage()).post();
			return null;
		}
	}
	
	private byte[] serializeValue() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(stream);
			
			oos.writeObject(value);
			
			oos.close();
			
			return stream.toByteArray();
			
		} catch (Exception e) {
			new TAEModException(Setting.class, "Cannot serialize setting " + name + ": " + e.getMessage()).post();
			return new byte[0];
		}
	}

	
}