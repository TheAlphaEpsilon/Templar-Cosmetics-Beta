package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;
import java.util.List;

import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketEntityMetadataModule extends AbstractPacketModule {

	private SPacketEntityMetadata packet;
	
	public SPacketEntityMetadataModule(SPacketEntityMetadata packet, long timestamp) {
		super("Changes entity metadata.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketEntityMetadata", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Entity ID: " + packet.getEntityId(), x, y + 14, Color.WHITE.getRGB());
			List<DataEntry<?>> toDraw = packet.getDataManagerEntries();
			
			int yInc = 28;
			
			if(toDraw != null) {
				
				for(DataEntry<?> entry : toDraw) {
					fontRenderer.drawString("Value: " + entry.getValue(), x, y + yInc, Color.WHITE.getRGB());
					yInc += 14;
				}
				
			}
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
