package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketEntityEquipment;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEntityEquipmentModule extends AbstractPacketModule {

	private SPacketEntityEquipment packet;
	
	public SPacketEntityEquipmentModule(SPacketEntityEquipment packet, long timestamp) {
		super("Equips Entities.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketEntityEquipment", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityID(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Slot: " + packet.getEquipmentSlot(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Item: " + packet.getItemStack(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
