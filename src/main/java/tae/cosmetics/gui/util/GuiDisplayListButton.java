package tae.cosmetics.gui.util;

import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import tae.cosmetics.util.CircularList;

public class GuiDisplayListButton<T> extends GuiButton {

	private CircularList<T> toIterate;
	private int listSize;
	private int currentIndex;
	private int increment;
	
	public GuiDisplayListButton(int buttonId, int x, int y, int widthIn, int heightIn, Collection<T> e, T defaultItem) {
		super(buttonId, x, y, widthIn, heightIn, "");
		toIterate = new CircularList<T>(e);
		listSize = e.size();
		currentIndex = toIterate.indexOf(defaultItem);
		if(currentIndex == -1) throw new IllegalArgumentException("Default item not in list");
		increment = 0;
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (packedFGColour != 0)
            {
                j = packedFGColour;
            }
            else
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }

            this.drawCenteredString(fontrenderer, toIterate.get(currentIndex + increment).toString(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }
    }
	
	public void nextValue() {
		if(++increment == listSize) {
			increment = 0;
		}
	}
	
	public T getValue() {
		return toIterate.get(currentIndex + increment);
	}

}
