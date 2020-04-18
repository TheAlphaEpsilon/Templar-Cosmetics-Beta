package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketVehicleMove;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketVehicleMoveModule extends AbstractPacketModule {

	private CPacketVehicleMove packet;
	
	public CPacketVehicleMoveModule(CPacketVehicleMove packet, long timestamp) {
		
		super("Sent when moving on a vehicle.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketVehicleMove", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Pos: " + (int)packet.getX()+"|"+(int)packet.getY()+"|"+(int)packet.getZ(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Pitch: " + packet.getPitch(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Yaw: " + packet.getYaw(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
