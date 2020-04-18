package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketBlockAction;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketBlockActionModule extends AbstractPacketModule {

	private SPacketBlockAction packet;
	
	public SPacketBlockActionModule(SPacketBlockAction packet, long timestamp) {
		super("Sent to update block animations.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketBlockAction", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Block Type: " + packet.getBlockType(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Block Pos: " + packet.getBlockPosition().getX() + " | " + packet.getBlockPosition().getY() + " | " + packet.getBlockPosition().getZ(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Noteblock Instrument: " + packet.getData1(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Noteblock Pitch: " + packet.getData2(), x, y + 56, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
