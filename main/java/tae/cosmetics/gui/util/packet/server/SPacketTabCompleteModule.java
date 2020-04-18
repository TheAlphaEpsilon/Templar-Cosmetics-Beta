package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;
import java.util.Arrays;

import net.minecraft.network.play.server.SPacketTabComplete;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketTabCompleteModule extends AbstractPacketModule {

	private SPacketTabComplete packet;
	
	public SPacketTabCompleteModule(SPacketTabComplete packet, long timestamp) {
		super("Response to CPacketTabComplete", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketTabComplete", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawSplitString("Matches: " + Arrays.toString(packet.getMatches()), x, y + 14, AbstractPacketModule.modwidth - 10, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
