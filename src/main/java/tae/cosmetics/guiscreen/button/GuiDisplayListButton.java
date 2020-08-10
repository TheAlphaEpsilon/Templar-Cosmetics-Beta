package tae.cosmetics.guiscreen.button;

import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import tae.cosmetics.util.CircularList;

public class GuiDisplayListButton<T> extends AbstractTAEButton<T> {

	private CircularList<T> toIterate;
	private int listSize;
	private int currentIndex;
	private int increment;
	
	public GuiDisplayListButton(int buttonId, int x, int y, int widthIn, int heightIn, Collection<T> e, T defaultItem, String info,int scale) {
		super(buttonId, x, y, widthIn, heightIn, "", info, scale);
		toIterate = new CircularList<T>(e);
		listSize = e.size();
		currentIndex = toIterate.indexOf(defaultItem);
		if(currentIndex == -1) throw new IllegalArgumentException("Default item not in list");
		increment = 0;
	}
	
	@Override
	protected void drawButton0(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		
		FontRenderer fontrenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                
        int i = this.getHoverState(this.hovered);
        
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.drawTexturedModalRect(x, y, 0, 46 + i * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(x + this.width / 2, y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
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
	
	public T getValue() {
		return toIterate.get(currentIndex + increment);
	}

	@Override
	public void changeStateOnClick() {
		if(++increment == listSize) {
			increment = 0;
		}
	}

}
