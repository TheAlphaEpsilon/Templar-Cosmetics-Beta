package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketEntityTeleport;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEntityTeleportModule extends AbstractPacketModule {

	private SPacketEntityTeleport packet;
	
	public SPacketEntityTeleportModule(SPacketEntityTeleport packet, long timestamp) {
		super("Sent if an entity moves more than 8 blocks.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketEntityTeleport", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityId(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + (int)packet.getX() + " | " + (int)packet.getY() + " | " + (int)packet.getZ(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Yaw: " + packet.getYaw(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Pitch: " + packet.getPitch(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("On Ground: " + packet.getOnGround(), x, y + 70, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
