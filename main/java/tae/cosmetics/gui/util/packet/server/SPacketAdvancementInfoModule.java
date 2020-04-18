package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;
import java.util.Map;

import net.minecraft.advancements.Advancement;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.util.ResourceLocation;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketAdvancementInfoModule extends AbstractPacketModule {

	private SPacketAdvancementInfo packet;
	
	public SPacketAdvancementInfoModule(SPacketAdvancementInfo packet, long timestamp) {
		super("Sent when modifying the advancements tab", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketAdvancementInfo", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Advancements To Add:", x, y + 14, Color.WHITE.getRGB());
			int yInc = 28;
			
			Map<ResourceLocation, Advancement.Builder> toDisplay = packet.getAdvancementsToAdd();
			
			if(toDisplay != null) {
				for(ResourceLocation key : toDisplay.keySet()) {
					fontRenderer.drawString(toDisplay.get(key).toString(), x, y + yInc, Color.WHITE.getRGB());
					yInc += 14;
				}
			}
			
			fontRenderer.drawString("NEEDS UPDATING", x, y + yInc, Color.WHITE.getRGB());
			
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
