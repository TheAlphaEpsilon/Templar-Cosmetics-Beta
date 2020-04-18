package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketInput;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketInputModule extends AbstractPacketModule {

	private CPacketInput packet;
	
	public CPacketInputModule(CPacketInput packet, long timestamp) {
		
		super("Sent when player moves.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketInput", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Forward Speed: " + packet.getForwardSpeed(), x, y, Color.WHITE.getRGB());
			fontRenderer.drawString("Strafe Speed: " + packet.getStrafeSpeed(), x, y, Color.WHITE.getRGB());
			fontRenderer.drawString("Jumping: " + packet.isJumping(), x, y, Color.WHITE.getRGB());
			fontRenderer.drawString("Sneaking: " + packet.isSneaking(), x, y, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}

}
