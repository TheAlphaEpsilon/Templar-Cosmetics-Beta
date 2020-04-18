package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketConfirmTeleport;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketConfirmTeleportModule extends AbstractPacketModule {

	private CPacketConfirmTeleport packet;
	
	public CPacketConfirmTeleportModule(CPacketConfirmTeleport packet, long timestamp) {
		
		super("Sent as a confirmation of a server teleport", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketConfirmTeleport", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("ID: " + packet.getTeleportId(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
