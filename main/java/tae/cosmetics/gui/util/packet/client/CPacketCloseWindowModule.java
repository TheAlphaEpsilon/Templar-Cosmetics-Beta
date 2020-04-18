package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketCloseWindow;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketCloseWindowModule extends AbstractPacketModule {

	private CPacketCloseWindow packet;
	
	public CPacketCloseWindowModule(CPacketCloseWindow packet, long timestamp) {
		
		super("Sent when closing a window.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketCloseWindow", x, y, Color.WHITE.getRGB());
	}

	@Override
	public boolean type() {
		return true;
	}

}
