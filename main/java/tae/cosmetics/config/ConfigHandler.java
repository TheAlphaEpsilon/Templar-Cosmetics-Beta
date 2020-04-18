package tae.cosmetics.config;

import java.io.File;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.Configuration;
import tae.cosmetics.ColorCode;
import tae.cosmetics.Globals;
import tae.cosmetics.OnLogin;
import tae.cosmetics.exceptions.TAEModException;

public class ConfigHandler implements Globals {
	public static Configuration config;
	private static String file = configPath;
	
	public static String[] getStringMap(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if(config.getCategory(category).containsKey(key)) {
				String raw = config.get(category, key, "").getString();
				int firstlength = raw.charAt(0) - 32;
				String first = raw.substring(1,firstlength+1);
				String second = raw.substring(firstlength+1);
				String[] toReturn = new String[2];
				toReturn[0] = first;
				toReturn[1] = second;
				return toReturn;
			}
		} catch (Exception e) {
			OnLogin.addError(new TAEModException(ConfigHandler.class,"Cannot access config file"));
		} finally {
			config.save();
		}
		return new String[] {"",""};
	}
	
	public static void writeStringMap(String category, String key, String mapkey, String toput) {
		config = new Configuration(new File(file));
		try {
			config.load();
			
			char code = (char) (mapkey.length() + 32);
			
			String value = code+mapkey+toput;
			
			config.get(category, key, value).getString();
			config.getCategory(category).get(key).set(value);
		} catch (Exception e) {
			staticSendMessage("Cannot write to config file",ColorCode.RED);
		} finally {
			config.save();
		}
	}
	
	public static String[] getPlayerAlert(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if(config.getCategory(category).containsKey(key)) {
				String raw = config.get(category, key, "").getString();
				int firstlength = raw.charAt(0) - 32;
				int secondlength = raw.charAt(1) - 32;
				int thirdlength = raw.charAt(2) - 32;
				
				String first = raw.substring(3,firstlength+3);
				String second = raw.substring(firstlength+3,secondlength + firstlength + 3);
				String third = raw.substring(secondlength + firstlength + 3, thirdlength + secondlength + firstlength + 3);
				String alertbool = raw.substring(thirdlength + secondlength + firstlength + 3, thirdlength + secondlength + firstlength + 4);
				String queuebool = raw.substring(thirdlength + secondlength + firstlength + 4, thirdlength + secondlength + firstlength + 5);
				String prio = raw.substring(thirdlength + secondlength + firstlength + 5, thirdlength + secondlength + firstlength + 6);
				
				String[] toReturn = new String[6];
				
				toReturn[0] = first;
				toReturn[1] = second;
				toReturn[2] = third;
				toReturn[3] = alertbool;
				toReturn[4] = queuebool;
				toReturn[5] = prio;
				
				return toReturn;
			}
		} catch (Exception e) {
			OnLogin.addError(new TAEModException(ConfigHandler.class,"Cannot access config file"));
		} finally {
			config.save();
		}
		return new String[] {"",""};
	}
	
	public static void writePlayerAlert(String category, String key, String mapkey, String oldname, String prefix, boolean alert, boolean queue, boolean assumePrio) {
		config = new Configuration(new File(file));
		try {
			config.load();
			
			char keylength = (char) (32 + mapkey.length());
			char namelength = (char) (32 + oldname.length());
			char prefixlength = (char) (32 + prefix.length());
			
			String value = keylength + "" + namelength +""+ prefixlength + mapkey + oldname + prefix + (alert ? 1 : 0) + (queue ? 1 : 0) + (assumePrio ? 1 : 0);
			
			config.get(category, key, value).getString();
			config.getCategory(category).get(key).set(value);
		} catch (Exception e) {
			staticSendMessage("Cannot write to config file",ColorCode.RED);
		} finally {
			config.save();
		}
	}
	
	public static int getInt(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, 0).getInt();
			}
		} catch (Exception e) {
			OnLogin.addError(new TAEModException(ConfigHandler.class,"Cannot access config file"));
		} finally {
			config.save();
		}
		return -1;
	}
	
	public static void writeInt(String category, String key, int value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			config.get(category, key, value).getInt();
			config.getCategory(category).get(key).set(value);
		} catch (Exception e) {
			staticSendMessage("Cannot write to config file",ColorCode.RED);
		} finally {
			config.save();
		}
	}
	
	public static boolean getBoolean(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key))
				return config.get(category, key, false).getBoolean();
		} catch (Exception e) {
			OnLogin.addError(new TAEModException(ConfigHandler.class,"Cannot access config file"));
		} finally {
			config.save();
		}
		return true;
	}
	
	public static void writeBoolean(String category, String key, boolean value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			config.get(category, key, value).getBoolean();
			config.getCategory(category).get(key).set(value);
		} catch (Exception e) {
			staticSendMessage("Cannot write to config file",ColorCode.RED);
		} finally {
			config.save();
		}
	}
	
	private static void staticSendMessage(String message, ColorCode... code) {
		if(mc.player == null) return;
		if(message == null) return;
		String format = "";
		String color = "";
		
		for(ColorCode in : code) {
			if(in.getType() == 0) color += in.getCode();
			else format += in.getCode();
		}
		
		mc.player.sendMessage(new TextComponentString(color+format+message));
	}
	
}