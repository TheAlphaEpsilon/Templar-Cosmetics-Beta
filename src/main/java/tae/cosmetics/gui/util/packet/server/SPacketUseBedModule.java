package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketUseBed;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketUseBedModule extends AbstractPacketModule {
	
	private SPacketUseBed packet;
	
	public SPacketUseBedModule(SPacketUseBed packet, long timestamp) {
		super("Sent when a player uses a bed.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketUseBed", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Player: " + packet.getPlayer(Minecraft.getMinecraft().world), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Bed Pos: " + packet.getBedPosition(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
