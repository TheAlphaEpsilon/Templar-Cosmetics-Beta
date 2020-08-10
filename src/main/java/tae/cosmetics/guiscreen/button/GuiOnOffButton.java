package tae.cosmetics.guiscreen.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class GuiOnOffButton extends AbstractTAEButton<Boolean> {

	private String text;
	private boolean toggled;
	
	public GuiOnOffButton(int buttonId, int x, int y, int widthIn, int heightIn, String text, boolean defaultState, String info, int scale) {
		super(buttonId, x, y, widthIn, heightIn, "", info, scale);
		this.text = text;
		toggled = defaultState;
	}

	public String getText() {
		return text;
	}
	
	public void drawButton0(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
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

		if (packedFGColour != 0) {
			j = packedFGColour;
		} else if (!this.enabled) {
			j = 10526880;
        } else if (this.hovered) {
        	j = 16777120;
        }

		this.drawCenteredString(fontrenderer, text + (toggled?"\u00a72ON":"\u00a7cOFF"), x + this.width / 2, y + (this.height - 8) / 2, j);
      
    }
	
	public boolean getHovered() {
		return hovered;
	}

	@Override
	public void changeStateOnClick() {
		toggled = toggled ? false : true;
	}

	@Override
	public Boolean getValue() {
		return toggled;
	}
	
}
