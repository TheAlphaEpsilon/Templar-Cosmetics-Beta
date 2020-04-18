package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketEntityEffect;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEntityEffectModule extends AbstractPacketModule {

	private SPacketEntityEffect packet;
	
	public SPacketEntityEffectModule(SPacketEntityEffect packet, long timestamp) {
		super("Sent to give an effect to an entity.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketEntityEffect", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityId(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Effect ID: " + packet.getEffectId(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Amplifier: " + packet.getAmplifier(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Duration: " + packet.getDuration(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Ambient: " + packet.getIsAmbient(), x, y + 70, Color.WHITE.getRGB());
			fontRenderer.drawString("Particles: " + packet.doesShowParticles(), x, y + 84, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
