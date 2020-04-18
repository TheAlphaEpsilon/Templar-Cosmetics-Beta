package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSoundEffect;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSoundEffectModule extends AbstractPacketModule {
	
	private SPacketSoundEffect packet;
	
	public SPacketSoundEffectModule(SPacketSoundEffect packet, long timestamp) {
		super("For hard-coded sound events.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSoundEffect", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Sound: " + packet.getSound(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Category: " + packet.getCategory(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Volume: " + packet.getVolume(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Pitch: " + packet.getPitch(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Sound Pos: " + (int)packet.getX() + " | " + (int)packet.getY() + " | " + (int)packet.getZ(), x, y + 70, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
