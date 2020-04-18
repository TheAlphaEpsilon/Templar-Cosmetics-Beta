package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketCamera;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketCameraModule extends AbstractPacketModule {
	
	private SPacketCamera packet;
	
	public SPacketCameraModule(SPacketCamera packet, long timestamp) {
		super("Sent to set the entity the camera renders on.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketCamera", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntity(Minecraft.getMinecraft().world), x, y + 14, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
