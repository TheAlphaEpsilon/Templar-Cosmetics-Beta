package tae.cosmetics.guiscreen.button;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class GuiExtendButton extends AbstractTAEButton<Boolean> {
		
	private boolean extended = false;
	public AbstractPacketModule module;
	
	public GuiExtendButton(int buttonId, int x, int y, AbstractPacketModule module, String info, int scale) {
		super(buttonId, x, y, 20, 20, "", info, scale);
		this.module = module;
	}
	
	@Override
	public void drawButton0(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
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
    }

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return this.enabled && this.visible && this.hovered;
    }

	@Override
	public void changeStateOnClick() {
		extended = extended ? false : true;
		module.setMinimized(!extended);
	}

	@Override
	public Boolean getValue() {
		return extended;
	}
	
}

