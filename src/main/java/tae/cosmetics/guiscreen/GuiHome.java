package tae.cosmetics.guiscreen;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tae.cosmetics.TAECosmetics;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.guiscreen.button.GuiGenericButton;
import tae.cosmetics.guiscreen.button.GuiOnOffButton;
import tae.cosmetics.guiscreen.packets.Gui2b2tcp;
import tae.cosmetics.settings.Keybind;

public class GuiHome extends GuiScreen {

	
	public static final Keybind openGui = new Keybind("Open Home Menu", Keyboard.KEY_BACKSLASH, () -> {
		AbstractTAEGuiScreen.displayScreen(new GuiHome());
	});
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation("taecosmetics","textures/gui/playeroptions.png");
	
	private GuiGenericButton utilities = new GuiGenericButton(0, 0, 0, 150, 20, "General Utilities", "Toggle and Modify Utilities", 1);
	private GuiGenericButton render = new GuiGenericButton(1, 0, 0, 150, 20, "Render", "Changes how you see things", 1);
	private GuiGenericButton packet = new GuiGenericButton(2, 0, 0, 150, 20, "Packets", "See packet info", 1);
	private GuiGenericButton motd = new GuiGenericButton(4, 0, 0, 150, 20, "MOTD", "See updates by the dev", 1);
	
	private GuiOnOffButton toggleMenuBackground = new GuiOnOffButton(3, 0, 0, 170, 20, "Toggle Menu Background: ", TAECosmetics.changeTitle.getValue(), "Toggle the menu background", 1);
	
	//private GuiSetKeybind bookModKey = null;
	private GuiSetKeybind home = null;
		
	private static final int guiwidth = 290;
	private static final int guiheight = 200;
			
	@Override
	public void initGui() {
		
		buttonList.add(utilities);
		buttonList.add(render);
		buttonList.add(packet);
		buttonList.add(motd);

		buttonList.add(toggleMenuBackground);
		/*		
		if(bookModKey == null ) {        	
        	
        	bookModKey = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, BaseMod.getKey(0));
        	bookModKey.setTextColor(-1);
        	bookModKey.setDisabledTextColour(-1);
        	bookModKey.setEnableBackgroundDrawing(true);
        	bookModKey.setMaxStringLength(32);
        
		}
			*/	
		
		if(home == null) {
			home = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, openGui.getInt());
        	home.setTextColor(-1);
        	home.setDisabledTextColour(-1);
        	home.setEnableBackgroundDrawing(true);
        	home.setMaxStringLength(32);
        	home.setCanLoseFocus(true);
		}
		    	
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);

		//BaseMod.setBind(0, bookModKey.getKeyCode());
		
		openGui.updateBinding(home.getKeyCode());
						
		mc.gameSettings.saveOptions();
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == utilities) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(new GuiScreenUtilities(this));
			});
		} else if(button == render) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(new GuiScreenRendering(this));
			});
		} else if(button == packet) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(new Gui2b2tcp(this));
			});
		} else if(button == toggleMenuBackground) {
			toggleMenuBackground.changeStateOnClick();
			TAECosmetics.changeTitle.setValue(toggleMenuBackground.getValue());
		} else if(button == motd) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(new GuiMOTD(this));
			});
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (home.textboxKeyTyped(typedChar, keyCode)) {
        
        } else {	
       
            super.keyTyped(typedChar, keyCode);
            
        }
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	
		if(home.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} 		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		int i = width / 2;
		int j = height / 2;
		
		updateButtonLocations(i, j);
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		
		mc.getTextureManager().bindTexture(BACKGROUND);
		Gui.drawScaledCustomSizeModalRect(i - guiwidth / 2, j - guiheight / 2, 0, 0, 242, 192, guiwidth, guiheight, 256, 256);	
		
		//bookModKey.drawTextBox();
		home.drawTextBox();
				
		this.drawCenteredString(fontRenderer, "Options", i, j - 82, Color.WHITE.getRGB());
		
		this.drawCenteredString(fontRenderer, "Keybind for this screen", i, j + 30, Color.WHITE.getRGB());
				
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	private void updateButtonLocations(int x, int y) {
				
		utilities.x = x - utilities.width / 2;
		utilities.y = y - 70;
		
		render.x = x - render.width / 2;
		render.y = y - 46;
		
		packet.x = x - packet.width / 2;
		packet.y = y - 22;
		
		motd.x = x - motd.width / 2;
		motd.y = y + 2;
		
		//bookModKey.x = x - 10;
		//bookModKey.y = y - 67;
		
		home.x = x - home.width / 2;
		home.y = y + 42;
		
		toggleMenuBackground.x = x - toggleMenuBackground.width / 2;
		toggleMenuBackground.y = y + 70;
		
	}
	
}
