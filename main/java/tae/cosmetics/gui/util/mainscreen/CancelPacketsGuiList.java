package tae.cosmetics.gui.util.mainscreen;

import java.awt.Color;

import net.minecraft.network.Packet;
import tae.cosmetics.gui.GuiCancelPackets;

public class CancelPacketsGuiList extends AbstractMoveableGuiList {

	private static int color = Color.RED.getRGB();
	
	public CancelPacketsGuiList(String title, int startingX, int startingY) {
		super(title, startingX, startingY, color);
	}

	@Override
	protected void drawList() {
		int startY = y + fontRenderer.FONT_HEIGHT + 5;
		
		int counter = 0;
		
		for(Class<? extends Packet<?>> clazz : GuiCancelPackets.instance().getCancelPackets()) {
			
			mc.fontRenderer.drawString(clazz.getSimpleName(), x, startY + counter++ * (fontRenderer.FONT_HEIGHT + 5) , color);
			
		}
	}

	
	
}
