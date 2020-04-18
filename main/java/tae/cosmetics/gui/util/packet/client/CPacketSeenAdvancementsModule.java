package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketSeenAdvancements;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketSeenAdvancementsModule extends AbstractPacketModule {

	private CPacketSeenAdvancements packet;
	
	public CPacketSeenAdvancementsModule(CPacketSeenAdvancements packet, long timestamp) {
		
		super("Sent when opening the advancements tab.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketSeenAdvancements", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Action: " + packet.getAction(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Tab: " + packet.getTab(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
