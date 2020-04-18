package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketEntityAttach;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEntityAttachModule extends AbstractPacketModule {

	private SPacketEntityAttach packet;
	
	public SPacketEntityAttachModule(SPacketEntityAttach packet, long timestamp) {
		super("Sent when an entity is leashed.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketEntityAttach", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Main Entity ID: " + packet.getEntityId(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Vehicle Entity ID: " + packet.getVehicleEntityId(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
