package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketStatistics;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketStatisticsModule extends AbstractPacketModule {
	
	private SPacketStatistics packet;
	
	public SPacketStatisticsModule(SPacketStatistics packet, long timestamp) {
		super("Response to Client Status.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketStatistics", x, y, Color.WHITE.getRGB());
		if(!minimized) {
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
