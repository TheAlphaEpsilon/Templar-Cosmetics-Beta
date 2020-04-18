package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.status.client.CPacketPing;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketPingModule extends AbstractPacketModule {

	private CPacketPing packet;
	
	public CPacketPingModule(CPacketPing packet, long timestamp) {
		
		super("Self Explanatory", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketPing", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Client Time: " + packet.getClientTime(), x, y, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}
	
}
