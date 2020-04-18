package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketCustomPayload;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketCustomPayloadModule extends AbstractPacketModule {

	private SPacketCustomPayload packet;
	
	public SPacketCustomPayloadModule(SPacketCustomPayload packet, long timestamp) {
		super("Various uses.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketCustomPayload", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Channel: " + packet.getChannelName(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawSplitString("Data: " + packet.getBufferData().toString(), x, y + 28, AbstractPacketModule.modtimestampwidth - 10, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
}
