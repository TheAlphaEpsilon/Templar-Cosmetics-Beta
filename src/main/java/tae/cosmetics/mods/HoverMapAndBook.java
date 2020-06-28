package tae.cosmetics.mods;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.ColorCode;
import tae.cosmetics.gui.GuiBookTitleMod;
import tae.cosmetics.settings.Keybind;
import tae.cosmetics.settings.Setting;
import tae.cosmetics.util.PlayerUtils;

public class HoverMapAndBook extends BaseMod {

	/**
	 * TheAlphaEpsilon
	 * 17 April 2020
	 */
	
	public static final Keybind keybind = new Keybind("Map and Book Hover", 0, () -> {
		
	});
	
	private static final int backgroundColor = new Color(203, 188, 147).getRGB();

	private static BookRenderer toRender = null;
	
	private static boolean keyWasDown = false;
	
	private static Setting<Double> bookandmapscalevalue = new Setting<>("Book and map scale", 4D);
	
	public static void updateScale(double s) {
		bookandmapscalevalue.setValue(s);
		if(toRender != null) {
			toRender.scale = s;
		}
	}
	
	public static int getScale() {
		return (int)(double)bookandmapscalevalue.getValue();
	}
	
	//Cancel normal tooltips
	@SubscribeEvent
	public void onHover(RenderTooltipEvent.Pre event) {
		Item item = event.getStack().getItem();
				
		if(!(Minecraft.getMinecraft().currentScreen instanceof GuiContainer) || Minecraft.getMinecraft().currentScreen instanceof GuiBookTitleMod || !keybind.isDownOverride()) {
			return;
		} else if(item != null && (item instanceof ItemMap || item instanceof ItemWrittenBook)) {
			event.setCanceled(true);
		}
	}
	
	//To draw new gui
	@SubscribeEvent
	public void drawNewGui(GuiScreenEvent.DrawScreenEvent.Post event) {
		if(!(Minecraft.getMinecraft().currentScreen instanceof GuiContainer) || Minecraft.getMinecraft().currentScreen instanceof GuiBookTitleMod || !keybind.isDownOverride()) {
			keyWasDown = false;
			return;
		}
		
		GlStateManager.disableDepth();
		
		GlStateManager.disableLighting();

		GuiContainer container = (GuiContainer) Minecraft.getMinecraft().currentScreen;
		
		Slot hovered = container.getSlotUnderMouse();
		
		if(hovered != null) {
			
			Item item = hovered.getStack().getItem();
			
			NBTTagCompound nbt = hovered.getStack().getTagCompound();
			
			if(nbt != null && !keyWasDown) {
				keyWasDown = true;
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					sendMessage(nbt.toString());
				}
			}
			
			if(item instanceof ItemMap) {
				drawMap((ItemStack)hovered.getStack(), (ItemMap)item, event.getMouseX(), event.getMouseY());
			} else if(item instanceof ItemWrittenBook) {
				
				if(toRender == null || (toRender != null && !toRender.isNBTSame(hovered.getStack().getTagCompound()))) {
					toRender = new BookRenderer(hovered.getStack(), 100, 10, (double)bookandmapscalevalue.getValue());
				} 
				toRender.draw(event.getMouseX(), event.getMouseY());
			}
			
		}
	}
	
	//Draws the map on screen
	private void drawMap(ItemStack mapStack, ItemMap map, int x, int y) {
				
		x = (int) (x * (8 / (double)bookandmapscalevalue.getValue()) + 5);
		y = (int) (y * (8 / (double)bookandmapscalevalue.getValue()) - 128);
		
		GlStateManager.scale((double)bookandmapscalevalue.getValue() / 8, (double)bookandmapscalevalue.getValue() / 8, (double)bookandmapscalevalue.getValue() / 8);
		
		Gui.drawRect(x - 1, y - 1, x + 129, y + 129, Color.BLACK.getRGB());
		Gui.drawRect(x, y, x + 128, y + 128, backgroundColor);
		
		MapData data = map.getMapData(mapStack, Minecraft.getMinecraft().world);
		
		if(data != null) {
			byte[] colors = data.colors;
			
			for (int i = 0; i < colors.length; ++i) {
				
	            int j = colors[i] & 255;

	            if (j / 4 == 0) {
	                drawPixel(i % 128 + x, i / 128 + y, (i + i / 128 & 1) * 8 + 16 << 24);
	            }
	            else {
	                drawPixel(i % 128 + x, i / 128 + y, MapColor.COLORS[j / 4].getMapColor(j & 3));
	            }
	        }
		}
		
		GlStateManager.scale(8 / (double)bookandmapscalevalue.getValue(), 8 / (double)bookandmapscalevalue.getValue(), 8 / (double)bookandmapscalevalue.getValue());
		
	}
	
	//helper
	private static void drawPixel(int x, int y, int color) {
		Gui.drawRect(x, y, x+1, y+1, color);
	}
	
	//Draw the big book blob
	static class BookRenderer extends Gui {
		
		private static final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		
		//private static final int outlineColor = new Color(153, 135, 108).getRGB();
				
		/**
		 * space between text and metadata
		 */
		private static final int metaSpace = 11;
		
		private String author;
		private String title;
		private String[] pages;
		private NBTTagCompound nbt;
		private int width;
		private int padding;
		private double scale;
		private Generation gen;
		//private String nbtString;
		
		private BookRenderer(ItemStack bookStack, int width, int padding, double scale) {
			this.nbt = bookStack.getTagCompound();
			//this.nbtString = nbt.toString();
			author = nbt.getString("author");
			title = nbt.getString("title");
			pages = getPageText(nbt);
			this.scale = scale;
			this.width = width;
			this.padding = padding;
			
			//Generation
			if(!nbt.hasKey("generation")) {
				//if no generation key, assume og
				gen = Generation.OG;
			} else {
				int type = nbt.getInteger("generation");
				switch(type) {
				case(0):
					gen = Generation.OG;
					break;
				case(1):
					gen = Generation.OGCOP;
					break;
				case(2):
					gen = Generation.COPCOP;
					break;
				case(3):
					gen = Generation.TAT;
				}
			}
			
			if(fontRenderer.getStringWidth(title) > width) {
				width = fontRenderer.getStringWidth(title);
			}
			if(fontRenderer.getStringWidth("By: " + author) > width) {
				width = fontRenderer.getStringWidth("By: " + author);
			}
			if(fontRenderer.getStringWidth("Generation: " + gen.toString()) > width) {
				width = fontRenderer.getStringWidth("Generation: " + gen.toString());
			}
			
			//Auto resize
			while(height() > this.width * 2) {
				this.width *= 2;
			}
			
		}
		
		//Draw white and call text
		private void draw(int x, int y) {
			
			GlStateManager.scale(scale / 8, scale / 8, scale / 8);
						
			x *= 8 / scale;
			y *= 8 / scale;
			
			x -= width / 2;
			y += height() / 2;
			
			drawRect(x, y - height() - padding * 2, x + width + padding * 2, y, backgroundColor);
			
			//draw tattering using nbt data as pseudorandom removed due to lag
			/*
			for(int i = 0; i < padding * 2 + width; i++) {				
				int index = 10 * nbtString.charAt(i % nbtString.length());
				
				int value1 = (int) (2 * Math.sin(nbtString.charAt(index % nbtString.length())));
				
				int value2 = (int) (2 * Math.sin(nbtString.charAt(Math.abs(value1 % nbtString.length()))));
				
				drawVerticalLine(x + i, y - height() - padding * 2 - 1, (int) (value1 + y - height() - padding * (7/4f)), outlineColor);
				drawVerticalLine(x + i, y + 1, (int) (value2 + y - padding * (1/4f)), outlineColor);
			}
			
			for(int i = 0; i < height() + padding * 2; i++) {
				
				int index = nbtString.charAt(i % nbtString.length()) ^ i;
				
				int value1 = (int) (2 * Math.sin(nbtString.charAt(index % nbtString.length())));
				
				int value2 = (int) (2 * Math.sin(nbtString.charAt(Math.abs(value1 % nbtString.length()))));
				
				drawHorizontalLine((int) (x + width + padding * (7/4f) + value1), x + width + padding * 2 - 1, y - i - 1, outlineColor);
				drawHorizontalLine(x, (int) (value2 + x + padding * (1/4f)), y - i - 1, outlineColor);
				
			}
			*/
			drawHorizontalLine(x + padding, x + padding + width, y - padding - fontRenderer.FONT_HEIGHT * 2 - metaSpace / 2, Color.BLACK.getRGB());

			drawText(x + padding, y - height() - padding);
			
			GlStateManager.scale(8 / scale, 8 / scale, 8 / scale);
			
		}
		
		private void drawText(int x, int y) {
						
			fontRenderer.drawString(title, x + width / 2 - fontRenderer.getStringWidth(title) / 2, y, 0);
			fontRenderer.drawString("By: " + author, x + width / 2 - fontRenderer.getStringWidth("By: " + author) / 2, y + fontRenderer.FONT_HEIGHT, 0);
			
			fontRenderer.drawSplitString(arrayToString(pages), x, y + fontRenderer.FONT_HEIGHT * 2, width, 0);
			
			int textHeight = fontRenderer.getWordWrappedHeight(arrayToString(pages), width);
			
			fontRenderer.drawString("Generation: " + gen.toString(), x, y + fontRenderer.FONT_HEIGHT * 2 + textHeight + metaSpace, 0);
			fontRenderer.drawString("Pages: " + pages.length, x, y + fontRenderer.FONT_HEIGHT * 3 + textHeight + metaSpace, 0);
									
		}
		
		private int height() {
			
			int textHeight = fontRenderer.getWordWrappedHeight(arrayToString(pages), width);
			
			return fontRenderer.FONT_HEIGHT * 4 + textHeight + metaSpace;
			
		}

		private String arrayToString(String[] array) {
			StringBuffer buff = new StringBuffer();
			for(int i = 0; i < array.length; i++) {
				buff.append(array[i]);
				buff.append(" " + ColorCode.RESET.getCode());
			}
			return buff.toString();
		}
		
		private String[] getPageText(NBTTagCompound nbt) {
			
			NBTTagList pages = nbt.getTagList("pages", 8);
			
			String[] toReturn = new String[pages.tagCount()];
			
			for(int i = 0; i < pages.tagCount(); i++) {
				
				try { //10
					
					String text = ITextComponent.Serializer.jsonToComponent(pages.getStringTagAt(i)).getUnformattedText();
					
					toReturn[i] = text;
					
					
				} catch (Exception e) {
					PlayerUtils.sendMessage("Page Error");
				}
				
			}
			
			return toReturn;
		}
		
		private boolean isNBTSame(NBTTagCompound other) {
			return nbt.equals(other);
		}
		
		static enum Generation {
			OG, OGCOP, COPCOP, TAT;
			
			@Override
			public String toString() {
				switch(this) {
				case OG:
					return "Original";
				case OGCOP:
					return "Copy of Original";
				case COPCOP:
					return "Copy of a Copy";
				case TAT:
					return "Tattered";
				default:
					return "ERROR";
				}
			}
			
		}
		
	}
	
}
