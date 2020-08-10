package tae.cosmetics.guiscreen;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Tuple;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.guiscreen.button.AbstractTAEButton;
import tae.cosmetics.guiscreen.button.GuiGenericButton;
import tae.cosmetics.guiscreen.button.GuiOnOffButton;
import tae.cosmetics.guiscreen.button.OptionsButton;
import tae.cosmetics.guiscreen.utilities.BookArt;
import tae.cosmetics.guiscreen.utilities.GuiBookTitleMod;
import tae.cosmetics.guiscreen.utilities.GuiScreenStalkerMod;
import tae.cosmetics.mods.BaseMod;
import tae.cosmetics.mods.ChatEncryption;
import tae.cosmetics.mods.PearlTracking;

public class GuiScreenUtilities extends AbstractTAEGuiScreen {

	private ArrayList<Tuple<AbstractTAEButton<?>, OptionsButton>> modSets = new ArrayList<>();
	
	private GuiGenericButton openBookTitle = new GuiGenericButton(0, 0, 0, 150, 20, "Open Book Title Mod", "Make colored and unicode titles", 1);
	private OptionsButton bookTitleOptions = new OptionsButton(1, 0, 0, 20, 20, "", "Book Title Options", 1);
	
	private GuiGenericButton openStalkerMod = new GuiGenericButton(2, 0, 0, 150, 20, "Open Stalker Mod", "Track Queue Positions and Other", 1);
	private OptionsButton stalkerModOptions = new OptionsButton(3, 0, 0, 20, 20, "", "Stalker Mod Options", 1);
	
	private GuiGenericButton openBookArt = new GuiGenericButton(4, 0, 0, 150, 20, "Open Book Art", "Make pixel art in books", 1);
	private OptionsButton bookArtOptions = new OptionsButton(5, 0, 0, 20, 20, "", "Book Art Options", 1);
	
	private GuiOnOffButton togglePearlLogging = new GuiOnOffButton(6, 0, 0, 150, 20, "Pearl Logging: ", PearlTracking.enabled.getValue(), "Log Pearls to .minecraft", 1);
	private OptionsButton pearlLoggingOptions = new OptionsButton(7, 0, 0, 20, 20, "", "Pearl Logging Options", 1);
	
	private GuiOnOffButton sendEncryptedChat = new GuiOnOffButton(8, 0, 0, 150, 20, "Encrypted Chat: ", ChatEncryption.enabled.getValue(), "Send \"Encrypted\" Chat", 1);
	private OptionsButton encryptedChatOptions = new OptionsButton(9, 0, 0, 20, 20, "", "Encrypted Chat Options", 1);
	
	private GuiGenericButton sendElytraPacket = new GuiGenericButton(10, 0, 0, 150, 20, "Send Elytra Packet", "Same as double space", 1);
	private OptionsButton elytraPacketOptions = new OptionsButton(11, 0, 0, 20, 20, "", "Elytra Packet Options", 1);
	
	public GuiScreenUtilities(GuiScreen parent) {
		super(parent);
		modSets.add(new Tuple<>(openBookTitle, bookTitleOptions));
		modSets.add(new Tuple<>(openStalkerMod, stalkerModOptions));
		modSets.add(new Tuple<>(openBookArt, bookArtOptions));
		modSets.add(new Tuple<>(togglePearlLogging, pearlLoggingOptions));
		modSets.add(new Tuple<>(sendEncryptedChat, encryptedChatOptions));
		modSets.add(new Tuple<>(sendElytraPacket, elytraPacketOptions));
	}
	
	@Override
	public void initGui() {
		
		if(!togglePearlLogging.getValue().equals(PearlTracking.enabled.getValue())) {
			togglePearlLogging.changeStateOnClick();
		}
		
		if(!sendEncryptedChat.getValue().equals(ChatEncryption.enabled.getValue())) {
			sendEncryptedChat.changeStateOnClick();
		}
		
		for(Tuple<AbstractTAEButton<?>, OptionsButton> tuple : modSets) {
			buttonList.add(tuple.getSecond());
			buttonList.add(tuple.getFirst());
		}
		super.initGui();
	}

	@Override
	public void onGuiClosed() {		
	}

	@Override
	protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
		
		int i = width / 2;
		int j = height / 2;
		
		this.drawCenteredString(fontRenderer, "General Utilities", i, j - 82, Color.WHITE.getRGB());
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		if(button instanceof AbstractTAEButton) {
			((AbstractTAEButton<?>) button).changeStateOnClick();
		}
		
		if(button == openBookTitle) {
			displayScreen(new GuiBookTitleMod());
		} else if(button == bookTitleOptions) {
			displayScreen(new GuiBookTitleMod().settingsScreen);
		} else if(button == openStalkerMod) {
			displayScreen(new GuiScreenStalkerMod(this));
		} else if(button == stalkerModOptions) {
			displayScreen(new GuiScreenStalkerMod(this).settingsScreen);
		} else if(button == openBookArt) {
			displayScreen(new BookArt(this));
		} else if(button == bookArtOptions) {
			displayScreen(new BookArt(this).settingsScreen);
		} else if(button == togglePearlLogging) {
			PearlTracking.enabled.setValue(((GuiOnOffButton)button).getValue());
		} else if(button == pearlLoggingOptions) {
			displayScreen(new AbstractTAEGuiScreen(this) {

		    	{
		    		guiwidth = 200;
		    		guiheight = 100;
		    	}
		    	
		    	private GuiSetKeybind toggleKeyBind = null;
		    	private String keyBindInfo = "Keybind to toggle pearl tracking";
		    	
		    	@Override
		    	public void initGui() {
		    		if(toggleKeyBind == null) {
		    			toggleKeyBind = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, PearlTracking.toggle.getInt());
		    			toggleKeyBind.setTextColor(-1);
		    			toggleKeyBind.setDisabledTextColour(-1);
		    			toggleKeyBind.setEnableBackgroundDrawing(true);
		    			toggleKeyBind.setMaxStringLength(32);
		    			toggleKeyBind.setCanLoseFocus(true);
		    		}
		    		super.initGui();
		    	}
		    	
				@Override
				public void onGuiClosed() {
					Keyboard.enableRepeatEvents(false);
					
					PearlTracking.toggle.updateBinding(toggleKeyBind.getKeyCode());
									
					mc.gameSettings.saveOptions();
				}
				
				@Override
				protected void keyTyped(char typedChar, int keyCode) throws IOException {
					if (toggleKeyBind.textboxKeyTyped(typedChar, keyCode)) {
			        } else {	
			            super.keyTyped(typedChar, keyCode);
			        }
			    }
				
				@Override
				protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
					super.mouseClicked(mouseX, mouseY, mouseButton);
					if(toggleKeyBind.mouseClicked(mouseX, mouseY, mouseButton)) {
					} 				
				}

				@Override
				protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
					
					int i = width / 2;
					int j = height / 2;
					
					toggleKeyBind.drawTextBox();
					
					this.drawCenteredString(fontRenderer, keyBindInfo, i, j - 20, Color.WHITE.getRGB());
				}

				@Override
				protected void updateButtonPositions(int x, int y) {
					
					toggleKeyBind.x = x - toggleKeyBind.width / 2;
					toggleKeyBind.y = y - 6;
					
					back.x = x - back.width / 2;
					back.y = y + 16;
				}
		    	
		    });
		} else if(button == sendEncryptedChat) {
			ChatEncryption.enabled.setValue(((GuiOnOffButton)button).getValue());
		} else if(button == encryptedChatOptions) {
			displayScreen(new AbstractTAEGuiScreen(this) {

		    	{
		    		guiwidth = 200;
		    		guiheight = 150;
		    	}
		    	
		    	private GuiSetKeybind toggleKeyBind = null;
		    	private String keyBindInfo = "Keybind to toggle chat encryption";
		    	private GuiOnOffButton showRawButton = new GuiOnOffButton(0, 0, 0, 150, 20, "Show Raw Chat: ", ChatEncryption.showRaw.getValue(), "See the encrypted chat", 1);
		    	
		    	@Override
		    	public void initGui() {
		    		if(toggleKeyBind == null) {
		    			toggleKeyBind = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, ChatEncryption.toggle.getInt());
		    			toggleKeyBind.setTextColor(-1);
		    			toggleKeyBind.setDisabledTextColour(-1);
		    			toggleKeyBind.setEnableBackgroundDrawing(true);
		    			toggleKeyBind.setMaxStringLength(32);
		    			toggleKeyBind.setCanLoseFocus(true);
		    		}
		    		
		    		if(!showRawButton.getValue().equals(ChatEncryption.showRaw.getValue())) {
		    			showRawButton.changeStateOnClick();
		    		}
		    		
		    		buttonList.add(showRawButton);
		    		super.initGui();
		    	}
		    	
				@Override
				public void onGuiClosed() {
					Keyboard.enableRepeatEvents(false);
					
					ChatEncryption.toggle.updateBinding(toggleKeyBind.getKeyCode());
									
					mc.gameSettings.saveOptions();
				}
				
				@Override
				protected void keyTyped(char typedChar, int keyCode) throws IOException {
					if (toggleKeyBind.textboxKeyTyped(typedChar, keyCode)) {
			        } else {	
			            super.keyTyped(typedChar, keyCode);
			        }
			    }
				
				@Override
				protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
					super.mouseClicked(mouseX, mouseY, mouseButton);
					if(toggleKeyBind.mouseClicked(mouseX, mouseY, mouseButton)) {
					} 				
				}

				@Override
				protected void actionPerformed(GuiButton button) throws IOException {
					
					if(button instanceof AbstractTAEButton) {
						((AbstractTAEButton<?>) button).changeStateOnClick();
					}
					
					if(button == showRawButton) {
						ChatEncryption.showRaw.setValue(showRawButton.getValue());
					}
					
					super.actionPerformed(button);
					
				}
				
				@Override
				protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
					
					int i = width / 2;
					int j = height / 2;
					
					toggleKeyBind.drawTextBox();
					
					this.drawCenteredString(fontRenderer, keyBindInfo, i, j - 30, Color.WHITE.getRGB());
				}

				@Override
				protected void updateButtonPositions(int x, int y) {
					
					toggleKeyBind.x = x - toggleKeyBind.width / 2;
					toggleKeyBind.y = y - 16;
					
					showRawButton.x = x - showRawButton.width / 2;
					showRawButton.y = y + 10;
					
					back.x = x - back.width / 2;
					back.y = y + 32;
				}
		    	
		    });
		} else if(button == sendElytraPacket || button == elytraPacketOptions) {
			displayScreen(new AbstractTAEGuiScreen(this) {

				{
		    		guiwidth = 200;
		    		guiheight = 100;
		    	}
		    	
		    	private GuiSetKeybind toggleKeyBind = null;
		    	private String keyBindInfo = "Keybind to send elytra packet";
		    	
		    	@Override
		    	public void initGui() {
		    		if(toggleKeyBind == null) {
		    			toggleKeyBind = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, BaseMod.elytra.getInt());
		    			toggleKeyBind.setTextColor(-1);
		    			toggleKeyBind.setDisabledTextColour(-1);
		    			toggleKeyBind.setEnableBackgroundDrawing(true);
		    			toggleKeyBind.setMaxStringLength(32);
		    			toggleKeyBind.setCanLoseFocus(true);
		    		}
		    		super.initGui();
		    	}
		    	
				@Override
				public void onGuiClosed() {
					Keyboard.enableRepeatEvents(false);
					
					BaseMod.elytra.updateBinding(toggleKeyBind.getKeyCode());
									
					mc.gameSettings.saveOptions();
				}
				
				@Override
				protected void keyTyped(char typedChar, int keyCode) throws IOException {
					if (toggleKeyBind.textboxKeyTyped(typedChar, keyCode)) {
			        } else {	
			            super.keyTyped(typedChar, keyCode);
			        }
			    }
				
				@Override
				protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
					super.mouseClicked(mouseX, mouseY, mouseButton);
					if(toggleKeyBind.mouseClicked(mouseX, mouseY, mouseButton)) {
					} 				
				}

				@Override
				protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
					
					int i = width / 2;
					int j = height / 2;
					
					toggleKeyBind.drawTextBox();
					
					this.drawCenteredString(fontRenderer, keyBindInfo, i, j - 20, Color.WHITE.getRGB());
				}

				@Override
				protected void updateButtonPositions(int x, int y) {
					
					toggleKeyBind.x = x - toggleKeyBind.width / 2;
					toggleKeyBind.y = y - 6;
					
					back.x = x - back.width / 2;
					back.y = y + 16;
				}
				
			});
		}
		
		super.actionPerformed(button);
	}
	
	@Override
	protected void updateButtonPositions(int x, int y) {	
		
		int tempY = y - 70;
		int spacing = 10;
		
		for(Tuple<AbstractTAEButton<?>, OptionsButton> tuple : modSets) {
			
			AbstractTAEButton<?> button1 = tuple.getFirst();
			OptionsButton button2 = tuple.getSecond();
			
			button1.x = x - button1.width / 2;
			button2.x = button1.x + button1.width + spacing;
			
			button1.y = tempY;
			button2.y = tempY + 3;
			
			tempY += 22;
			
		}
		
		back.x = x - back.width / 2;
		back.y = tempY;
		
	}

}
