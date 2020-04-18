package tae.cosmetics.gui.util.packet;

import java.awt.Color;

import net.minecraft.network.Packet;

public class UnknownPacketModule extends AbstractPacketModule {
	
	Packet<?> packet;
	boolean type;
	
	public UnknownPacketModule(Packet<?> packet, long timestamp, boolean type) {
		super("No quick data avaliable. Click More", timestamp, packet);
		this.packet = packet;
		this.type = type;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString(packet.getClass().getSimpleName(), x, y, Color.WHITE.getRGB());
	}

	@Override
	public boolean type() {
		return type;
	}

}
