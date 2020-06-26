package tae.cosmetics.gui;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Books extends AbstractTAEGuiScreen {

	private static final ResourceLocation BACKGROUND = new ResourceLocation("taecosmetics","textures/gui/playeroptions.png");
	
	private GuiButton openBookMod;
	private GuiButton openBookArt;
	
	protected Books(GuiScreen parent) {
		super(parent);
		openBookMod = new GuiButton(0, 0, 0, 150, 20, "Open Book Title Mod");
		openBookArt = new GuiButton(0, 0, 0, 150, 20, "Open Book Art Mod");
	}
	
	protected void drawScreen0(int mouseX, int mouseY, float tick) {
		
	}
	
	@Override
	public void initGui() {
		buttonList.add(openBookArt);
		buttonList.add(openBookMod);
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
		
	}

	@Override
	protected void updateButtonPositions(int x, int y) {
		openBookMod.x = x - 150 / 2;
		openBookMod.y = y - 70;
		
		openBookArt.x = x - 150 / 2;
		openBookArt.y = y - 46;
	}

}
