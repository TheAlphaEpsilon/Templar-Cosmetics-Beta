package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;
import java.util.Arrays;

import net.minecraft.network.play.server.SPacketDestroyEntities;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketDestroyEntitiesModule extends AbstractPacketModule {

	private SPacketDestroyEntities packet;
	
	public SPacketDestroyEntitiesModule(SPacketDestroyEntities packet, long timestamp) {
		super("Used when entities despawn.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketDestroyEntities", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawSplitString("IDs: " + Arrays.toString(packet.getEntityIDs()), x, y, AbstractPacketModule.modheightfull - 10, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
