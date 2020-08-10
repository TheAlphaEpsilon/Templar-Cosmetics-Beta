package tae.cosmetics.guiscreen.packets;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.guiscreen.AbstractTAEGuiScreen;
import tae.cosmetics.guiscreen.GuiHome;
import tae.cosmetics.guiscreen.button.GuiGenericButton;
import tae.cosmetics.mods.CancelPacketMod;
import tae.cosmetics.mods.VisualizePacketsMod;

public class Gui2b2tcp extends AbstractTAEGuiScreen {

	private GuiButton openCancelPacketGui;
	private GuiSetKeybind cancelPacketKeybind = null;
	
	private GuiButton openVisualizePackets;
	private GuiSetKeybind trackpackets = null;
	private GuiSetKeybind createmarker = null;
	
	private GuiButton openMoveScreenElements;
	private GuiButton back;
	
	public Gui2b2tcp(GuiScreen parent) {
		
		super(parent);
		
		openCancelPacketGui = new GuiGenericButton(0, 0, 0, 150, 20, "Open Packet Selection", "Choose what packets to cancel", 1);
		openVisualizePackets = new GuiGenericButton(1, 0, 0, 150, 20, "Open Packet Visualization", "Scroll through captured packets", 1);
		
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
			cancelPacketKeybind = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, CancelPacketMod.toggle.getInt());
			cancelPacketKeybind.setTextColor(-1);
			cancelPacketKeybind.setDisabledTextColour(-1);
			cancelPacketKeybind.setEnableBackgroundDrawing(true);
			cancelPacketKeybind.setMaxStringLength(32);
		}
		
		if(trackpackets == null) {
			trackpackets = new GuiSetKeybind(1, fontRenderer, 0, 0, 12, VisualizePacketsMod.toggle.getInt());
			trackpackets.setTextColor(-1);
			trackpackets.setDisabledTextColour(-1);
			trackpackets.setEnableBackgroundDrawing(true);
			trackpackets.setMaxStringLength(32);
		}
		
		if(createmarker == null) {
			createmarker = new GuiSetKeybind(2, fontRenderer, 0, 0, 12, VisualizePacketsMod.addMarker.getInt());
			createmarker.setTextColor(-1);
			createmarker.setDisabledTextColour(-1);
			createmarker.setEnableBackgroundDrawing(true);
			createmarker.setMaxStringLength(32);
		}
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);

		CancelPacketMod.toggle.updateBinding(cancelPacketKeybind.getKeyCode());
		VisualizePacketsMod.toggle.updateBinding(trackpackets.getKeyCode());
		VisualizePacketsMod.addMarker.updateBinding(createmarker.getKeyCode());
	
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
				mc.displayGuiScreen(new GuiHome());
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
	public void drawScreen0(int mouseX, int mouseY, float partialTicks) {
		
		int i = width / 2;
		int j = height / 2;
		
		this.drawString(fontRenderer, "Cancel packets keybind", i - 75, j - 65, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "Track packets keybind", i - 75, j - 15, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "Create a tracker", i - 75, j + 5, Color.WHITE.getRGB());

		cancelPacketKeybind.drawTextBox();
		trackpackets.drawTextBox();
		createmarker.drawTextBox();
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	@Override
	protected void updateButtonPositions(int x, int y) {
		
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
