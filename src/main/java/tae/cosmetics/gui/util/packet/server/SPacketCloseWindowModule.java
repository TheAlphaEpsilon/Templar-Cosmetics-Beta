package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketCloseWindow;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketCloseWindowModule extends AbstractPacketModule {

	private SPacketCloseWindow packet;
	
	public SPacketCloseWindowModule(SPacketCloseWindow packet, long timestamp) {
		super("Forces the client to close a window.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketCloseWindow", x, y, Color.WHITE.getRGB());

		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
