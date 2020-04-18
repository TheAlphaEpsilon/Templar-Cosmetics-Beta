package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.status.server.SPacketServerInfo;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketServerInfoModule extends AbstractPacketModule {
	
	private SPacketServerInfo packet;
	
	public SPacketServerInfoModule(SPacketServerInfo packet, long timestamp) {
		super("Sends server information.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketServerInfo", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Response: " + packet.getResponse(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
