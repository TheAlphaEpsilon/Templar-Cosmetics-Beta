package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketDisplayObjective;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketDisplayObjectiveModule extends AbstractPacketModule {

	private SPacketDisplayObjective packet;
	
	public SPacketDisplayObjectiveModule(SPacketDisplayObjective packet, long timestamp) {
		super("Sent to display the scoreboard.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketDisplayObjective",x,y,Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Name: " + packet.getName(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + packet.getPosition(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
