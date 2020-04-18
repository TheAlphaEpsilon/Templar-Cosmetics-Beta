package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketChangeGameState;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketChangeGameStateModule extends AbstractPacketModule {

	private SPacketChangeGameState packet;
	
	public SPacketChangeGameStateModule(SPacketChangeGameState packet, long timestamp) {
		super("Changes the player's state.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		
		fontRenderer.drawString("SPacketChangeGameState", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("State ID: " + packet.getGameState(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("State Value: " + packet.getValue(), x, y + 28, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
