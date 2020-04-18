package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketChat;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketChatModule extends AbstractPacketModule {
	
	private SPacketChat packet;
	
	public SPacketChatModule(SPacketChat packet, long timestamp) {
		super("Sends chat messages to the client.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketChat", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Chat Type: " + packet.getType(), x, y + 14, Color.WHITE.getRGB());
			fontRenderer.drawSplitString("Text: " + packet.getChatComponent().getFormattedText(), x, y + 28, AbstractPacketModule.modwidth - 9, Color.WHITE.getRGB());
		}
		
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
