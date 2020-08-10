package tae.cosmetics.gui.util;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ScrollBar extends Gui {
	//Current scroll from 0 to 1
	private float currentScroll = 0.0F;
	/** True if the scrollbar is being dragged */
	private boolean isScrolling = false;
	//true if was clicking last frame
	private boolean wasClicking = false;
	
	private boolean disabled = false;
	
	public int x = 0;
	
	public int y = 0;
		
	private int height = 0;
	
	private int area = -1;
	
	private static final ResourceLocation STALKER_MOD_GUI = new ResourceLocation("taecosmetics","textures/gui/playeralert.png");
	
	public ScrollBar(int x, int y, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
	}
	
	public ScrollBar(int x, int y, int height, int area) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.area = area;
	}
	
	public void draw(int mouseX, int mouseY) {
		
		boolean clickflag = Mouse.isButtonDown(0);
				
        if (!this.wasClicking && clickflag && mouseX >= x && mouseY >= y && mouseX < x + 12 && mouseY < y + height) {
            this.isScrolling = !disabled;
        }
        
        if (!clickflag) {
            this.isScrolling = false;
        }
		
        this.wasClicking = clickflag;
        
        if (this.isScrolling) {
            this.currentScroll = ((float)(mouseY - y) - 7.5F) / ((float)(height) - 15.0F);
        }
        
        if(area > 0) {
        	if(!disabled && mouseX > x - area && mouseX < x + area) {
            	int wheel = Mouse.getDWheel();
                currentScroll -= wheel * (Minecraft.getMinecraft().gameSettings.mouseSensitivity / 100F);
        	}
        } else {
        	if(disabled) {
        		Mouse.getDWheel();
        	}
        	int wheel = Mouse.getDWheel();
            currentScroll -= wheel * (Minecraft.getMinecraft().gameSettings.mouseSensitivity / 100F);
        }
        
        this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(STALKER_MOD_GUI);
        this.drawTexturedModalRect(x, y + (int)((float)(height - 15) * this.currentScroll), 77 + (!disabled && !this.isScrolling ? 0 : 12), 236, 12, 15);
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	
	public void setDisabled(boolean bool) {
		disabled = bool;
	}
	
	public float getScroll() {
		return currentScroll;
	}

	public void setScroll(float f) {
		currentScroll = f;
	}
	
}
