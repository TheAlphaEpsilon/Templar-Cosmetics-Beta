package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketCustomPayload;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketCustomPayloadModule extends AbstractPacketModule {

	private CPacketCustomPayload packet;
	
	public CPacketCustomPayloadModule(CPacketCustomPayload packet, long timestamp) {
		
		super("Sent to change an item. (E.g. signing a book)", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketCustomPayload", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Channel: " + packet.getChannelName(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Data: " + packet.getBufferData().toString(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}
	
}
