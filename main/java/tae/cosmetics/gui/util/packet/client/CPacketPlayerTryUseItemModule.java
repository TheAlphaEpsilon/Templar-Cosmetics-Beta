package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketPlayerTryUseItemModule extends AbstractPacketModule {

	private CPacketPlayerTryUseItem packet;
	
	public CPacketPlayerTryUseItemModule(CPacketPlayerTryUseItem packet, long timestamp) {
		
		super("Sent when the player tries to use an item.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketPlayerTryUseItem", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Hand: " + packet.getHand(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
