package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;
import java.util.Collection;

import net.minecraft.network.play.server.SPacketTeams;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketTeamsModule extends AbstractPacketModule {
	
	private SPacketTeams packet;
	
	public SPacketTeamsModule(SPacketTeams packet, long timestamp) {
		super("Updates teams", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketTeams", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Name: " + packet.getName(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Display: " + packet.getDisplayName(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Players: " + collectionToString(packet.getPlayers()), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Action: " + packet.getAction(), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Flags: 0x" + Integer.toHexString(packet.getFriendlyFlags()), x, y + 70, Color.WHITE.getRGB());
			fontRenderer.drawString("Collision Rule: " + packet.getCollisionRule(), x, y + 84, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
	private String collectionToString(Collection<?> c) {
		
		StringBuffer buff = new StringBuffer();
		c.forEach(x -> buff.append(x.toString() + " "));
		return buff.toString();
		
	}

}
