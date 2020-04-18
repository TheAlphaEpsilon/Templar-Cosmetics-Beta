package tae.cosmetics.gui.util;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import tae.cosmetics.gui.AbstractTAEGuiScreen;
import tae.cosmetics.gui.util.packet.GuiGenericPacketInformation;

public class GuiMorePacketInformationButton extends GuiButton {

	private static final int scalefactor = 2;

	private Packet<?> packet;
	
	public GuiMorePacketInformationButton(int buttonId, int x, int y, Packet<?> packet) {
		super(buttonId, x, y, 18, 18, "");
		this.packet = packet;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
    		
        	int x = this.x * scalefactor;
        	int y = this.y * scalefactor;
        	
        	GlStateManager.scale(1D / scalefactor, 1D / scalefactor, 1D / scalefactor);
        	
        	//FontRenderer fontrenderer = mc.fontRenderer;
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + width / scalefactor && mouseY < this.y + height / scalefactor;
            int i = this.getHoverState(this.hovered);
           
            Gui.drawRect(x, y, x + width, y + height, Color.BLACK.getRGB());
            
      
            for(int index = 0; index < 2; index++) {

            	Gui.drawRect(x + width / 2 - index, y + 2, x + width / 2 - index + 1, y + height - 2, i == 2 ? Color.YELLOW.getRGB() : Color.WHITE.getRGB());
            	Gui.drawRect(x + width / 2 + index, y + 2, x + width / 2 + index + 1, y + height - 2, i == 2 ? Color.YELLOW.getRGB() : Color.WHITE.getRGB());
            	
            	Gui.drawRect(x + 2, y + height / 2 - index, x + width - 2, y + height / 2 - index + 1, i == 2 ? Color.YELLOW.getRGB() : Color.WHITE.getRGB());
            	Gui.drawRect(x + 2, y + height / 2 + index, x + width - 2, y + height / 2 + index + 1, i == 2 ? Color.YELLOW.getRGB() : Color.WHITE.getRGB());
            	
            }
            	
            
    		GlStateManager.scale(scalefactor, scalefactor, scalefactor);
            
        }
    }

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return this.enabled && this.visible && this.hovered;
    }
	
	public void openScreen(AbstractTAEGuiScreen parent) {
		
		parent.displayScreen(new GuiGenericPacketInformation(packet, parent));
		
	}
	
}
