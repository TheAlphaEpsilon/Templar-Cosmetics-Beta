package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketTimeUpdate;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketTimeUpdateModule extends AbstractPacketModule {
	
	private SPacketTimeUpdate packet;
	
	public SPacketTimeUpdateModule(SPacketTimeUpdate packet, long timestamp) {
		super("Syncs client-server in-game time.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketTimeUpdate", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Time: " + packet.getWorldTime(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Total Time: " + packet.getTotalWorldTime(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
