package tae.cosmetics.gui;

import java.io.IOException;
import java.util.Iterator;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tae.cosmetics.gui.util.GuiExtendPacketModuleButton;
import tae.cosmetics.gui.util.GuiMorePacketInformationButton;
import tae.cosmetics.gui.util.ScrollBar;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;
import tae.cosmetics.mods.VisualizePacketsMod;

public class GuiScreenVisualizePackets extends AbstractTAEGuiScreen {
	
    private static final ResourceLocation BACKGROUND = new ResourceLocation("taecosmetics","textures/gui/cancelpackets.png");
	    
    private static final ModifiedList<GuiExtendPacketModuleButton> extendButtonsToDraw = new ModifiedList<>();
    
    private static final ModifiedList<GuiMorePacketInformationButton> moreButtonsToDraw = new ModifiedList<>();
    
    private static int firstIndex = 0;
    private static int firstY = 0;
    
	private ScrollBar scroll = new ScrollBar(0, 0, 139);
    
	public GuiScreenVisualizePackets(GuiScreen parent) {
		super(parent);
		override = true;
	}
	
	protected void drawScreen0(int x, int y, float f) {
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		int i = width / 2;
		int j = height / 2;
		
		int minY;
		if(firstY == 0) {
			minY = j - 60;
 		} else {
 			minY = firstY;
 		}
				
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		
		mc.getTextureManager().bindTexture(BACKGROUND);
		
		this.drawTexturedModalRect(i - 213, j - 120, 0, 0, 128, 205);
		
		this.drawTexturedModalRect(i - 213, j - 14, 0, 77, 128, 134);
		
		this.drawTexturedModalRect(i - 85, j - 120, 4, 0, 240, 205);
		
		this.drawTexturedModalRect(i - 85, j - 86, 4, 5, 240, 206);
		
		this.drawTexturedModalRect(i + 86, j - 120, 128, 0, 128, 211);
		
		this.drawTexturedModalRect(i + 86, j - 14, 128, 77, 128, 134);
		
		scroll.draw(mouseX, mouseY);

		int size = VisualizePacketsMod.modulesToDraw.size();
		
		int totalOffset = 0;
		for(int index = 0; index < size; index++) {
			totalOffset += VisualizePacketsMod.modulesToDraw.get(index).getHeight();
		}
						
		AbstractPacketModule curr = null;
		
		AbstractPacketModule prev = null;
		
		AbstractPacketModule lastHovered = null;
		
		GuiButton lastButton1 = null;
		GuiButton lastButton2 = null;
		
		if(size > 0) {
			int index = firstIndex;
			do {
				
				curr = VisualizePacketsMod.modulesToDraw.get(index++);
				
				curr.x = i - 140;
				
				if(prev == null) {
					curr.y = (int) (minY - totalOffset * scroll.getScroll());
				} else {
					curr.y = prev.y + prev.getHeight();
				}
								
				if(curr.y + curr.getHeight() <= j + 120 && curr.y >= j - 120) {
					curr.drawScreen(mouseX, mouseY, partialTicks);
					if(curr.isHovered(mouseX, mouseY)) {
						lastHovered = curr;
					}
					
					fontRenderer.drawString(Integer.toString(index), curr.x - 30, curr.y + 2, 0);
					
					GuiExtendPacketModuleButton button1 = curr.extendButton();
					button1.drawButton(mc, mouseX, mouseY, partialTicks);
					if(button1.y >= j - 60 && button1.y <= j + 60) {
						extendButtonsToDraw.set(index % 20, button1);
						if(button1.isMouseOver()) {
							lastButton1 = button1;
						}
					}
				
					GuiMorePacketInformationButton button2 = curr.moreInfoButton();
					button2.drawButton(mc, mouseX, mouseY, partialTicks);
					if(button2.y >= j - 60 && button2.y <= j + 60) {
						moreButtonsToDraw.set(index % 20, button2);
						if(button2.isMouseOver()) {
							lastButton2 = button2;
						}
					}
					
				}
				
				prev = curr;
				
			} while(curr.y + curr.getHeight() <= j + 120 && index < size);
		}
			
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		
		mc.getTextureManager().bindTexture(BACKGROUND);
		
		Gui.drawScaledCustomSizeModalRect(i - 200, j - 120, 5, 0, 200, 60, 370, 60, 256, 256);		
		Gui.drawScaledCustomSizeModalRect(i - 200, j + 60, 5, 151, 200, 60, 370, 60, 256, 256);
		
		if(lastHovered != null && lastHovered.isHovered(mouseX, mouseY) && lastHovered.y >= j - 60 && lastHovered.y <= j + 60) {
			drawHoveringText(lastHovered.getTip(), mouseX, mouseY);
		}
		
		if(lastButton1 != null && lastButton1.isMouseOver()) {
			drawHoveringText("Extend Module", mouseX, mouseY);
		}
		
		if(lastButton2 != null && lastButton2.isMouseOver()) {
			drawHoveringText("In-Depth Info", mouseX, mouseY);
		}
		
		updateButtonPositions(i, j);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	
		for(GuiExtendPacketModuleButton button : extendButtonsToDraw) {
			if(button.isMouseOver()) {
				actionPerformed(button);
			}
		}
		
		for(GuiMorePacketInformationButton button : moreButtonsToDraw) {
			if(button.isMouseOver()) {
				actionPerformed(button);
			}
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		if(button instanceof GuiExtendPacketModuleButton) {
			((GuiExtendPacketModuleButton) button).toggle();
		} else if(button instanceof GuiMorePacketInformationButton) {
			((GuiMorePacketInformationButton) button).openScreen(this);
		}
		
		super.actionPerformed(button);
		
	}
	
	
	@Override
	protected void updateButtonPositions(int x, int y) {
		scroll.x = x + 194;
		scroll.y = y - 68;
	}
		
	@Override
	public void onGuiClosed() {
		
	}
	
	static class ModifiedList<E> implements Iterable<E> {
		
		private int size;
		private E[] array;
		
		@SuppressWarnings("unchecked")
		private ModifiedList() {
			array = (E[]) new Object[10];
			size = 0;
		}
		
		@SuppressWarnings("unchecked")
		private void set(int index, E element) {
			if(array.length <= index) {
				
				Object[] temp = new Object[index * 2];
				
				for(int i = 0; i < array.length; i++) {
					temp[i] = array[i];
				}
				
				array = (E[]) temp;
			}
			
			if(array[index] == null) {
				size++;
			}
			
			array[index] = element;
		}

		@Override
		public Iterator<E> iterator() {
			return new Iterator<E>() {

				private int counter = 0;
				private int index = 0;
				
				@Override
				public boolean hasNext() {
					return counter < size;
				}

				@Override
				public E next() {
					counter++;
					
					E toReturn = null;
					
					while(toReturn == null) {
						toReturn = array[index++];
					}
					
					return toReturn;
					
				}
				
			};
		}
		
	}
	
}
