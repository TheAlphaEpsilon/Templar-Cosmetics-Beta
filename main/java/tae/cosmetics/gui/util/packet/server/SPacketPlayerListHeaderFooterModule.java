package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketPlayerListHeaderFooterModule extends AbstractPacketModule {

	private SPacketPlayerListHeaderFooter packet;
	
	public SPacketPlayerListHeaderFooterModule(SPacketPlayerListHeaderFooter packet, long timestamp) {
		super("Additional player list information", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketPlayerListHeaderFooter", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Header: " + packet.getHeader().getFormattedText(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Footer: " + packet.getFooter().getFormattedText(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
