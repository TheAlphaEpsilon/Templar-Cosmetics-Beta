package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSetSlot;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSetSlotModule extends AbstractPacketModule {

	private SPacketSetSlot packet;
	
	public SPacketSetSlotModule(SPacketSetSlot packet, long timestamp) {
		super("Removes/Adds an item to a slot.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSetSlot", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Window ID: " + packet.getWindowId(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Slot ID: " + packet.getSlot(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Item: " + packet.getStack(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
