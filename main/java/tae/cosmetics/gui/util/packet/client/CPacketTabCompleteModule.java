package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketTabComplete;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketTabCompleteModule extends AbstractPacketModule {

	private CPacketTabComplete packet;
	
	public CPacketTabCompleteModule(CPacketTabComplete packet, long timestamp) {
		
		super("Self Explanatory", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketTabComplete", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Message: " + packet.getMessage(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Has Block: " + packet.hasTargetBlock(), x, y + 28, Color.WHITE.getRGB());
			if(packet.hasTargetBlock()) {
				fontRenderer.drawString("Block Pos: " + packet.getTargetBlock().getX() + "|" + packet.getTargetBlock().getY() + "|" + packet.getTargetBlock().getZ(), x, y + 42, Color.WHITE.getRGB());
			}
		}
	}

	@Override
	public boolean type() {
		return true;
	}


}
