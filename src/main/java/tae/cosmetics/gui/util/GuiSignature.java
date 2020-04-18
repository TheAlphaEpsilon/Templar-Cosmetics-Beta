package tae.cosmetics.gui.util;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import tae.cosmetics.Globals;
import tae.cosmetics.mods.UnicodeKeyboard;
import tae.cosmetics.mods.UnicodeKeyboard.TextType;

public class GuiSignature extends Gui implements Globals{

    private static final String sign = UnicodeKeyboard.toUnicode("TEMPLAR", TextType.SMALL);
    private static Color color = new Color(255, 0, 0);
    
    public static void draw(int x, int y) {
    	updateTextColor();
    	mc.fontRenderer.drawString(sign, x, y, color.getRGB());
    }
   
    private static void updateTextColor() {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		
		if(blue == 255) {
			if(green == 0) {
				red+=5;
			}
			if(red == 0) {
				green-=5;
			}
		}
		if(red == 255) {
			if(blue == 0) {
				green+=5;
			}
			if(green == 0) {
				blue-=5;
			}
		}
		if(green == 255) {
			if(red == 0) {
				blue+=5;
			}
			if(blue == 0) {
				red-=5;
			}
		}
		
		color = new Color(red, green, blue);
	}
    
}
