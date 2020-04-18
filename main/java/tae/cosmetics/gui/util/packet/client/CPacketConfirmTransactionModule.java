package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketConfirmTransaction;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketConfirmTransactionModule extends AbstractPacketModule {

	private CPacketConfirmTransaction packet;
	
	public CPacketConfirmTransactionModule(CPacketConfirmTransaction packet, long timestamp) {
		
		super("Supports ClickWindow Packets", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketConfirmTransaction", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("UID: " + packet.getUid(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Window ID: " + packet.getWindowId(), x, y, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
