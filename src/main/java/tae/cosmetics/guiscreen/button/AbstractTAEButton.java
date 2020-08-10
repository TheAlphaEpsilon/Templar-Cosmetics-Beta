package tae.cosmetics.guiscreen.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import tae.cosmetics.guiscreen.AbstractTAEGuiScreen;

public abstract class AbstractTAEButton<T> extends GuiButton {

	protected int scale;
	protected String info;
	
	public AbstractTAEButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String info, int scale) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.scale = scale;
		this.info = info;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		
		if(this.visible) {
			
			int oldX = this.x;
			int oldY = this.y;
			
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + width / scale && mouseY < this.y + height / scale;			

			this.x = this.x * scale;
        	this.y = this.y * scale;
        	
        	GlStateManager.scale(1D / scale, 1D / scale, 1D / scale);
        	
        	this.drawButton0(mc, mouseX, mouseY, partialTicks);
    		
    		if(this.hovered && !info.isEmpty()) {
    			
    			AbstractTAEGuiScreen.drawHoveringTextGlobal(info, mouseX * scale, mouseY * scale);
    			
    		}
    		
    		this.x = oldX;
    		this.y = oldY;
    		
        	GlStateManager.scale(scale, scale, scale);
		}	
		
	}
	
	protected abstract void drawButton0(Minecraft mc, int mouseX, int mouseY, float partialTicks);
	
	public abstract void changeStateOnClick();
	
	public abstract T getValue();
	
}
