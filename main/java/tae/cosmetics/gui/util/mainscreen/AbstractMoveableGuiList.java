package tae.cosmetics.gui.util.mainscreen;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public abstract class AbstractMoveableGuiList {

	protected Minecraft mc = Minecraft.getMinecraft();
	
	protected ScaledResolution scale = new ScaledResolution(mc);
	
	protected FontRenderer fontRenderer = mc.fontRenderer;
	
	/**
	 * The x position
	 */
	public int x;
	
	/**
	 * The y position
	 */
	public int y;
	
	private String title;
	private int color;
	private int width;
	
	protected AbstractMoveableGuiList(String title, int startingX, int startingY, int color) {
		this.title = title;
		this.x = startingX;
		this.y = startingY;
		this.color = color;
		width = fontRenderer.getStringWidth(title);
	}
	
	public void draw(int mouseX, int mouseY) {
		if(isHovered(mouseX, mouseY)) {
			Gui.drawRect(x, y, x + width, y + fontRenderer.FONT_HEIGHT, Color.YELLOW.getRGB());
		}
		fontRenderer.drawString(title, x, y, color);
		drawList();
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		
		return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + fontRenderer.FONT_HEIGHT;
		
	}
	
	public String getTitle() {
		return title;
	}
	
	protected abstract void drawList();
	
}
