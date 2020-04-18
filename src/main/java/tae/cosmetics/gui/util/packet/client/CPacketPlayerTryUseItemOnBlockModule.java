package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketPlayerTryUseItemOnBlockModule extends AbstractPacketModule {

	private CPacketPlayerTryUseItemOnBlock packet;
	
	public CPacketPlayerTryUseItemOnBlockModule(CPacketPlayerTryUseItemOnBlock packet, long timestamp) {
		
		super("Sent when a player right-clicks a block.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketPlayerTryUseItemOnBlock", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Block Face: " + packet.getDirection(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("X Pitch: " + packet.getFacingX(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Y Pitch: " + packet.getFacingY(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Z Pitch: " + packet.getFacingZ(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Hand: " + packet.getHand(), x, y + 70, Color.WHITE.getRGB());
			fontRenderer.drawString("Block Pos: " + packet.getPos().getX() + "|" + packet.getPos().getY() + "|" + packet.getPos().getZ(), x, y + 84, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
