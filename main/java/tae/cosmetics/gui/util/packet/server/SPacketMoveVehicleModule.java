package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketMoveVehicle;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketMoveVehicleModule extends AbstractPacketModule {

	private SPacketMoveVehicle packet;
	
	public SPacketMoveVehicleModule(SPacketMoveVehicle packet, long timestamp) {
		super("Sent to move a vehicle.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketMoveVehicle", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Pos: " + (int)packet.getX() + " | " + (int)packet.getY() + " | " + (int)packet.getZ(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Pitch: " + packet.getPitch(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Yaw: " + packet.getYaw(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
