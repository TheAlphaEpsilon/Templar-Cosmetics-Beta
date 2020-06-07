package tae.cosmetics.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.mods.BaseMod;

public class GuiHome extends GuiScreen {

	private static final ResourceLocation BACKGROUND = new ResourceLocation("taecosmetics","textures/gui/playeroptions.png");
	
	private GuiButton openBookMod;
	private GuiButton openStalkerMod;
	private GuiButton openTimeStampMod;
	private GuiButton open2b2tcp;
	private GuiButton openMisc;
	
	private GuiSetKeybind bookModKey = null;
	private GuiSetKeybind stalkerModKey = null;
	private GuiSetKeybind timestampModKey = null;
	private GuiSetKeybind home = null;
		
	private static final int xOffset = 80;
	private static final int yOffset = 0;
		
	private static GuiHome INSTANCE = null;
	
	private GuiHome() {
		openBookMod = new GuiButton(0, 0, 0, 150, 20, "Open Book Title Mod GUI");
		openStalkerMod = new GuiButton(1, 0, 0, 150, 20, "Open Stalker Mod GUI");
		openTimeStampMod = new GuiButton(2, 0, 0, 150, 20, "Open Timestamp Mod GUI");
		open2b2tcp = new GuiButton(3, 0, 0, 150, 20, "2b2tcpdump");
		openMisc = new GuiButton(4, 0, 0, 150, 20, "Misc");
	}
	
	public static GuiHome instance() {
		if(INSTANCE == null) {
			return new GuiHome();
		} else {
			return INSTANCE;
		}
	}
	
	@Override
	public void initGui() {
		
		buttonList.add(openBookMod);
		buttonList.add(openStalkerMod);
		buttonList.add(openTimeStampMod);
		buttonList.add(open2b2tcp);
		buttonList.add(openMisc);
				
		if(bookModKey == null ) {        	
        	
        	bookModKey = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, BaseMod.getKey(0));
        	bookModKey.setTextColor(-1);
        	bookModKey.setDisabledTextColour(-1);
        	bookModKey.setEnableBackgroundDrawing(true);
        	bookModKey.setMaxStringLength(32);
        
		}
				
		if(stalkerModKey == null ) {        	
        	
        	stalkerModKey = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, BaseMod.getKey(1));
        	stalkerModKey.setTextColor(-1);
        	stalkerModKey.setDisabledTextColour(-1);
        	stalkerModKey.setEnableBackgroundDrawing(true);
        	stalkerModKey.setMaxStringLength(32);
        
		}
		
		if(timestampModKey == null ) {        	
        	
        	timestampModKey = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, BaseMod.getKey(2));
        	timestampModKey.setTextColor(-1);
        	timestampModKey.setDisabledTextColour(-1);
        	timestampModKey.setEnableBackgroundDrawing(true);
        	timestampModKey.setMaxStringLength(32);
        
		}
		
		if(home == null) {
			home = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, BaseMod.getKey(3));
        	home.setTextColor(-1);
        	home.setDisabledTextColour(-1);
        	home.setEnableBackgroundDrawing(true);
        	home.setMaxStringLength(32);
        
		}
		    	
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);

		BaseMod.setBind(0, bookModKey.getKeyCode());
		
		BaseMod.setBind(1, stalkerModKey.getKeyCode());
		
		BaseMod.setBind(2, timestampModKey.getKeyCode());
		
		BaseMod.setBind(3, home.getKeyCode());
				
		mc.gameSettings.saveOptions();
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == openBookMod) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(GuiBookTitleMod.instance());
			});
		} else if(button == openStalkerMod) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(new GuiScreenStalkerMod(this));
			});
		} else if(button == openTimeStampMod) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(GuiTimestampMod.instance());
			});
		} else if(button == open2b2tcp) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(Gui2b2tcp.instance());
			});
		} else if(button == openMisc) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(new GuiMisc(this));
			});
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (bookModKey.textboxKeyTyped(typedChar, keyCode)) {
			
        } else if (stalkerModKey.textboxKeyTyped(typedChar, keyCode)) {
			
        } else if (timestampModKey.textboxKeyTyped(typedChar, keyCode)) {
			
        } else if (home.textboxKeyTyped(typedChar, keyCode)) {
        
        } else {	
       
            super.keyTyped(typedChar, keyCode);
            
        }
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	
		if(bookModKey.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} else if(stalkerModKey.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} else if(timestampModKey.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} else if(home.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} 
		
		//TODO: rest of text boxes
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		int i = width / 2 + xOffset;
		int j = height / 2 + yOffset;
		
		updateButtonLocations(i, j);
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		
		mc.getTextureManager().bindTexture(BACKGROUND);
		Gui.drawScaledCustomSizeModalRect(i - 200, j - 100, 0, 0, 242, 192, 290, 200, 256, 256);	
		
		bookModKey.drawTextBox();
		stalkerModKey.drawTextBox();
		timestampModKey.drawTextBox();
		home.drawTextBox();
		
		this.drawString(fontRenderer, "Open GUI", i - 180, j - 82, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "Keybinds", i - 10, j - 82, Color.WHITE.getRGB());
		
		this.drawString(fontRenderer, "Keybind for this screen", i - 180, j + 54, Color.WHITE.getRGB());
				
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	private void updateButtonLocations(int x, int y) {
		
		openBookMod.x = x - 180;
		openBookMod.y = y - 70;
		
		openStalkerMod.x = x - 180;
		openStalkerMod.y = y - 46;
		
		openTimeStampMod.x = x - 180;
		openTimeStampMod.y = y - 22;
		
		open2b2tcp.x = x - 180;
		open2b2tcp.y = y + 2;
		
		openMisc.x = x - 180;
		openMisc.y = y + 26;
		
		bookModKey.x = x - 10;
		bookModKey.y = y - 67;
		
		stalkerModKey.x = x - 10;
		stalkerModKey.y = y - 43;
		
		timestampModKey.x = x - 10;
		timestampModKey.y = y - 19;
		
		home.x = x - 10;
		home.y = y + 54;
		
	}
	
}
