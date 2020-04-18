package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketPlayerAbilities;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketPlayerAbilitiesModule extends AbstractPacketModule {

	private CPacketPlayerAbilities packet;
	
	public CPacketPlayerAbilitiesModule(CPacketPlayerAbilities packet, long timestamp) {
		
		super("Sent to update what the player can do.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketPlayerAbilities", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Can Fly: " + packet.isAllowFlying(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Creative Mode: " + packet.isCreativeMode(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Is Flying: " + packet.isFlying(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

	
}
