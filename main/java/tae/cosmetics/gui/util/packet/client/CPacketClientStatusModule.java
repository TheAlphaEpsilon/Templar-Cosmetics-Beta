package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketClientStatus;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketClientStatusModule extends AbstractPacketModule {

	private CPacketClientStatus packet;
	
	public CPacketClientStatusModule(CPacketClientStatus packet, long timestamp) {
		
		super("Misc Actions", timestamp, packet);
		this.packet = packet;
	}
	
	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketClientStatus", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Status: " + packet.getStatus(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
