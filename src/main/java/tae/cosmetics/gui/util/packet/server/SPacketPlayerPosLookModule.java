package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketPlayerPosLookModule extends AbstractPacketModule {

	private SPacketPlayerPosLook packet;
	
	public SPacketPlayerPosLookModule(SPacketPlayerPosLook packet, long timestamp) {
		super("Updates the player position.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketPlayerPosLook", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Pos: " + (int)packet.getX() + " | " + (int)packet.getX() + " | " + (int)packet.getZ(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Yaw: " + packet.getYaw(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Pitch: " + packet.getPitch(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Teleport ID: " + packet.getTeleportId(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Flags: " + setToString(packet.getFlags()), x, y + 70, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
	private String setToString(Set<?> s) {
		
		StringBuffer buff = new StringBuffer();
		s.forEach(x -> buff.append(x.toString() + " "));
		return buff.toString();
		
	}
	
}
