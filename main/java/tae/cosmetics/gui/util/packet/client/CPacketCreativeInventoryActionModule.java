package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketCreativeInventoryActionModule extends AbstractPacketModule {

	private CPacketCreativeInventoryAction packet;
	
	public CPacketCreativeInventoryActionModule(CPacketCreativeInventoryAction packet, long timestamp) {
		
		super("Sent while in creative mode.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketCreativeInventoryAction", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Slot ID: " + packet.getSlotId(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Item Stack: " + packet.getStack().getDisplayName(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
