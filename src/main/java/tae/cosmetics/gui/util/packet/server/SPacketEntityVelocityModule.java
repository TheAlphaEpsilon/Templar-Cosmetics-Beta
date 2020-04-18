package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketEntityVelocity;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEntityVelocityModule extends AbstractPacketModule {

	private SPacketEntityVelocity packet;
	
	public SPacketEntityVelocityModule(SPacketEntityVelocity packet, long timestamp) {
		super("Velocity in terms of 1/8000 blocks.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketEntityVelocity", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityID(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("X: " + packet.getMotionX(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Y: " + packet.getMotionY(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Z: " + packet.getMotionZ(), x, y + 56, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
