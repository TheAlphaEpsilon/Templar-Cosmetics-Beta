package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;
import java.util.List;

import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketEntityProperties.Snapshot;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEntityPropertiesModule extends AbstractPacketModule {
	
	private SPacketEntityProperties packet;
	
	public SPacketEntityPropertiesModule(SPacketEntityProperties packet, long timestamp) {
		super("Changes an entity's properties", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {

		fontRenderer.drawString("SPacketEntityProperties", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityId(), x, y + 14, Color.WHITE.getRGB());
			List<Snapshot> toDraw = packet.getSnapshots();
			
			if(toDraw != null) {
				
				int yInc = 28;
				
				for(Snapshot shot : toDraw) {
					
					fontRenderer.drawString("Data: " + shot.getName(), x, y + yInc, Color.WHITE.getRGB());
					fontRenderer.drawString("Value: " + shot.getBaseValue(), x, y + yInc + 14, Color.WHITE.getRGB());
					yInc += 28;
					
				}
				
			}
			
		}
	}

	@Override
	public boolean type() {
		return false;
	}

}
