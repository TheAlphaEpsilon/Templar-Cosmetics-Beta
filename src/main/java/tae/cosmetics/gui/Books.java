package tae.cosmetics.gui;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.settings.Keybind;

public class Books extends AbstractTAEGuiScreen {
	
	private static GuiScreen instance = new Books(GuiHome.instance());

	private GuiButton openBookMod;
	private GuiButton openBookArt;
	
	private GuiSetKeybind bookModKey = null;
	private GuiSetKeybind bookArtKey = null;
	
	protected Books(GuiScreen parent) {
		super(parent);
		openBookMod = new GuiButton(0, 0, 0, 150, 20, "Open Book Title Mod");
		openBookArt = new GuiButton(0, 0, 0, 150, 20, "Open Book Art Mod");
		instance = this;
	}
	
	protected void drawScreen0(int mouseX, int mouseY, float tick) {
		
		int i = width / 2;
		int j = height / 2;
		
		bookModKey.drawTextBox();
		bookArtKey.drawTextBox();
		
		drawString(fontRenderer, "Open GUIs", i - 75, j - 85, Color.WHITE.getRGB());
		
		drawString(fontRenderer, "Keybinds", i + 65, j - 85, Color.WHITE.getRGB());
		
	}
	
	@Override
	public void initGui() {
		buttonList.add(openBookArt);
		buttonList.add(openBookMod);
		
		if(bookModKey == null ) {        	
        	
        	bookModKey = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, GuiBookTitleMod.openGui.getInt());
        	bookModKey.setTextColor(-1);
        	bookModKey.setDisabledTextColour(-1);
        	bookModKey.setEnableBackgroundDrawing(true);
        	bookModKey.setMaxStringLength(32);
        
		}
		
		if(bookArtKey == null ) {        	
        	
        	bookArtKey = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, GuiHome.openBookArtGui.getInt());
        	bookArtKey.setTextColor(-1);
        	bookArtKey.setDisabledTextColour(-1);
        	bookArtKey.setEnableBackgroundDrawing(true);
        	bookArtKey.setMaxStringLength(32);
        
		}

		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == openBookMod) {
			displayScreen(GuiBookTitleMod.instance());
		} else if(button == openBookArt) {
			displayScreen(new BookArt(this));
		}
		super.actionPerformed(button);
	}
	
	@Override
	public void onGuiClosed() {
		GuiBookTitleMod.openGui.updateBinding(bookModKey.getKeyCode());
		GuiHome.openBookArtGui.updateBinding(bookArtKey.getKeyCode());
		
		mc.gameSettings.saveOptions();
	}
	
	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		if(bookModKey.textboxKeyTyped(typedChar, keyCode)) {
			
		} else if(bookArtKey.textboxKeyTyped(typedChar, keyCode)) {
			
		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if(bookModKey.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} else if(bookArtKey.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		}
		
	}

	@Override
	protected void updateButtonPositions(int x, int y) {
		openBookMod.x = x - 150 / 2 - 50;
		openBookMod.y = y - 70;
		
		openBookArt.x = x - 150 / 2 - 50;
		openBookArt.y = y - 46;
		
		bookModKey.x = x + 50;
		bookModKey.y = y - 65;
		
		bookArtKey.x = x + 50;
		bookArtKey.y = y - 41;
	}

}
