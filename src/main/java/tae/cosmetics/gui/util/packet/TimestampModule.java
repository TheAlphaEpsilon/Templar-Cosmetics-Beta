package tae.cosmetics.gui.util.packet;

import java.awt.Color;
import java.time.Instant;
import java.util.Date;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class TimestampModule extends AbstractPacketModule {

	private long timestamp;
	
	public TimestampModule(long timestamp) {
		super("Used to track a position in time.", timestamp, null);
		this.timestamp = timestamp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		int x = this.x * scalefactor;
		int y = this.y * scalefactor;
		
		GlStateManager.scale(1D / scalefactor, 1D / scalefactor, 1D / scalefactor);
		
		Gui.drawRect(x, y, x + modtimestampwidth + modwidth * 2, y + (minimized ? modheightminimized : modheightfull), new Color(128, 0, 128).getRGB());
		Gui.drawRect(x + 1, y + 1, x + modtimestampwidth + modwidth * 2 - 1, y + (minimized ? modheightminimized : modheightfull) - 1, new Color(64, 0, 64).getRGB());
		Gui.drawRect(x + 2, y + 2, x + modtimestampwidth + modwidth * 2 - 2, y + (minimized ? modheightminimized : modheightfull) - 2, Color.YELLOW.getRGB());
		
		fontRenderer.drawString(Long.toString(getTimestamp()), x + 3, y + 6, Color.BLACK.getRGB());
		
		fontRenderer.drawString("Marker", x + modtimestampwidth + modwidth - fontRenderer.getStringWidth("Marker")/2, y + 6, Color.BLACK.getRGB());
		if(!minimized) {
			Date date;
			fontRenderer.drawString((date = Date.from(Instant.ofEpochMilli(timestamp))).getHours() + " : " + date.getMinutes() + " : " + (date.getSeconds() < 10 ? "0" : "") + date.getSeconds(), x + modtimestampwidth + modwidth - fontRenderer.getStringWidth("Marker")/2, y + 20, Color.BLACK.getRGB());
		}
		
		GlStateManager.scale(scalefactor, scalefactor, scalefactor);
	}
	
	@Override
	public void drawText(int x, int y) {
		
	}

	@Override
	public boolean type() {
		return true;
	}

}
