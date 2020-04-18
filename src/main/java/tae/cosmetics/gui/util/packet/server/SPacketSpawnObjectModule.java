package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSpawnObject;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSpawnObjectModule extends AbstractPacketModule {
	
	private SPacketSpawnObject packet;
	
	public SPacketSpawnObjectModule(SPacketSpawnObject packet, long timestamp) {
		super("Spawns non-mob entities.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSpawnObject", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityID(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("UUID: " + packet.getUniqueId(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + (int)packet.getX() + " | " + (int)packet.getY() + " | " + (int)packet.getZ(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Velocity: " + packet.getSpeedX() + " | " + packet.getSpeedY() + " | " + packet.getSpeedZ(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Data: " + packet.getData(), x, y + 70, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
