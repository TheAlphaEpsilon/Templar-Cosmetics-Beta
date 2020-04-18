package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEntityHeadLookModule extends AbstractPacketModule {

	private SPacketEntityHeadLook packet;
	
	public SPacketEntityHeadLookModule(SPacketEntityHeadLook packet, long timestamp) {
		super("Changes the direction of an entity's head.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketEntityHeadLook", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity: " + packet.getEntity(Minecraft.getMinecraft().world), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Yaw: " + packet.getYaw(), x, y + 28, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
