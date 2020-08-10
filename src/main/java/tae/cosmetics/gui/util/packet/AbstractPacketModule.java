package tae.cosmetics.gui.util.packet;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import tae.cosmetics.guiscreen.button.GuiExtendButton;
import tae.cosmetics.guiscreen.button.GuiMorePacketInformationButton;

public abstract class AbstractPacketModule extends Gui {
	
	protected static final int scalefactor = 2;
	
	protected static final int modwidth = 210;
	protected static final int modheightminimized= 24;
	protected static final int modheightfull = (modheightminimized) * 5;
	protected static final int modtimestampwidth = 100;
		
	//x coord
	public int x = 0;
	//y coord
	public int y = 0;
	//timestamp
	private long timestamp = -1;
	//TODO: make private
	public Packet<?> packet;
	
	protected boolean minimized = true;
	
	protected FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		
	private String hoverText;
	
	private GuiExtendButton extendButton;
	
	private GuiMorePacketInformationButton moreInfoButton;
	
	protected AbstractPacketModule(String info, long timestamp, Packet<?> packet) {
		hoverText = info;
		this.timestamp = timestamp;
		this.packet = packet;
		
		extendButton = new GuiExtendButton(0, 0, 0, this, "", 2);
		
		moreInfoButton = new GuiMorePacketInformationButton(0, 0, 0, packet, "", 2);
	}
	
	public GuiExtendButton extendButton() {
		extendButton.x = x - 10;
		extendButton.y = y;
		return extendButton;
	}
	
	public GuiMorePacketInformationButton moreInfoButton() {
		moreInfoButton.x = x + getWidth() + 10;
		moreInfoButton.y = y;
		return moreInfoButton;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		int x = this.x * scalefactor;
		int y = this.y * scalefactor;
		
		GlStateManager.scale(1D / scalefactor, 1D / scalefactor, 1D / scalefactor);
		
		int color;
		this.drawGradientRect(x, y, x + modtimestampwidth + modwidth * 2, y + (minimized ? modheightminimized : modheightfull), color = new Color(128, 0, 128).getRGB(), color);
		this.drawGradientRect(x + 1, y + 1, x + modtimestampwidth + modwidth * 2 - 1, y + (minimized ? modheightminimized : modheightfull) - 1, color = new Color(64, 0, 64).getRGB(), color);
		this.drawGradientRect(x + 2, y, x + 2 + modtimestampwidth - 2, y + (minimized ? modheightminimized : modheightfull) - 2, color = Color.BLACK.getRGB(), color);
		this.drawGradientRect(x + 2 + modtimestampwidth + 1, y + 2, x + 2 + modtimestampwidth + modwidth - 1, y + (minimized ? modheightminimized : modheightfull) - 2, color = Color.BLACK.getRGB(), color);
		this.drawGradientRect(x + modtimestampwidth + modwidth + 3, y + 2, x + modtimestampwidth + modwidth * 2, y + (minimized ? modheightminimized : modheightfull) - 2, color = Color.BLACK.getRGB(), color);
		
		fontRenderer.drawString(Long.toString(getTimestamp()), x + 3, y + 6, Color.WHITE.getRGB());
		drawText(x + 10 + (type() ? modtimestampwidth : modtimestampwidth + modwidth), y + 6);
		GlStateManager.scale(scalefactor, scalefactor, scalefactor);
		
	}

	/**
	 * 
	 * Draw text
	 * 
	 * @param x xcoord
	 * @param y ycoord
	 */
	public abstract void drawText(int x, int y);
	
	public String getTip() {
		return hoverText;
	}
	
	public boolean isMinimized() {
		return minimized;
	}
	
	public void setMinimized(boolean bool) {
		minimized = bool;
	}
	
	public int getHeight() {
		return minimized ? modheightminimized / scalefactor: modheightfull / scalefactor;
	}
	
	public int getWidth() {
		return (modtimestampwidth + modwidth * 2) / scalefactor;
	}
	
	/**
	 * 
	 * @return unix timestamp of send/recieve packet
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * 
	 * @return type of packet: true for client, false for server
	 */
	public abstract boolean type();
	
	/**
	 * 
	 * @return when the mouse is overing over the module
	 */
	public boolean isHovered(int mouseX, int mouseY) {
		
		return mouseX > x && mouseX < x + getWidth() && mouseY > y && mouseY < y + getHeight();
		
	}
	
}
