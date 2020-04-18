package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSetExperience;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSetExperienceModule extends AbstractPacketModule {

	private SPacketSetExperience packet;
	
	public SPacketSetExperienceModule(SPacketSetExperience packet, long timestamp) {
		super("Sent when the client's exp should change.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSetExperience", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Bar Filled: " + packet.getExperienceBar(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Total Exp: " + packet.getTotalExperience(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Level: " + packet.getLevel(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
