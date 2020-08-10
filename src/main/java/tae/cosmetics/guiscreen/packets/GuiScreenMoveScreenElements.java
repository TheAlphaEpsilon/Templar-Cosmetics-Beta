package tae.cosmetics.guiscreen.packets;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.GuiScreen;
import tae.cosmetics.gui.util.mainscreen.CancelPacketsGuiList;
import tae.cosmetics.gui.util.mainscreen.VisualizePacketsGuiList;
import tae.cosmetics.guiscreen.AbstractTAEGuiScreen;
import tae.cosmetics.mods.CancelPacketMod;
import tae.cosmetics.mods.VisualizePacketsMod;

public class GuiScreenMoveScreenElements extends AbstractTAEGuiScreen {
	
	private CancelPacketsGuiList cancel;
	private VisualizePacketsGuiList caught;
	
	private boolean mouseWasClicked = false;
	
	private int oldCancelX = 0;
	private int oldCancelY = 0;
	
	private int oldCaughtX = 0;
	private int oldCaughtY = 0;
	
	private int oldMouseX = 0;
	private int oldMouseY = 0;
	
	private boolean cancelWasHovered = false;
	private boolean caughtWasHovered = false;
	
	public GuiScreenMoveScreenElements(GuiScreen parent) {
		super(parent);
		cancel = CancelPacketMod.getGuiTitleCopy();
		caught = VisualizePacketsMod.getGuiTitleCopy();
		
	}
	
	@Override
	public void onGuiClosed() {
		
		CancelPacketMod.updateGui(cancel.x, cancel.y);
		VisualizePacketsMod.updateGui(caught.x, caught.y);
		
	}
	
	@Override
	public void initGui() {
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		this.drawDefaultBackground();
		
		boolean clickflag = Mouse.isButtonDown(0);
		
		if(!mouseWasClicked && clickflag) {
			oldCancelX = cancel.x;
			oldCancelY = cancel.y;
			
			oldCaughtX = caught.x;
			oldCaughtY = caught.y;
			
			oldMouseX = mouseX;
			oldMouseY = mouseY;
		}
		
		mouseWasClicked = clickflag;

		if(clickflag && mouseWasClicked) {
			
			if(cancelWasHovered) {
				
				cancel.x = oldCancelX + (mouseX - oldMouseX);
				cancel.y = oldCancelY + (mouseY - oldMouseY);
				
			} else if(caughtWasHovered) {
				
				caught.x = oldCaughtX + mouseX - oldMouseX;
				caught.y = oldCaughtY + mouseY - oldMouseY;
				
			}
			
		}
		
		cancelWasHovered = cancel.isHovered(mouseX, mouseY);
		caughtWasHovered = caught.isHovered(mouseX, mouseY);
				
		cancel.draw(mouseX, mouseY);
		caught.draw(mouseX, mouseY);
	}
		
	protected void drawScreen0(int x, int y, float tick) {
		
	}

	@Override
	protected void updateButtonPositions(int x, int y) {		
	}
}
