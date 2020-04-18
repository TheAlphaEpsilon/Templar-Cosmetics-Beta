package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketSteerBoat;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketSteerBoatModule extends AbstractPacketModule {

	private CPacketSteerBoat packet;
	
	public CPacketSteerBoatModule(CPacketSteerBoat packet, long timestamp) {
		
		super("Sent when steering a boat.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketSteerBoat", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Left: " + packet.getLeft(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Right: " + packet.getRight(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}


}
