package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketKeepAlive;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketKeepAliveModule extends AbstractPacketModule {

	private CPacketKeepAlive packet;
	
	public CPacketKeepAliveModule(CPacketKeepAlive packet, long timestamp) {
		
		super("Sent to stay in server.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketKeepAlive", x, y, Color.WHITE.getRGB());
		/*
		if(!minimized) {
			fontRenderer.drawString("Random Key: " + packet.getKey(), x, y, Color.WHITE.getRGB());
		}
		*/
	}

	@Override
	public boolean type() {
		return true;
	}

}
