package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketConfirmTransaction;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketConfirmTransactionModule extends AbstractPacketModule {

	private SPacketConfirmTransaction packet;
	
	public SPacketConfirmTransactionModule(SPacketConfirmTransaction packet, long timestamp) {
		super("Used to confirm a click.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketConfirmTransaction", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Window ID: " + packet.getWindowId(), x, y, Color.WHITE.getRGB());
			fontRenderer.drawString("Action ID: " + packet.getActionNumber(), x, y, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
