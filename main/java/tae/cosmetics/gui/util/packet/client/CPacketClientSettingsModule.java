package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketClientSettings;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketClientSettingsModule extends AbstractPacketModule {

	private CPacketClientSettings packet;
	
	public CPacketClientSettingsModule(CPacketClientSettings packet, long timestamp) {
	
		super("Sent when the player connects, or when settings are changed.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("CPacketClientSettings", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Language: " + packet.getLang(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawString("Chat Visability: " + packet.getChatVisibility(), x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Main hand: " + packet.getMainHand(), x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Player Skin: 0x" + Integer.toHexString(packet.getModelPartFlags()), x, y + 56, Color.WHITE.getRGB());
			fontRenderer.drawString("Colors: " + packet.isColorsEnabled(), x, y + 70, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return true;
	}
	
}
