package tae.cosmetics.gui.util.packet.client;

import java.awt.Color;

import net.minecraft.network.play.client.CPacketChatMessage;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class CPacketChatMessageModule extends AbstractPacketModule {

	private CPacketChatMessage packet;
	
	public CPacketChatMessageModule(CPacketChatMessage packet, long timestamp) {
		super("Used to send a chat message to the server.", timestamp, packet);
		this.packet = packet;
	}
	
	@Override
	public void drawText(int x, int y) {
		
		fontRenderer.drawString("CPacketChatMessage", x, y, Color.WHITE.getRGB());
	
		if(!minimized) {
			fontRenderer.drawSplitString("Text: " + packet.getMessage(), x, y + 14, modwidth, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return true;
	}

}
