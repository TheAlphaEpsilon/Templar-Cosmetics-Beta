package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketCustomSound;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketCustomSoundModule extends AbstractPacketModule {

	private SPacketCustomSound packet;
	
	public SPacketCustomSoundModule(SPacketCustomSound packet, long timestamp) {
		super("Tells client to play sound.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketCustomSound", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Name: " + packet.getSoundName(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Category: " + packet.getCategory(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Pitch: " + packet.getPitch(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Volume: " + packet.getVolume(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Direction: " + (int)packet.getX() + " | " + (int)packet.getY() + " | " + (int)packet.getZ(), x, y, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
