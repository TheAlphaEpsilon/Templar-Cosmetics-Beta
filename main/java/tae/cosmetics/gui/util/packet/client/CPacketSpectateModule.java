package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketSpectate;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketSpectateModule extends AbstractPacketModule {

	private CPacketSpectate packet;
	
	public CPacketSpectateModule(CPacketSpectate packet, long timestamp) {
		
		super("Sent to teleport to an entity.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketSpectate", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			//fontRenderer.drawString("Entity: " + packet.getEntity(mc.server), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}


}
