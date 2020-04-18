package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketEntity;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEntityModule extends AbstractPacketModule {

	private SPacketEntity packet;
	
	public SPacketEntityModule(SPacketEntity packet, long timestamp) {
		super("Data on various entities.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketEntity", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity: " + packet.getEntity(Minecraft.getMinecraft().world), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + packet.getX() + " | " + packet.getY() + " | " + packet.getZ(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Pitch: " + packet.getPitch(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Yaw: " + packet.getYaw(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("On Ground: " + packet.getOnGround(), x, y + 70, Color.WHITE.getRGB());
			fontRenderer.drawString("Rotating: " + packet.isRotating(), x, y + 84, Color.WHITE.getRGB());
			
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
