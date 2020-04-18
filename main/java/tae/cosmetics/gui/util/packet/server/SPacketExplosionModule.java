package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketExplosion;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketExplosionModule extends AbstractPacketModule {

	private SPacketExplosion packet;
	
	public SPacketExplosionModule(SPacketExplosion packet, long timestamp) {
		super("Sent when an explosion occurs.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketExplosion", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Pos: " + (int)packet.getX() + " | " + (int)packet.getY() + " | " + (int)packet.getZ(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Strength: " + packet.getStrength(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("X motion: " + packet.getMotionX(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Y motion: " + packet.getMotionY(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Z motion: " + packet.getMotionZ(), x, y + 70, Color.WHITE.getRGB());
			if(packet.getAffectedBlockPositions() != null) {
				fontRenderer.drawString("Affected Blocks: " + packet.getAffectedBlockPositions().size(), x, y + 84, Color.WHITE.getRGB());
			}
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
