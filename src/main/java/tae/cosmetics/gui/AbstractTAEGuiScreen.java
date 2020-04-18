package tae.cosmetics.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import tae.cosmetics.Globals;

public abstract class AbstractTAEGuiScreen extends GuiScreen {
	
	//TODO: Implement
	
	private GuiScreen parent;
	
	protected GuiButton back = new GuiButton(0, 0, 0, 70, 20, "Back");
	
	protected ArrayList<GuiMessage> messagesToDraw = new ArrayList<>();
	
	protected AbstractTAEGuiScreen(GuiScreen parent) {
		this.parent = parent;
	}
		
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		messagesToDraw.forEach(x -> x.draw());
				
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		if(button == back) {
			
			displayScreen(parent);
			
		}
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	public void displayScreen(GuiScreen screenIn) {
		mc.addScheduledTask(() -> {
			
			mc.displayGuiScreen(screenIn);
			
		});
	}
	
	protected void addMessage(String message, int x, int y, int ticks, int color) {
		messagesToDraw.add(new GuiMessage(message, x, y, ticks, color));
	}
	
	protected void addMessage(String message, int x, int y, int ticks, Color color) {
		messagesToDraw.add(new GuiMessage(message, x, y, ticks, color.getRGB()));
	}
	
	@Override
	public void initGui() {
		buttonList.add(back);
	}
	
	@Override
	public abstract void onGuiClosed();
	
	@Override
	public void drawHoveringText(String text, int x, int y) {
		drawHoveringText(Arrays.asList(text), x, y, fontRenderer);
	}
	
	@Override
	protected void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font) {
        if (!textLines.isEmpty()) {
            
            int i = 0;

            for (String s : textLines)
            {
                int j = this.fontRenderer.getStringWidth(s);

                if (j > i)
                {
                    i = j;
                }
            }

            int l1 = x + 12;
            int i2 = y - 12;
            int k = 8;

            if (textLines.size() > 1)
            {
                k += 2 + (textLines.size() - 1) * 10;
            }

            this.drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
            this.drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
            this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
            this.drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
            this.drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
            this.drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847);
            this.drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847);
            this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415);
            this.drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);

            for (int k1 = 0; k1 < textLines.size(); ++k1)
            {
                String s1 = textLines.get(k1);
                this.fontRenderer.drawStringWithShadow(s1, (float)l1, (float)i2, -1);

                if (k1 == 0)
                {
                    i2 += 2;
                }

                i2 += 10;
            }
            
        }
        
    }
	
	protected abstract void updateButtonPositions(int x, int y);
	
	static class GuiMessage extends Gui implements Globals {
		
		private String message;
		private int x;
		private int y;
		private int ticks;
		private int color;
		
		public GuiMessage(String message, int x, int y, int ticks, int color) {
			
			this.message = message;
			this.x = x;
			this.y = y;
			this.ticks = ticks;
			this.color = color;
			
		}
		
		public void draw() {
			
			if(ticks > 0) {
				ticks--;
				mc.fontRenderer.drawString(message, x, y, color);
			}
			
		}
		
	}
	
}
