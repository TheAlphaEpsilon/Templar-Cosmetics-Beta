package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketClickWindow;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketClickWindowModule extends AbstractPacketModule {

	CPacketClickWindow packet;
	
	public CPacketClickWindowModule(CPacketClickWindow packet, long timestamp) {
		
		super("Sent by the player when it clicks on a slot in a window.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketClickWindow", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Action #: " + packet.getActionNumber(), x, y + 14, Color.white.getRGB());
			fontRenderer.drawString("Item Stack: " + packet.getClickedItem().getDisplayName(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Click Type: " + packet.getClickType(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Slot ID: " + packet.getSlotId(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Button ID: " + packet.getUsedButton(), x, y + 70, Color.WHITE.getRGB());
			fontRenderer.drawString("Window ID: " + packet.getWindowId(), x, y + 84, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
