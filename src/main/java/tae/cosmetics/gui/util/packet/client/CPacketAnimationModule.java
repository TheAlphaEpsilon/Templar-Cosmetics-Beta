package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketAnimation;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketAnimationModule extends AbstractPacketModule {

	private CPacketAnimation packet;
	
	public CPacketAnimationModule(CPacketAnimation packet, long timestamp) {
		super("Sent when the player's arm swings.", timestamp, packet);
		this.packet = packet;
	}
	
	@Override
	public boolean type() {
		return true;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketAnimation", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Hand: " + packet.getHand().toString(), x, y + 14, Color.WHITE.getRGB());
		}
	}

}
