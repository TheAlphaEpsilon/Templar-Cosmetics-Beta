package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketUpdateBossInfoModule extends AbstractPacketModule {
	
	private SPacketUpdateBossInfo packet;
	
	public SPacketUpdateBossInfoModule(SPacketUpdateBossInfo packet, long timestamp) {
		super("Updates boss info", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketUpdateBossInfo", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Name: " + packet.getName().getFormattedText(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("UUID: " + packet.getUniqueId(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Percent: " + packet.getPercent(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Operation: " + packet.getOperation(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Overlay: " + packet.getOverlay(), x, y + 70, Color.WHITE.getRGB());
			fontRenderer.drawString("Flags: " + packet.shouldCreateFog() + " | " + packet.shouldDarkenSky() + " | " + packet.shouldPlayEndBossMusic(), x, y + 84, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
