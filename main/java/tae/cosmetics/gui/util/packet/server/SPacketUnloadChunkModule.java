package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketUnloadChunk;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketUnloadChunkModule extends AbstractPacketModule {
	
	private SPacketUnloadChunk packet;
	
	public SPacketUnloadChunkModule(SPacketUnloadChunk packet, long timestamp) {
		super("Unloads a chunk.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketUnloadChunk", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Pos: " + packet.getX() + " | " + packet.getZ(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
