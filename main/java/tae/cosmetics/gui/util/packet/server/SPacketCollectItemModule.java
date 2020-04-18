package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketCollectItem;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketCollectItemModule extends AbstractPacketModule {

	private SPacketCollectItem packet;
	
	public SPacketCollectItemModule(SPacketCollectItem packet, long timestamp) {
		super("Tells client to show pick-up animation.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketCollectItem", x, y, Color.WHITE.getRGB());
		
		if(!minimized) {
			fontRenderer.drawString("Collector Entity ID: " + packet.getEntityID(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Item Entity ID: " + packet.getCollectedItemEntityID(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Amount: " + packet.getAmount(), x, y + 42, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
