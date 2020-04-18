package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketHeldItemChange;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketHeldItemChangeModule extends AbstractPacketModule {

	private CPacketHeldItemChange packet;
	
	public CPacketHeldItemChangeModule(CPacketHeldItemChange packet, long timestamp) {
		
		super("Sent when player changes hotbar slot.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketHeldItemChange", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Slot: " + packet.getSlotId(), x, y, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

	
}
