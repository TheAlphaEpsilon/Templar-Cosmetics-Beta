package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketRecipeBook;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketRecipeBookModule extends AbstractPacketModule {

	private SPacketRecipeBook packet;
	
	public SPacketRecipeBookModule(SPacketRecipeBook packet, long timestamp) {
		super("Recipe Book information.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketRecipeBook", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Open: " + packet.isGuiOpen(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("State: " + packet.getState(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Filtering: " + packet.isFilteringCraftable(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
