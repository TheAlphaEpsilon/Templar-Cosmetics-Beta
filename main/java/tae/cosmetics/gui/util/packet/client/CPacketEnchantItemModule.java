package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketEnchantItem;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketEnchantItemModule extends AbstractPacketModule {

	private CPacketEnchantItem packet;
	
	public CPacketEnchantItemModule(CPacketEnchantItem packet, long timestamp) {
		
		super("Sent when enchanting an item.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketEnchantItem", x, y, Color.white.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Button ID: " + packet.getButton(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Window ID: " + packet.getWindowId(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
