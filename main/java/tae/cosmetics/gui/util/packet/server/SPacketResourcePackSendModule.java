package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketResourcePackSend;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketResourcePackSendModule extends AbstractPacketModule {
	
	private SPacketResourcePackSend packet;
	
	public SPacketResourcePackSendModule(SPacketResourcePackSend packet, long timestamp) {
		super("Sent to give the client a resource pack.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketResourcePackSend", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Hash: " + packet.getHash(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("URL: " + packet.getURL(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
