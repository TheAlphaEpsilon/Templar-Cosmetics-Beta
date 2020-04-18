package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketResourcePackStatus;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketResourcePackStatusModule extends AbstractPacketModule {

	private CPacketResourcePackStatus packet;
	
	public CPacketResourcePackStatusModule(CPacketResourcePackStatus packet, long timestamp) {
		
		super("Sent as a response to a server resource pack.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketResourcePackStatus", x, y, Color.WHITE.getRGB());
	}

	@Override
	public boolean type() {
		return true;
	}
}
