package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketAnimation;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketAnimationModule extends AbstractPacketModule {
	
	private SPacketAnimation packet;
	
	public SPacketAnimationModule(SPacketAnimation packet, long timestamp) {
		super("Sent when an animation should change.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketAnimation", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityID(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Animation ID: " + packet.getAnimationType(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
