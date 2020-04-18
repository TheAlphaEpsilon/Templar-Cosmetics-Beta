package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketRespawn;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketRespawnModule extends AbstractPacketModule {
	
	private SPacketRespawn packet;
	
	public SPacketRespawnModule(SPacketRespawn packet, long timestamp) {
		super("Changes player dimension.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketRespawn", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Dimension ID: " + packet.getDimensionID(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Difficulty: " + packet.getDifficulty(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Game Type: " + packet.getGameType(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("World Type: " + packet.getWorldType(), x, y + 56, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
