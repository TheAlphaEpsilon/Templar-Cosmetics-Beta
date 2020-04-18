package tae.cosmetics.gui.util.mainscreen;

import java.awt.Color;

import net.minecraft.network.Packet;
import tae.cosmetics.util.SetAddOnlyQueue;

public class VisualizePacketsGuiList extends AbstractMoveableGuiList {

	private SetAddOnlyQueue<Packet<?>> packets;
	private static int color = new Color(0, 200, 0).getRGB();
	
	public VisualizePacketsGuiList(String title, int startingX, int startingY) {
		super(title, startingX, startingY, color);
		packets = new SetAddOnlyQueue<>(6);
	}

	@Override
	protected void drawList() {
		int startY = y + fontRenderer.FONT_HEIGHT + 5;
		
		int counter = 0;
		
		for(Packet<?> packet : packets) {
			
			String name = packet.getClass().getSimpleName();
			
			if(name != null) {
				fontRenderer.drawString(name, x, startY + counter++ * (fontRenderer.FONT_HEIGHT + 5), color);
			}
			
		}
		
	}

	public void addPacket(Packet<?> packet) {
		packets.add(packet);
	}
	
	public void reset() {
		packets = new SetAddOnlyQueue<>(6);
	}
	
}
