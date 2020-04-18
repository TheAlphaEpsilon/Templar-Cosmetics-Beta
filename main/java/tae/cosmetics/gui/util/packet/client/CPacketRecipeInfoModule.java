package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketRecipeInfo;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketRecipeInfoModule extends AbstractPacketModule {

	private CPacketRecipeInfo packet;
	
	public CPacketRecipeInfoModule(CPacketRecipeInfo packet, long timestamp) {
		
		super("Sent when the recipe book is opened.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketRecipeInfoModule", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Recipe: " + packet.getRecipe(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Purpose: " + packet.getPurpose(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Filtering: " + packet.isFilteringCraftable(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Gui Open: " + packet.isGuiOpen(), x, y + 56, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return true;
	}

}
