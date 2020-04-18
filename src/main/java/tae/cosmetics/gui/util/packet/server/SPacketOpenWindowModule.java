package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketOpenWindow;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketOpenWindowModule extends AbstractPacketModule {

	private SPacketOpenWindow packet;
	
	public SPacketOpenWindowModule(SPacketOpenWindow packet, long timestamp) {
		super("Sent to tell the client to open a window.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketOpenWindow", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Title: " + packet.getWindowTitle().getFormattedText(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("ID: " + packet.getWindowId(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Slots: " + packet.getSlotCount(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Gui ID: " + packet.getGuiId(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Entity ID: " + packet.getEntityId(), x, y + 70, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
