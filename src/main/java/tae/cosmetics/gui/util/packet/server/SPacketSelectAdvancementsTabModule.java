package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSelectAdvancementsTabModule extends AbstractPacketModule {

	private SPacketSelectAdvancementsTab packet;
	
	public SPacketSelectAdvancementsTabModule(SPacketSelectAdvancementsTab packet, long timestamp) {
		super("Tells client to switch to an advancement tab.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSelectAdvancementsTab", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Tab: " + packet.getTab(), x, y, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
}
