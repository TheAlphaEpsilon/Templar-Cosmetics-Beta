package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSpawnGlobalEntityModule extends AbstractPacketModule {
	
	private SPacketSpawnGlobalEntity packet;
	
	public SPacketSpawnGlobalEntityModule(SPacketSpawnGlobalEntity packet, long timestamp) {
		super("Spawns Lightning", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSpawnGlobalEntity", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityId(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Type: " + packet.getType(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + (int)packet.getX() + " | " + (int)packet.getY() + " | " + (int)packet.getZ(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
