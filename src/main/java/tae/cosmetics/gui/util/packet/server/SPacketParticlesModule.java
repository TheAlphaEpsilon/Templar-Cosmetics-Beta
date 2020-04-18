package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketParticles;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketParticlesModule extends AbstractPacketModule {

	private SPacketParticles packet;
	
	public SPacketParticlesModule(SPacketParticles packet, long timestamp) {
		super("Sent to show particles.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketParticles", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Type: " + packet.getParticleType(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + (int)packet.getXCoordinate() + " | " + (int)packet.getYCoordinate() + " | " + (int)packet.getZCoordinate(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Offset: " + (int)packet.getXOffset() + " | " + (int)packet.getYOffset() + " | " + (int)packet.getZOffset(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Speed: " + packet.getParticleSpeed(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Amount: " + packet.getParticleCount(), x, y + 70, Color.WHITE.getRGB());
			fontRenderer.drawString("Args: " + packet.getParticleArgs(), x, y + 84, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
}
