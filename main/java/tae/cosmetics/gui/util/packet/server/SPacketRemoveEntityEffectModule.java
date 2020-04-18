package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketRemoveEntityEffectModule extends AbstractPacketModule {

	private SPacketRemoveEntityEffect packet;
	
	public SPacketRemoveEntityEffectModule(SPacketRemoveEntityEffect packet, long timestamp) {
		super("Removes an effect from an entity.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketRemoveEntityEffect", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity: " + packet.getEntity(Minecraft.getMinecraft().world), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Potion: " + packet.getPotion(), x, y + 28, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
