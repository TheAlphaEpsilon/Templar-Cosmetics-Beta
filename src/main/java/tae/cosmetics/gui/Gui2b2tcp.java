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

public class Gui2b2tcp extends GuiScreen {

	private static final ResourceLocation BACKGROUND = new ResourceLocation("taecosmetics","textures/gui/playeroptions.png");

	private static Gui2b2tcp INSTANCE;
	
	public static Gui2b2tcp instance() {
		if(INSTANCE == null) {
			INSTANCE = new Gui2b2tcp();
		}
		return INSTANCE;
	}
	
	private static final int xOffset = 0;
	private static final int yOffset = 0;
	
	private GuiButton openCancelPacketGui;
	private GuiSetKeybind cancelPacketKeybind = null;
	
	private GuiButton openVisualizePackets;
	private GuiSetKeybind trackpackets = null;
	private GuiSetKeybind createmarker = null;
	
	private GuiButton openMoveScreenElements;
	private GuiButton back;
	
	private Gui2b2tcp() {
		openCancelPacketGui = new GuiButton(0, 0, 0, 150, 20, "Open Packet Selection");
		openVisualizePackets = new GuiButton(1, 0, 0, 150, 20, "Open Packet Visualization");
		
		openMoveScreenElements = new GuiButton(2, 0, 0, 150, 20, "Move Screen Elements");
		back = new GuiButton(3, 0, 0, 70, 20, "Back");
	}
	
	@Override
	public void initGui() {
		buttonList.add(openCancelPacketGui);
		buttonList.add(openVisualizePackets);
		buttonList.add(openMoveScreenElements);
		buttonList.add(back);
		
		if(cancelPacketKeybind == null) {
			cancelPacketKeybind = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, BaseMod.getKey(6));
			cancelPacketKeybind.setTextColor(-1);
			cancelPacketKeybind.setDisabledTextColour(-1);
			cancelPacketKeybind.setEnableBackgroundDrawing(true);
			cancelPacketKeybind.setMaxStringLength(32);
		}
		
		if(trackpackets == null) {
			trackpackets = new GuiSetKeybind(1, fontRenderer, 0, 0, 12, BaseMod.getKey(7));
			trackpackets.setTextColor(-1);
			trackpackets.setDisabledTextColour(-1);
			trackpackets.setEnableBackgroundDrawing(true);
			trackpackets.setMaxStringLength(32);
		}
		
		if(createmarker == null) {
			createmarker = new GuiSetKeybind(2, fontRenderer, 0, 0, 12, BaseMod.getKey(8));
			createmarker.setTextColor(-1);
			createmarker.setDisabledTextColour(-1);
			createmarker.setEnableBackgroundDrawing(true);
			createmarker.setMaxStringLength(32);
		}
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);

		BaseMod.setBind(6, cancelPacketKeybind.getKeyCode());
		BaseMod.setBind(7, trackpackets.getKeyCode());
		BaseMod.setBind(8, createmarker.getKeyCode());
		
		mc.gameSettings.saveOptions();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == openCancelPacketGui) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(GuiCancelPackets.instance());
			});
		} else if(button == openVisualizePackets) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(new GuiScreenVisualizePackets(this));
			});
		} else if(button == openMoveScreenElements) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(new GuiScreenMoveScreenElements(this));
			});
		} else if(button == back) {
			mc.addScheduledTask(() -> {
				mc.displayGuiScreen(GuiHome.instance());
			});
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		if(cancelPacketKeybind.textboxKeyTyped(typedChar, keyCode)) {
			
		} else if(trackpackets.textboxKeyTyped(typedChar, keyCode)) {
			
		} else if(createmarker.textboxKeyTyped(typedChar, keyCode)) {
			
		} else {	
			
			super.keyTyped(typedChar, keyCode);
			
		}
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if(cancelPacketKeybind.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} else if(trackpackets.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} else if(createmarker.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		}
		
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
		Gui.drawScaledCustomSizeModalRect(i - 145, j - 110, 0, 0, 242, 192, 290, 200, 256, 256);	
		
		this.drawString(fontRenderer, "Cancel packets keybind", i - 75, j - 65, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "Track packets keybind", i - 75, j - 15, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "Create a tracker", i - 75, j + 5, Color.WHITE.getRGB());

		cancelPacketKeybind.drawTextBox();
		trackpackets.drawTextBox();
		createmarker.drawTextBox();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	private void updateButtonLocations(int x, int y) {
		
		openCancelPacketGui.x = x - 75;
		openCancelPacketGui.y = y - 90;
		
		cancelPacketKeybind.x = x + 50;
		cancelPacketKeybind.y = y - 65;
		
		openVisualizePackets.x = x - 75;
		openVisualizePackets.y = y - 40;
		
		trackpackets.x = x + 50;
		trackpackets.y = y - 15;
		
		createmarker.x = x + 50;
		createmarker.y = y + 5;
		
		openMoveScreenElements.x = x - 75;
		openMoveScreenElements.y = y + 25;
		
		back.x = x - 75;
		back.y = y + 50;
		
	}
	
}
