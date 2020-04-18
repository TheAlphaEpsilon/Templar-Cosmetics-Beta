package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSpawnExperienceOrbModule extends AbstractPacketModule {
	
	private SPacketSpawnExperienceOrb packet;
	
	public SPacketSpawnExperienceOrbModule(SPacketSpawnExperienceOrb packet, long timestamp) {
		super("Spawns an experience orb.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSpawnExperienceOrb", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityID(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("XP Amount: " + packet.getXPValue(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + packet.getX() + " | " + packet.getY() + " | " + packet.getZ(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
