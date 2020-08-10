package tae.cosmetics.guiscreen;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Tuple;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.guiscreen.button.AbstractTAEButton;
import tae.cosmetics.guiscreen.button.GuiDisplayListButton;
import tae.cosmetics.guiscreen.button.GuiGenericButton;
import tae.cosmetics.guiscreen.button.GuiOnOffButton;
import tae.cosmetics.guiscreen.button.OptionsButton;
import tae.cosmetics.guiscreen.render.GuiPlayerFaker;
import tae.cosmetics.guiscreen.render.GuiTimestampMod;
import tae.cosmetics.mods.HoverMapAndBook;
import tae.cosmetics.mods.ThrownEntityTrails;

public class GuiScreenRendering extends AbstractTAEGuiScreen {

	private ArrayList<Tuple<AbstractTAEButton<?>, OptionsButton>> modSets = new ArrayList<>();
	
	private GuiGenericButton openTimeStampMod = new GuiGenericButton(0, 0, 0, 150, 20, "Open Timestamp Mod", "Add timestamps to chat", 1);
	private OptionsButton timestampOptions = new OptionsButton(1, 0, 0, 20, 20, "", "Timestamp Mod Options", 1);
	
	private GuiOnOffButton entityTrails = new GuiOnOffButton(2, 0, 0, 150, 20, "Entity Data: ", ThrownEntityTrails.enabled.getValue(), "Get Entity Data", 1);
	private OptionsButton entityTrailsOptions = new OptionsButton(3, 0, 0, 20, 20, "", "Entity Data Options", 1);
	
	private GuiGenericButton bookAndMapViewer = new GuiGenericButton(4, 0, 0, 150, 20, "Book and Map Viewer", "See books and maps in inventory", 1);
	private OptionsButton bookAndMapOptions = new OptionsButton(5, 0, 0, 20, 20, "", "Book and Map Viewer Options", 1);
	
	private GuiGenericButton playerFaker = new GuiGenericButton(6, 0, 0, 150, 20, "Player Faker", "EZ Clout", 1);
	private OptionsButton playerFakerOptions = new OptionsButton(7, 0, 0, 20, 20, "", "Player Faker Options", 1);
	
	public GuiScreenRendering(GuiScreen parent) {
		super(parent);
		
		modSets.add(new Tuple<>(openTimeStampMod, timestampOptions));
		modSets.add(new Tuple<>(entityTrails, entityTrailsOptions));
		modSets.add(new Tuple<>(bookAndMapViewer, bookAndMapOptions));
		modSets.add(new Tuple<>(playerFaker, playerFakerOptions));

	}

	@Override
	public void initGui() {
		for(Tuple<AbstractTAEButton<?>, OptionsButton> tuple : modSets) {
			buttonList.add(tuple.getSecond());
			buttonList.add(tuple.getFirst());
		}
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		if(button instanceof AbstractTAEButton) {
			((AbstractTAEButton<?>) button).changeStateOnClick();
		}
		
		if(button == openTimeStampMod) {
			displayScreen(new GuiTimestampMod(this));
		} else if(button == timestampOptions) {
			displayScreen(new GuiTimestampMod(this).settingsScreen);
		} else if(button == entityTrails) {
			ThrownEntityTrails.enabled.setValue(((GuiOnOffButton)button).getValue());
		} else if(button == entityTrailsOptions) {
			displayScreen(new AbstractTAEGuiScreen(this) {

				{
		    		guiwidth = 200;
		    		guiheight = 200;
		    	}
		    	
		    	private GuiSetKeybind toggleKeyBind = null;
		    	private String keyBindInfo = "Toggle thrown entity data";
		    	
		    	private GuiOnOffButton toggleTrails = new GuiOnOffButton(0, 0, 0, 150, 20, "Entity Trails: ", ThrownEntityTrails.trails.getValue(), "See entity trails", 1);
		    	private GuiOnOffButton alwaysShow = new GuiOnOffButton(1, 0, 0, 150, 20, "Always Show: ", ThrownEntityTrails.alwaysShow.getValue(), "Always see entity trails", 1);
		    	private GuiOnOffButton showNameTags = new GuiOnOffButton(2, 0, 0, 150, 20, "Nametags: ", ThrownEntityTrails.showNameTags.getValue(), "See who threw the entity, if possible", 1);
		    	private GuiOnOffButton textAlert = new GuiOnOffButton(3, 0, 0, 150, 20, "Text Alert: ", ThrownEntityTrails.textAlert.getValue(), "Get a chat alert if a thrown entity is loaded", 1);
		    	private GuiOnOffButton nbtData = new GuiOnOffButton(4, 0, 0, 150, 20, "NBT Data: ", ThrownEntityTrails.nbt.getValue(), "Get thrown entity data", 1);
		    	
		    	@Override
		    	public void initGui() {
		    		if(toggleKeyBind == null) {
		    			toggleKeyBind = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, ThrownEntityTrails.keyBind.getInt());
		    			toggleKeyBind.setTextColor(-1);
		    			toggleKeyBind.setDisabledTextColour(-1);
		    			toggleKeyBind.setEnableBackgroundDrawing(true);
		    			toggleKeyBind.setMaxStringLength(32);
		    			toggleKeyBind.setCanLoseFocus(true);
		    		}
		    		
		    		buttonList.add(toggleTrails);
		    		buttonList.add(alwaysShow);
		    		buttonList.add(showNameTags);
		    		buttonList.add(textAlert);
		    		buttonList.add(nbtData);

		    		super.initGui();
		    	}
		    	
		    	@Override
		    	protected void actionPerformed(GuiButton button) throws IOException {
		    		
		    		if(button instanceof AbstractTAEButton) {
		    			((AbstractTAEButton<?>) button).changeStateOnClick();
		    		}
		    	
		    		if(button == toggleTrails) {
		    			
		    			ThrownEntityTrails.trails.setValue(toggleTrails.getValue());
		    			
		    		} else if(button == alwaysShow) {
		    			
		    			ThrownEntityTrails.alwaysShow.setValue(alwaysShow.getValue());
		    			
		    		} else if(button == showNameTags) {
		    			
		    			ThrownEntityTrails.showNameTags.setValue(showNameTags.getValue());
		    			
		    		} else if(button == textAlert) {
		    			
		    			ThrownEntityTrails.textAlert.setValue(textAlert.getValue());
		    			
		    		} else if(button == nbtData) {
		    			
		    			ThrownEntityTrails.nbt.setValue(nbtData.getValue());
		    			
		    		}
		    		
		    		super.actionPerformed(button);
		    		
		    	}
		    	
				@Override
				public void onGuiClosed() {
					Keyboard.enableRepeatEvents(false);
					
					ThrownEntityTrails.keyBind.updateBinding(toggleKeyBind.getKeyCode());
									
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
					
					this.drawCenteredString(fontRenderer, keyBindInfo, i, j - 70, Color.WHITE.getRGB());
				}

				@Override
				protected void updateButtonPositions(int x, int y) {
					
					toggleKeyBind.x = x - toggleKeyBind.width / 2;
					toggleKeyBind.y = y - 56;
					
					
					toggleTrails.x = x - toggleTrails.width / 2;
					toggleTrails.y = y - 42;
					
					alwaysShow.x = x - alwaysShow.width / 2;
					alwaysShow.y = y - 20;
					
					showNameTags.x = x - showNameTags.width / 2;
					showNameTags.y = y + 2;
					
					textAlert.x = x - textAlert.width / 2;
					textAlert.y = y + 24;
					
					nbtData.x = x - nbtData.width / 2;
					nbtData.y = y + 46;
			
					
					back.x = x - back.width / 2;
					back.y = y + 70;
				}
				
			});
		} else if(button == bookAndMapViewer || button == bookAndMapOptions) {
			displayScreen(new AbstractTAEGuiScreen(this) {

				{
		    		guiwidth = 300;
		    		guiheight = 150;
		    	}
		    	
		    	private GuiSetKeybind toggleKeyBind = null;
		    	private String keyBindInfo = "Press this key while hovering over a map or book";
		    	private GuiDisplayListButton<Integer> scale = new GuiDisplayListButton<Integer>(0, 0, 0, 20, 20, Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16}), HoverMapAndBook.getScale(), "The size of the new overlay", 1);
		    	
		    	@Override
		    	public void initGui() {
		    		if(toggleKeyBind == null) {
		    			toggleKeyBind = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, HoverMapAndBook.keybind.getInt());
		    			toggleKeyBind.setTextColor(-1);
		    			toggleKeyBind.setDisabledTextColour(-1);
		    			toggleKeyBind.setEnableBackgroundDrawing(true);
		    			toggleKeyBind.setMaxStringLength(32);
		    			toggleKeyBind.setCanLoseFocus(true);
		    		}
		    		buttonList.add(scale);
		    		super.initGui();
		    	}
		    	
		    	@Override
		    	protected void actionPerformed(GuiButton button) throws IOException {
		    		
		    		if(button instanceof AbstractTAEButton) {
		    			((AbstractTAEButton<?>) button).changeStateOnClick();
		    		}
		    		
		    		if(button == scale) {
		    			HoverMapAndBook.updateScale(scale.getValue());
		    		}
		    		
		    		super.actionPerformed(button);
		    		
		    	}
		    	
				@Override
				public void onGuiClosed() {
					Keyboard.enableRepeatEvents(false);
					
					HoverMapAndBook.keybind.updateBinding(toggleKeyBind.getKeyCode());
									
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
					
					this.drawCenteredString(fontRenderer, keyBindInfo, i, j - 30, Color.WHITE.getRGB());
				}

				@Override
				protected void updateButtonPositions(int x, int y) {
					
					toggleKeyBind.x = x - toggleKeyBind.width / 2;
					toggleKeyBind.y = y - 16;
					
					scale.x = x - scale.width / 2;
					scale.y = y + 10;
					
					back.x = x - back.width / 2;
					back.y = y + 32;
				}
				
			});
		} else if(button == playerFaker) {
			displayScreen(new GuiPlayerFaker(this));
		} else if(button == playerFakerOptions) {
			displayScreen(new GuiPlayerFaker(this).settingsScreen);
		}
		
		super.actionPerformed(button);
	}
	
	@Override
	public void onGuiClosed() {		
	}

	@Override
	protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {	
		
		int i = width / 2;
		int j = height / 2;
		
		this.drawCenteredString(fontRenderer, "Render", i, j - 82, Color.WHITE.getRGB());
		
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
