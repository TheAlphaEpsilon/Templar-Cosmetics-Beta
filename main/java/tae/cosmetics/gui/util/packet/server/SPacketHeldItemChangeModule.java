package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketHeldItemChange;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketHeldItemChangeModule extends AbstractPacketModule {

	private SPacketHeldItemChange packet;
	
	public SPacketHeldItemChangeModule(SPacketHeldItemChange packet, long timestamp) {
		super("Changes player's held item", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketHeldItemChange", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Hotbar Index: " + packet.getHeldItemHotbarIndex(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
