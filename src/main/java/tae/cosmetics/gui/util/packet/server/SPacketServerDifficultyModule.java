package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketServerDifficulty;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketServerDifficultyModule extends AbstractPacketModule {

	private SPacketServerDifficulty packet;
	
	public SPacketServerDifficultyModule(SPacketServerDifficulty packet, long timestamp) {
		super("Changes the difficulty in client settings.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketServerDifficulty", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Difficulty: " + packet.getDifficulty(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
	
	
}
