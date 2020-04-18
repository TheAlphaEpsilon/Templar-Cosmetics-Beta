package tae.cosmetics.gui.util;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class GuiExtendPacketModuleButton extends GuiButton {
	
	private static final int scalefactor = 2;
	
	private boolean extended = false;
	public AbstractPacketModule module;
	
	public GuiExtendPacketModuleButton(int buttonId, int x, int y, AbstractPacketModule module) {
		super(buttonId, x, y, 20, 20, "");
		this.module = module;
	}
	
	public void toggle() {
		extended = extended ? false : true;
		module.setMinimized(!extended);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
    		
        	int x = this.x * scalefactor;
        	int y = this.y * scalefactor;
        	
        	GlStateManager.scale(1D / scalefactor, 1D / scalefactor, 1D / scalefactor);
        	
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + width / scalefactor && mouseY < this.y + height / scalefactor;
            int i = this.getHoverState(this.hovered);
           
           
            if(!extended) {	
            	for(int counter = 1; counter < width / 2; counter++) {
            		this.drawVerticalLine(x + counter, y + counter, y + height - counter, Color.BLACK.getRGB());
            	}
            	
            	if(i == 2) {
            		for(int counter = 2; counter < width / 2 - 1; counter++) {
            			this.drawVerticalLine(x + counter, y + counter, y + height - counter, Color.YELLOW.getRGB());
            		}
            	}
            } else {
            	for(int counter = 1; counter < height / 2; counter++) {
            		this.drawHorizontalLine(x + counter, x + width - counter, y + counter, Color.BLACK.getRGB());
            	}
            	
            	if(i == 2) {
            		for(int counter = 2; counter < height / 2 - 1; counter++) {
            			this.drawHorizontalLine(x + counter, x + width - counter, y + counter, Color.YELLOW.getRGB());
            		}
            	}
            }
            
            
    		GlStateManager.scale(scalefactor, scalefactor, scalefactor);
            
        }
    }

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return this.enabled && this.visible && this.hovered;
    }
	
}

