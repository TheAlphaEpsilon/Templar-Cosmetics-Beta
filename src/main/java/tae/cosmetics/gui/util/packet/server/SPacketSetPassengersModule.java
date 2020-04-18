package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;
import java.util.Arrays;

import net.minecraft.network.play.server.SPacketSetPassengers;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSetPassengersModule extends AbstractPacketModule {

	private SPacketSetPassengers packet;
	
	public SPacketSetPassengersModule(SPacketSetPassengers packet, long timestamp) {
		super("Sent when an entity starts riding another.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSetPassengers", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityId(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Passengers: " + Arrays.toString(packet.getPassengerIds()), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
