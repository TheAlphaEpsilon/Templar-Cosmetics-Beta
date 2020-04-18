package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketUpdateSign;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketUpdateSignModule extends AbstractPacketModule {

	private CPacketUpdateSign packet;
	
	public CPacketUpdateSignModule(CPacketUpdateSign packet, long timestamp) {
		
		super("Sent when placing a sign.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketUpdateSignModule", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Sign Pos: " + packet.getPosition().getX() + "|" + packet.getPosition().getY() + "|" + packet.getPosition().getZ(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString(packet.getLines()[0], x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString(packet.getLines()[1], x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString(packet.getLines()[2], x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString(packet.getLines()[3], x, y + 28, Color.WHITE.getRGB());

		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
