package tae.cosmetics.guiscreen.render;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tae.cosmetics.ColorCode;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.guiscreen.AbstractTAEGuiScreen;
import tae.cosmetics.guiscreen.GuiHome;
import tae.cosmetics.guiscreen.GuiScreenRendering;
import tae.cosmetics.guiscreen.button.GuiDisplayListButton;
import tae.cosmetics.guiscreen.button.GuiOnOffButton;
import tae.cosmetics.mods.TimeStampMod;
import tae.cosmetics.mods.UnicodeKeyboard;
import tae.cosmetics.mods.UnicodeKeyboard.TextType;
import tae.cosmetics.settings.Keybind;

public class GuiTimestampMod extends AbstractTAEGuiScreen {

	public static final Keybind openGui = new Keybind("Open Timestamp Mod",0, () -> {
		AbstractTAEGuiScreen.displayScreen(new GuiTimestampMod(new GuiScreenRendering(new GuiHome())));
	});

	private static final ResourceLocation TIMESTAMP_GUI = new ResourceLocation("taecosmetics","textures/gui/timestamp.png");
	
    private static final String sign = UnicodeKeyboard.toUnicode("TEMPLAR", TextType.SMALL);
	
	private GuiOnOffButton date;
	private GuiOnOffButton hour24;
	private GuiOnOffButton enabled;
	
	private GuiOnOffButton bold;
	private GuiOnOffButton italic;
	private GuiOnOffButton underline;
	
	private GuiDisplayListButton<ColorCode> colors;
			
    private Color taeTextColor = new Color(0, 255, 0, 255);
	
	public GuiTimestampMod(GuiScreen parent) {
		
		super(parent);
	
		override = true;
		
		date = new GuiOnOffButton(0, 0, 0, 80, 20, "Date: ", TimeStampMod.date.getValue(), "Toggle Date", 1);
		hour24 = new GuiOnOffButton(1, 0, 0, 80, 20, "24 Hour: ", TimeStampMod.hour24.getValue(), "Toggle 24h", 1);
		enabled = new GuiOnOffButton(2, 0, 0, 80, 20, "Enabled: ", TimeStampMod.enabled.getValue(), "Enable", 1);
		
		bold = new GuiOnOffButton(3, 0, 0, 80, 20, "Bold: ", TimeStampMod.bold.getValue(), "Toggle Bold", 1);
		italic = new GuiOnOffButton(4, 0, 0, 80, 20, "Italic: ", TimeStampMod.italic.getValue(), "Toggle Italic", 1);
		underline = new GuiOnOffButton(5, 0, 0, 80, 20, "Line: ", TimeStampMod.underline.getValue(), "Toggle Underline", 1);
		
		ArrayList<ColorCode> toList = new ArrayList<ColorCode>();
		
		//Only list colors
		
		for(ColorCode code : ColorCode.values()) {
			if(code.getType() == 0) {
				toList.add(code);
			}
		}
		
		colors = new GuiDisplayListButton<ColorCode>(6, 0, 0, 80, 20, toList, TimeStampMod.code.getValue(), "Iterate through colors", 1);
		
		settingsScreen = new AbstractTAEGuiScreen(parent) {

        	{
        		guiwidth = 200;
        		guiheight = 100;
        	}
        	
        	private GuiSetKeybind openGuiKeyBind = null;
        	private String keyBindInfo = "Keybind to open timestamp mod";
        	
        	@Override
        	public void initGui() {
        		if(openGuiKeyBind == null) {
        			openGuiKeyBind = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, openGui.getInt());
        			openGuiKeyBind.setTextColor(-1);
        			openGuiKeyBind.setDisabledTextColour(-1);
        			openGuiKeyBind.setEnableBackgroundDrawing(true);
        			openGuiKeyBind.setMaxStringLength(32);
        			openGuiKeyBind.setCanLoseFocus(true);
        		}
        		super.initGui();
        	}
        	
			@Override
			public void onGuiClosed() {
				Keyboard.enableRepeatEvents(false);
				
				openGui.updateBinding(openGuiKeyBind.getKeyCode());
								
				mc.gameSettings.saveOptions();
			}
			
			@Override
			protected void keyTyped(char typedChar, int keyCode) throws IOException {
				if (openGuiKeyBind.textboxKeyTyped(typedChar, keyCode)) {
		        } else {	
		            super.keyTyped(typedChar, keyCode);
		        }
		    }
			
			@Override
			protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
				super.mouseClicked(mouseX, mouseY, mouseButton);
				if(openGuiKeyBind.mouseClicked(mouseX, mouseY, mouseButton)) {
				} 				
			}

			@Override
			protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
				
				int i = width / 2;
				int j = height / 2;
				
				openGuiKeyBind.drawTextBox();
				
				this.drawCenteredString(fontRenderer, keyBindInfo, i, j - 20, Color.WHITE.getRGB());
			}

			@Override
			protected void updateButtonPositions(int x, int y) {
				
				openGuiKeyBind.x = x - openGuiKeyBind.width / 2;
				openGuiKeyBind.y = y - 6;
				
				back.x = x - back.width / 2;
				back.y = y + 16;
			}
        	
        };
	}
	
	@Override
	public void initGui() {
		buttonList.add(hour24);
		buttonList.add(enabled);
		buttonList.add(italic);
		
		buttonList.add(date);
		buttonList.add(bold);
		buttonList.add(underline);
		
		buttonList.add(colors);
				
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button instanceof GuiOnOffButton) {
			((GuiOnOffButton) button).changeStateOnClick();
		} else if(button instanceof GuiDisplayListButton) {
			((GuiDisplayListButton<?>) button).changeStateOnClick();
		}
		
		if(button == date) {
			TimeStampMod.date.setValue(date.getValue());
		} else if(button == hour24) {
			TimeStampMod.hour24.setValue(hour24.getValue());
		} else if(button == enabled) {
			TimeStampMod.enabled.setValue(enabled.getValue());
		} else if(button == bold) {
			TimeStampMod.bold.setValue(bold.getValue());
		} else if(button == italic) {
			TimeStampMod.italic.setValue(italic.getValue());
		} else if(button == underline) {
			TimeStampMod.underline.setValue(underline.getValue());
		} else if(button == colors) {
			TimeStampMod.code.setValue(colors.getValue());
		}
		
		super.actionPerformed(button);
	}
	
	@Override
	public void drawScreen0(int mouseX, int mouseY, float partialTicks) {
		
		updateTextColor();
		
		int i = width / 2;
		int j = height / 2;
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		mc.getTextureManager().bindTexture(TIMESTAMP_GUI);
		this.drawTexturedModalRect(i - 121, j - 110, 0, 0, 243, 216);
		
		this.drawCenteredString(fontRenderer, TimeStampMod.getFormattedTimeAsString(), i, j + 51, Color.WHITE.getRGB());
		
		this.drawString(fontRenderer, sign, i + 90, j + 90, taeTextColor.getRGB());
				
	}
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	@Override
	protected void updateButtonPositions(int x, int y) {
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
		
		back.x = x + back.width / 2;
		back.y = y + 70;
		
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

	@Override
	public void onGuiClosed() {
	}
	
}
