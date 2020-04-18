package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.status.server.SPacketPong;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketPongModule extends AbstractPacketModule {

	private SPacketPong packet;
	
	public SPacketPongModule(SPacketPong packet, long timestamp) {
		super("Response to CPacketPing.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketPong", x, y, Color.WHITE.getRGB());
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
