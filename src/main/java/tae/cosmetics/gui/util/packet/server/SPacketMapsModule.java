package tae.cosmetics.gui.util.packet.server;

import java.awt.Color;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.world.storage.MapData;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;

public class SPacketMapsModule extends AbstractPacketModule {

	private SPacketMaps packet;
	
	public SPacketMapsModule(SPacketMaps packet, long timestamp) {
		super("Sent to update map items.", timestamp, packet);
		this.packet = packet;
	}

	@Override
	public void drawText(int x, int y) {
		fontRenderer.drawString("SPacketMaps", x, y, Color.WHITE.getRGB());
		if(!minimized) {
			fontRenderer.drawString("Map ID: " + packet.getMapId(), x, y + 14, Color.WHITE.getRGB());
			
			MapData data = new MapData("data");
			packet.setMapdataTo(data);
			
			fontRenderer.drawString("Dimension: " + data.dimension, x, y + 28, Color.WHITE.getRGB());
			fontRenderer.drawString("Is Tracking: " + data.trackingPosition, x, y + 42, Color.WHITE.getRGB());
			fontRenderer.drawString("Scale: " + data.scale, x, y + 56, Color.WHITE.getRGB());
			
			GlStateManager.scale(0.25, 0.25, 0.25);
			
			for (int i = 0; i < 16384; ++i) {
				
                int j = data.colors[i] & 255;

                if (j / 4 == 0)
                {
                    this.drawHorizontalLine(i % 128 + x * 4, i % 128 + 1 + x * 4, i / 128 + (70 + y) * 4, (i + i / 128 & 1) * 8 + 16 << 24);
                }
                else
                {
                    this.drawHorizontalLine(i % 128 + x * 4, i % 128 + 1 + x * 4, i / 128 + (70 + y) * 4, MapColor.COLORS[j / 4].getMapColor(j & 3));
                }
            }
			
			GlStateManager.scale(4, 4, 4);
			
		}
	}

	@Override
	public boolean type() {
		return false;
	}
	
}
