package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSpawnMob;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSpawnMobModule extends AbstractPacketModule {
	
	private SPacketSpawnMob packet;
	
	public SPacketSpawnMobModule(SPacketSpawnMob packet, long timestamp) {
		super("Sent when a mob is spawned.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSpawnMob", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityID(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Entity UUID: " + packet.getUniqueId(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Entity Type: " + packet.getEntityType(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + (int)packet.getX() + " | " + (int)packet.getY() + " | " + (int)packet.getZ(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Velocity: " + packet.getVelocityX() + " | " + packet.getVelocityY() + " | " + packet.getVelocityZ(), x, y + 70, Color.WHITE.getRGB());
		
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
