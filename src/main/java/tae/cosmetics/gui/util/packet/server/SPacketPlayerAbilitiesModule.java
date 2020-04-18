package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketPlayerAbilities;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketPlayerAbilitiesModule extends AbstractPacketModule {

	private SPacketPlayerAbilities packet;
	
	public SPacketPlayerAbilitiesModule(SPacketPlayerAbilities packet, long  timestamp) {
		super("Sent to update what the player can do.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketPlayerAbilities", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Can Fly: " + packet.isAllowFlying(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Is Flying: " + packet.isFlying(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Creative: " + packet.isCreativeMode(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("God Mode: " + packet.isInvulnerable(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Walk Speed: " + packet.getWalkSpeed(), x, y + 70, Color.WHITE.getRGB());
			fontRenderer.drawString("Fly Speed: " + packet.getFlySpeed(), x, y + 84, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
