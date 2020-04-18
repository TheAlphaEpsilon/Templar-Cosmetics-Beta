package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSpawnPlayer;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSpawnPlayerModule extends AbstractPacketModule {
	
	private SPacketSpawnPlayer packet;
	
	public SPacketSpawnPlayerModule(SPacketSpawnPlayer packet, long timestamp) {
		super("Spawns a player entity.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSpawnPlayer", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("ID: " + packet.getEntityID(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("UUID: " + packet.getUniqueId(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + (int) packet.getX() + " | " + (int)packet.getY() + " | " + (int)packet.getZ(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
