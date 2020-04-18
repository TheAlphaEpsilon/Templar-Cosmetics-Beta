package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketUpdateScore;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketUpdateScoreModule extends AbstractPacketModule {
	
	private SPacketUpdateScore packet;
	
	public SPacketUpdateScoreModule(SPacketUpdateScore packet, long timestamp) {
		super("Updates scoreboard item", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketUpdateScore", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Player: " + packet.getPlayerName(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Objective: " + packet.getObjectiveName(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Action: " + packet.getScoreAction(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Value: " + packet.getScoreValue(), x, y + 56, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
