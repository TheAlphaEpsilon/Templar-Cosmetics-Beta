package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketWindowItems;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketWindowItemsModule extends AbstractPacketModule {
	
	private SPacketWindowItems packet;
	
	public SPacketWindowItemsModule(SPacketWindowItems packet, long timestamp) {
		super("Send when multiple items are added/removed.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketWindowItems", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("ID: " + packet.getWindowId(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
