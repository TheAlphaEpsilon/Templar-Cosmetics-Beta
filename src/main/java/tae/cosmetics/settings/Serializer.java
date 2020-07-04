package tae.cosmetics.settings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import tae.cosmetics.exceptions.TAEModException;

public class Serializer {

	public static byte[] serializeValue(Object obj) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(stream);
			
			oos.writeObject(obj);
			
			oos.close();
			
			return stream.toByteArray();
			
		} catch (Exception e) {
			new TAEModException(Serializer.class, "Cannot serialize setting " + obj.toString() + ": " + e.getMessage()).post();
			return new byte[0];
		}
	}
		
	public static Object deserializeFromBytes(byte[] bytes) {
		
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new ByteArrayInputStream(bytes));
			Object obj = ois.readObject();
			ois.close();
			return obj;
		} catch (Exception e) {
			new TAEModException(Serializer.class, "Cannot deserialize setting: " + e.getMessage()).post();
			return null;
		}
	}
	
	public static byte[] stringToBytes(String s) {
		
		try{
			return Base64.getDecoder().decode(s);
		} catch (Exception e) {
			new TAEModException(Serializer.class, "Cannot convert from B64: " + e.getMessage()).post();
			return new byte[0];
		}
		
	}
	
	public static String bytesToString(byte[] bytes) {
		
		try{
			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			new TAEModException(Serializer.class, "Cannot convert to B64: " + e.getMessage()).post();
			return "";
		}
		
	}
	
}
