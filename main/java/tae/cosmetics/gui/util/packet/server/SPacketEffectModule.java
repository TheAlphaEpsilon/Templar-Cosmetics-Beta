package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketEffect;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEffectModule extends AbstractPacketModule {

	private SPacketEffect packet;
	
	public SPacketEffectModule(SPacketEffect packet, long timestamp) {
		super("Tells the client to display a sound or particle.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketEffect", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Type: " + packet.getSoundType(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Data: " + packet.getSoundData(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Pos: " + packet.getSoundPos().getX() + " | " + packet.getSoundPos().getY() + " | " + packet.getSoundPos().getZ(), x, y + 42, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
