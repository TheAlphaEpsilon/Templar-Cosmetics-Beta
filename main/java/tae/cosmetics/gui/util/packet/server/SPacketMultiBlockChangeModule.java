package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketMultiBlockChange;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketMultiBlockChangeModule extends AbstractPacketModule {
	
	private SPacketMultiBlockChange packet;
	
	public SPacketMultiBlockChangeModule(SPacketMultiBlockChange packet, long timestamp) {
		super("Sent when 2+ blocks are changed in the same chunk on the same tick.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketMultiBlockChange", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Blocks: " + packet.getChangedBlocks(), x, y, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
