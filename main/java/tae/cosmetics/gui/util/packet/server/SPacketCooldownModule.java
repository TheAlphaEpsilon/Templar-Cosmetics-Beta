package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketCooldown;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketCooldownModule extends AbstractPacketModule {

	private SPacketCooldown packet;
	
	public SPacketCooldownModule(SPacketCooldown packet, long timestamp) {
		super("Sets a cooldown timer for items.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketCooldown", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Item: " + packet.getItem(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Ticks: " + packet.getTicks(), x, y + 28, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
