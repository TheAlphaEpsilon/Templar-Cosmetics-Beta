package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketPlayerDigging;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketPlayerDiggingModule extends AbstractPacketModule {

	private CPacketPlayerDigging packet;
	
	public CPacketPlayerDiggingModule(CPacketPlayerDigging packet, long timestamp) {
		
		super("Sent when a player mines a block.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketPlayerDigging", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Action: " + packet.getAction(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Block Face: " + packet.getFacing(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Block Pos: " + packet.getPosition().getX() + " | " + packet.getPosition().getY() + " | " + packet.getPosition().getZ(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
