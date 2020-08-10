package tae.cosmetics.guiscreen.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class OptionsButton extends AbstractTAEButton<Void> {

	private static final ResourceLocation STALKER_MOD_GUI = new ResourceLocation("taecosmetics","textures/gui/playeralert.png");

	public OptionsButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String info, int scale) {
		super(buttonId, x, y, widthIn, heightIn, buttonText, info, scale);
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && this.hovered;
    }

	@Override
	public void changeStateOnClick() {}

	@Override
	public Void getValue() {
		return null;
	}

	@Override
	protected void drawButton0(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

		int i = this.getHoverState(this.hovered) - 1;
      
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		mc.getTextureManager().bindTexture(STALKER_MOD_GUI);
			
		Gui.drawScaledCustomSizeModalRect(x, y, 187 + (i * 26), 178, 26, 26, 13, 13, 256, 256);
			
		this.mouseDragged(mc, mouseX, mouseY);
			
	}
	
}
