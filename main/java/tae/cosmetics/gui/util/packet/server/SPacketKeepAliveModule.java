package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketKeepAlive;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketKeepAliveModule extends AbstractPacketModule {

	private SPacketKeepAlive packet;
	
	public SPacketKeepAliveModule(SPacketKeepAlive packet, long timestamp) {
		super("Sent to see if the player is still responding.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketKeepAlive", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			//fontRenderer.drawString("Rand ID: " + packet.getId(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
