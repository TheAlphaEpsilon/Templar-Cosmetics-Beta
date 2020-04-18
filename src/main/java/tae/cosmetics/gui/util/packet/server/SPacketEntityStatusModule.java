package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketEntityStatus;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEntityStatusModule extends AbstractPacketModule {

	private SPacketEntityStatus packet;
	
	public SPacketEntityStatusModule(SPacketEntityStatus packet, long timestamp) {
		super("Triggers entity animation.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketEntityStatus", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity: " + packet.getEntity(Minecraft.getMinecraft().world), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Data: " + packet.getOpCode(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
