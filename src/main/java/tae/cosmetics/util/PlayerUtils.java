package tae.cosmetics.util;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.text.TextComponentString;
import tae.cosmetics.ColorCode;
import tae.cosmetics.Globals;

public class PlayerUtils implements Globals {

	public static void sendMessage(String message, ColorCode... code) {
		if(mc.player == null) return;
		if(message == null) return;
		
		Set<ColorCode> codes = new HashSet<ColorCode>();
		for(ColorCode in : code) {
			codes.add(in);
		}
		
		String format = organizeColorCodes(codes);
		
		mc.player.sendMessage(new TextComponentString(format+message));
	}
	
	private static String organizeColorCodes(Set<ColorCode> codes) {
		String format = "";
		String color = "";
		
		for(ColorCode in : codes) {
			if(in.getType() == 0) color += in.getCode();
			else format += in.getCode();
		}
		
		return color+format;
	}
	
}
