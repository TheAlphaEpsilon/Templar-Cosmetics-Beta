package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketBlockBreakAnimModule extends AbstractPacketModule {

	private SPacketBlockBreakAnim packet;
	
	public SPacketBlockBreakAnimModule(SPacketBlockBreakAnim packet, long timestamp) {
		super("Sent to update cracks when breaking blocks.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		
		fontRenderer.drawString("SPacketBlockBreakAnim", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Breaker ID: " + packet.getBreakerId(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Block Pos: " + packet.getPosition().getX() + " | " + packet.getPosition().getY() + " | " + packet.getPosition().getZ(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Progress: " + packet.getProgress(), x, y + 42, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
