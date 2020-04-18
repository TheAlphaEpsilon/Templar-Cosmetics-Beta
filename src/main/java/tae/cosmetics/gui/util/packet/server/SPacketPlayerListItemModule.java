package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketPlayerListItem;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketPlayerListItemModule extends AbstractPacketModule {

	private SPacketPlayerListItem packet;
	
	public SPacketPlayerListItemModule(SPacketPlayerListItem packet, long timestamp) {
		super("Sent to add/remove players", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketPlayerListItem", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Action: " + packet.getAction(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Player: " + packet.getEntries().get(0).getProfile().getName(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("UUID: " + packet.getEntries().get(0).getProfile().getId(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Ping: " + packet.getEntries().get(0).getPing(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Gamemode: " + packet.getEntries().get(0).getGameMode(), x, y + 72, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
