package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketUseEntity;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketUseEntityModule extends AbstractPacketModule {

	private CPacketUseEntity packet;
	
	public CPacketUseEntityModule(CPacketUseEntity packet, long timestamp) {
		
		super("Sent when a player interacts with an entity.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketUseEntity", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Action: " + packet.getAction(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Hand: " + packet.getHand(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Click Vector: " + packet.getHitVec().x + "|" + packet.getHitVec().y + "|" + packet.getHitVec().z, x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}
	
}
