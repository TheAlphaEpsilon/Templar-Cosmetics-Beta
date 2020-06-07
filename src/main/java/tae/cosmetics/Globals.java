package tae.cosmetics;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public interface Globals {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static String colorcode = "\u00A7";
	
	public static String dataFromName = "https://api.mojang.com/users/profiles/minecraft/";
	
	public static String dataFromUUID = "https://api.mojang.com/user/profiles/%s/names";
	
	public static String textureFromUUID = "https://sessionserver.mojang.com/session/minecraft/profile/";
	
	public static String rebaneQueue = "https://rebane2001.com/queuepeek/data.json";
	
	public static String prioqueue = "https://api.2b2t.dev/prioq";

	public static String modPath = "tae.cosmetics.mods.*";
	
	public static String configPath = "config/taescosmeticsv2.cfg";
	
	default Set<ColorCode> stringToCode(String[] possiblecolors) {
		
		Set<ColorCode> returncolors = new HashSet<ColorCode>();
		
		for(String string : possiblecolors) {
			
			for(ColorCode code : ColorCode.values()) {
				
				if(code.toString().toLowerCase().equals(string.toLowerCase())) {
					returncolors.add(code);
					break;
				}
				
			}
			
		}
		
		return returncolors;
	
	}
	
}
