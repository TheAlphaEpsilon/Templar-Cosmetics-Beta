package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketTitle;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketTitleModule extends AbstractPacketModule {
	
	private SPacketTitle packet;
	
	public SPacketTitleModule(SPacketTitle packet, long timestamp) {
		super("Displays a message on screen", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketTitle", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Message: " + packet.getMessage().getFormattedText(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Type: " + packet.getType(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Fade In: " + packet.getFadeInTime(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Time: " + packet.getDisplayTime(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Fade Out: " + packet.getFadeOutTime(), x, y + 70, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
