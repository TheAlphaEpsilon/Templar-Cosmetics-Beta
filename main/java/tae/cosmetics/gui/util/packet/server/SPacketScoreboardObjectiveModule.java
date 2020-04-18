package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketScoreboardObjective;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketScoreboardObjectiveModule extends AbstractPacketModule {
	
	private SPacketScoreboardObjective packet;
	
	public SPacketScoreboardObjectiveModule(SPacketScoreboardObjective packet, long timestamp) {
		super("Updates scoreboard objectives.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketScoreboardObjective", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Name: " + packet.getObjectiveName(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Value: " + packet.getObjectiveValue(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Action: " + packet.getAction(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Type: " + packet.getRenderType(), x, y + 56, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
