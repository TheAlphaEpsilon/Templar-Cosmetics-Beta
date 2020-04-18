package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.network.play.server.SPacketSignEditorOpen;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketSignEditorOpenModule extends AbstractPacketModule {
	
	private SPacketSignEditorOpen packet;
	
	public SPacketSignEditorOpenModule(SPacketSignEditorOpen packet, long timestamp) {
		super("Sent when the client should open a sign GUI.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketSignEditorOpen", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Sign Pos: " + packet.getSignPosition().getX() + " | " + packet.getSignPosition().getY() + " | " + packet.getSignPosition().getZ(), x, y + 14, Color.WHITE.getRGB());
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
