package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketUpdateHealth;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketUpdateHealthModule extends AbstractPacketModule {
	
	private SPacketUpdateHealth packet;
	
	public SPacketUpdateHealthModule(SPacketUpdateHealth packet, long timestamp) {
		super("Updates the player's health", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketUpdateHealth", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Health: " + packet.getHealth(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Food: " + packet.getFoodLevel(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Sat: " + packet.getSaturationLevel(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
