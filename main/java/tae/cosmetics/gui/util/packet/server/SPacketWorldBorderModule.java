package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketWorldBorder;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketWorldBorderModule extends AbstractPacketModule {
	
	private SPacketWorldBorder packet;
	
	public SPacketWorldBorderModule(SPacketWorldBorder packet, long timestamp) {
		super("World border data", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketWorldBorder", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("TODO: IMPLEMENT", x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
