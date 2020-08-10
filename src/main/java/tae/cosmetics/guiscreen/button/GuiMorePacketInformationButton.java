package tae.cosmetics.guiscreen.button;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.Packet;
import tae.cosmetics.gui.util.packet.GuiGenericPacketInformation;
import tae.cosmetics.guiscreen.AbstractTAEGuiScreen;

public class GuiMorePacketInformationButton extends AbstractTAEButton<Void> {

	private Packet<?> packet;
	
	public GuiMorePacketInformationButton(int buttonId, int x, int y, Packet<?> packet, String info, int scale) {
		super(buttonId, x, y, 18, 18, "", info, scale);
		this.packet = packet;
	}
	
	@Override
	public void drawButton0(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        
		int i = this.getHoverState(this.hovered);
        
        Gui.drawRect(x, y, x + width, y + height, Color.BLACK.getRGB());
        
  
        for(int index = 0; index < 2; index++) {

        	Gui.drawRect(x + width / 2 - index, y + 2, x + width / 2 - index + 1, y + height - 2, i == 2 ? Color.YELLOW.getRGB() : Color.WHITE.getRGB());
        	Gui.drawRect(x + width / 2 + index, y + 2, x + width / 2 + index + 1, y + height - 2, i == 2 ? Color.YELLOW.getRGB() : Color.WHITE.getRGB());
        	
        	Gui.drawRect(x + 2, y + height / 2 - index, x + width - 2, y + height / 2 - index + 1, i == 2 ? Color.YELLOW.getRGB() : Color.WHITE.getRGB());
        	Gui.drawRect(x + 2, y + height / 2 + index, x + width - 2, y + height / 2 + index + 1, i == 2 ? Color.YELLOW.getRGB() : Color.WHITE.getRGB());
        	
        }
        
    }

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return this.enabled && this.visible && this.hovered;
    }
	
	public void openScreen(AbstractTAEGuiScreen parent) {
		
		AbstractTAEGuiScreen.displayScreen(new GuiGenericPacketInformation(packet, parent));
		
	}

	@Override
	public void changeStateOnClick() {}

	@Override
	public Void getValue() {
		return null;
	}
	
}
