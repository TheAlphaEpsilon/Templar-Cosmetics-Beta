package tae.cosmetics.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tae.cosmetics.ColorCode;
import tae.cosmetics.gui.util.GuiDisplayListButton;
import tae.cosmetics.gui.util.GuiOnOffButton;
import tae.cosmetics.mods.TimeStampMod;
import tae.cosmetics.mods.UnicodeKeyboard;
import tae.cosmetics.mods.UnicodeKeyboard.TextType;

public class GuiTimestampMod extends GuiScreen {

	private static final ResourceLocation TIMESTAMP_GUI = new ResourceLocation("taecosmetics","textures/gui/timestamp.png");
	
    private static final String sign = UnicodeKeyboard.toUnicode("TEMPLAR", TextType.SMALL);
	
	private GuiOnOffButton date;
	private GuiOnOffButton hour24;
	private GuiOnOffButton enabled;
	
	private GuiOnOffButton bold;
	private GuiOnOffButton italic;
	private GuiOnOffButton underline;
	
	private GuiDisplayListButton<ColorCode> colors;
		
	private static GuiTimestampMod INSTANCE = null;
	
    private Color taeTextColor = new Color(0, 255, 0, 255);
	
	private static final int xOffset = 0;
	private static final int yOffset = 0;
	
	public static GuiTimestampMod instance() {
		if(INSTANCE == null) {
			return new GuiTimestampMod();
		} else {
			return INSTANCE;
		}
	}
	
	private GuiTimestampMod() {
		date = new GuiOnOffButton(0, 0, 0, 80, 20, "Date: ", !TimeStampMod.noDate);
		hour24 = new GuiOnOffButton(1, 0, 0, 80, 20, "24 Hour: ", TimeStampMod.hour24);
		enabled = new GuiOnOffButton(2, 0, 0, 80, 20, "Enabled: ", TimeStampMod.enabled);
		
		bold = new GuiOnOffButton(3, 0, 0, 80, 20, "Bold: ", !TimeStampMod.nobold);
		italic = new GuiOnOffButton(4, 0, 0, 80, 20, "Italic: ", !TimeStampMod.noitalic);
		underline = new GuiOnOffButton(5, 0, 0, 80, 20, "Line: ", !TimeStampMod.nounderline);
		
		ArrayList<ColorCode> toList = new ArrayList<ColorCode>();
		
		//Only list colors
		
		for(ColorCode code : ColorCode.values()) {
			if(code.getType() == 0) {
				toList.add(code);
			}
		}
		
		colors = new GuiDisplayListButton<ColorCode>(6, 0, 0, 80, 20, toList, TimeStampMod.code);
		
	}
	
	@Override
	public void initGui() {
		buttonList.add(date);
		buttonList.add(hour24);
		buttonList.add(enabled);
		
		buttonList.add(bold);
		buttonList.add(italic);
		buttonList.add(underline);
		
		buttonList.add(colors);
				
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button instanceof GuiOnOffButton) {
			((GuiOnOffButton) button).toggle();
		} else if(button instanceof GuiDisplayListButton) {
			((GuiDisplayListButton<?>) button).nextValue();
		}
		
		if(button == date) {
			TimeStampMod.noDate = !date.getState();
		} else if(button == hour24) {
			TimeStampMod.hour24 = hour24.getState();
		} else if(button == enabled) {
			TimeStampMod.enabled = enabled.getState();
		} else if(button == bold) {
			TimeStampMod.nobold = !bold.getState();
		} else if(button == italic) {
			TimeStampMod.noitalic = !italic.getState();
		} else if(button == underline) {
			TimeStampMod.nounderline = !underline.getState();
		} else if(button == colors) {
			TimeStampMod.code = colors.getValue();
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		updateTextColor();
		
		int i = width / 2 + xOffset;
		int j = height / 2 + yOffset;
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		mc.getTextureManager().bindTexture(TIMESTAMP_GUI);
		this.drawTexturedModalRect(i - 121, j - 110, 0, 0, 243, 216);
		
		this.drawCenteredString(fontRenderer, TimeStampMod.getFormattedTimeAsString(), i, j + 51, Color.WHITE.getRGB());
		
		this.drawString(fontRenderer, sign, i + 90, j + 90, taeTextColor.getRGB());
		
		updateButtonLocations(i, j);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	private void updateButtonLocations(int x, int y) {
		date.x = x - 96;
		date.y = y - 83;
		
		hour24.x = x + 17;
		hour24.y = y - 83;
		
		bold.x = x - 96;
		bold.y = y - 50;
		
		italic.x = x + 17;
		italic.y = y - 50;
		
		underline.x = x - 96;
		underline.y = y - 19;
		
		enabled.x = x - 39;
		enabled.y = y + 16;
		
		colors.x = x + 17;
		colors.y = y - 19;
		
	}
	
	 private void updateTextColor() {
			int red = taeTextColor.getRed();
			int green = taeTextColor.getGreen();
			int blue = taeTextColor.getBlue();
			
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
			
			taeTextColor = new Color(red, green, blue, 255);
			
		}
	    
	
}
