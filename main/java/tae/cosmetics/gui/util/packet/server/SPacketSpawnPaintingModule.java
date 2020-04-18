package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSpawnPainting;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSpawnPaintingModule extends AbstractPacketModule {

	private SPacketSpawnPainting packet;
	
	public SPacketSpawnPaintingModule(SPacketSpawnPainting packet, long timestamp) {
		super("Spawns paintings", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSpawnPainting", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Title: " + packet.getTitle(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Entity ID: " + packet.getEntityID(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Unique ID: " + packet.getUniqueId(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Facing: " + packet.getFacing(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + packet.getPosition(), x, y + 70, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
