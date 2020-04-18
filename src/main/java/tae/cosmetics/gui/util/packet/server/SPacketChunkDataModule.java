package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketChunkData;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketChunkDataModule extends AbstractPacketModule {

	private SPacketChunkData packet;
	
	public SPacketChunkDataModule(SPacketChunkData packet, long timestamp) {
		super("Sends chunk information.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketChunkData", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Chunk Coord: " + packet.getChunkX() + " | " + packet.getChunkZ(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Size: " + packet.getExtractedSize(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Tile Entities: " + ((packet.getTileEntityTags() == null) ? 0 : packet.getTileEntityTags().size()), x, y + 42, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
