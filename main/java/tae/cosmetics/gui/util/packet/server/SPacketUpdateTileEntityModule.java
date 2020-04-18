package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketUpdateTileEntityModule extends AbstractPacketModule {
	
	private SPacketUpdateTileEntity packet;
	
	public SPacketUpdateTileEntityModule(SPacketUpdateTileEntity packet, long timestamp) {
		super("Updates entities.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketUpdateTileEntity", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("TODO: IMPLEMENT", x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
