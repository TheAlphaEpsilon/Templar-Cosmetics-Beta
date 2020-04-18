package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSpawnPosition;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSpawnPositionModule extends AbstractPacketModule {
	
	private SPacketSpawnPosition packet;
	
	public SPacketSpawnPositionModule(SPacketSpawnPosition packet, long timestamp) {
		super("Sets the server spawn.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSpawnPosition", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Spawn Pos: " + packet.getSpawnPos(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
