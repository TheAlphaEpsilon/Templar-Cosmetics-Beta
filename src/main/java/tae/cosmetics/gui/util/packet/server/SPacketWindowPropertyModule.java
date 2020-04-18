package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketWindowProperty;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketWindowPropertyModule extends AbstractPacketModule {
	
	private SPacketWindowProperty packet;
	
	public SPacketWindowPropertyModule(SPacketWindowProperty packet, long timestamp) {
		super("Updates window properties", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketWindowProperty", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Window ID: " + packet.getWindowId(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Property: " + packet.getProperty(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Value: " + packet.getValue(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
