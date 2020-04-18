package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketPlayer;
import tae.cosmetics.Globals;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketPlayerModule extends AbstractPacketModule implements Globals {
	
	private CPacketPlayer packet;
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;
	
	public CPacketPlayerModule(CPacketPlayer packet, long timestamp) {
		
		super("Updates player position and rotation.", timestamp, packet);
		this.packet = packet;
		x = mc.player.posX;
		y = mc.player.posY;
		z = mc.player.posZ;
		pitch = mc.player.rotationPitch;
		yaw = mc.player.rotationYaw;
		
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketPlayer", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Pos: " + (int)packet.getX(this.x) + " | " + (int)packet.getY(this.y) + " | " +  (int)packet.getZ(this.z), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Yaw: " + packet.getYaw(yaw), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Pitch: " + packet.getPitch(pitch), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
