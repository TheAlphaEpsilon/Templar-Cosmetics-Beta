package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketBlockChange;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketBlockChangeModule extends AbstractPacketModule {

	private SPacketBlockChange packet;
	
	public SPacketBlockChangeModule(SPacketBlockChange packet, long timestamp) {
		super("Sent when a block is changed.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketBlockChange", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Block Pos: " + packet.getBlockPosition().getX() + " | " + packet.getBlockPosition().getY() + " | " + packet.getBlockPosition().getZ(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Block State: " + packet.getBlockState(), x, y + 28, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
