package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketCombatEvent;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketCombatEventModule extends AbstractPacketModule {

	private SPacketCombatEvent packet;
	
	public SPacketCombatEventModule(SPacketCombatEvent packet, long timestamp) {
		super("Currently only used to display deaths.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketCombatEvent", x, y, Color.WHITE.getRGB());
		
		if(!minimized) {
			fontRenderer.drawString("Player ID: " + packet.playerId, x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Entity ID: " + packet.entityId, x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Type: " + packet.eventType, x, y, Color.WHITE.getRGB());
			fontRenderer.drawString("Message: " + packet.deathMessage.getFormattedText(), x, y, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
