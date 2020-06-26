package tae.cosmetics.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import tae.cosmetics.ColorCode;
import tae.cosmetics.gui.util.GuiOnOffButton;

public class BookArt extends AbstractTAEGuiScreen {

	private static final int bookWidth = 14; //height oops
	private static final int bookHeight = 12; //width
	
	private static Pixel[][] pixels = new Pixel[bookWidth][bookHeight];
	
	private static final Pixel[][] prevCache = new Pixel[bookWidth][bookHeight];
	
	private static final Pixel[][] postCache = new Pixel[bookWidth][bookHeight];
	
	private static final GuiButton[] buttonSize = new GuiButton[15];
	
	private static final GuiButton[] buttonColor = new GuiButton[16];
	
	private static final GuiButton[] buttonFormat = new GuiButton[4];
		
	private static int xStart = 0;
	private static int yStart = 0;
	
	static {
		for(int i = 0; i < pixels.length; i++) {
			for(int j = 0; j < pixels[i].length; j++) {
				pixels[i][j] = new Pixel();
			}
		}
		for(int i = 0; i < 9; i++) {
			buttonSize[i] = new GuiButton(i, 0, 0, 20, 20, Character.toString((char)(0x2580 + i)));
		}
		
		buttonSize[9] = new GuiButton(9, 0, 0, 20, 20, Character.toString((char)(0x2580 + 16)));

		buttonSize[10] = new GuiButton(10, 0, 0, 20, 20, Character.toString((char)(0x2580 + 18)));
		
		buttonSize[11] = new GuiButton(11, 0, 0, 20, 20, Character.toString((char)(0x2580 + 19)));
		
		for(int i = 12; i < 15; i++) {
			buttonSize[i] = new GuiButton(i, 0, 0, 20, 20, Character.toString((char)(0x2580 + 13 + i)));
		}
		
		int index = 0;
		int counter = 0;
		for(ColorCode c : ColorCode.values()) {
			if(c.getType() == 0) {
				buttonColor[index] = new GuiButton(32 + counter, 0, 0, 20, 20, ColorCode.values()[counter].getCode() + (char)0x2588);
				index++;
			}
			counter++;
		}
		index = 0;
		counter = 0;
		for(ColorCode c : ColorCode.values()) {
			if(c.getType() == 1 && c != ColorCode.BOLD && c != ColorCode.OBFUSCATED) {
				buttonFormat[index] = new GuiButton(64 + counter, 0, 0, 20, 20, ColorCode.values()[counter].getCode() + (char)0x2588);
				index++;
			}
			counter++;
		}
	
	}
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation("taecosmetics","textures/gui/playeroptions.png");
    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation("textures/gui/book.png");
    
    private static GuiButton currentButtonSize;
    private static GuiButton currentButtonColor;
    private static GuiButton currentButtonFormat;
    
    private static GuiButton buttonSingle;
	private static GuiButton buttonAll;
	
	private static GuiButton buttonBack;
	private static GuiButton buttonForward;
	
	private static GuiButton addPage;
	
	private static GuiOnOffButton mode2b2t;
	
	private static boolean shorten = false;
	        
	protected BookArt(GuiScreen parent) {
		super(parent);
		
		guiwidth = 350;
		guiheight = 220;
		
		buttonSingle = new GuiButton(97, 0, 0, 20, 20, Character.toString((char) 0x2196));
		buttonSingle.enabled = false;
		buttonAll = new GuiButton(98, 0, 0, 20, 20, Character.toString((char) 0x2294));
		buttonBack = new GuiButton(100, 0, 0, 20, 20, Character.toString((char) 0x27f2));
		buttonBack.enabled = false;
		
		buttonForward = new GuiButton(101, 0, 0, 20, 20, Character.toString((char) 0x27f3));
		buttonForward.enabled = false;
		
		addPage = new GuiButton(99, 0, 0, 90, 20, "Add this page");
		
		mode2b2t = new GuiOnOffButton(102, 0, 0, 130, 20, "2b2t mode: ", false);
	}

	@Override
	public void initGui() {
		
		for(int i = 0; i < buttonSize.length; i++) {
			buttonList.add(buttonSize[i]);
		}
		for(int i = 0; i < buttonColor.length; i++) {
			buttonList.add(buttonColor[i]);
		}
		for(int i = 0; i < buttonFormat.length; i++) {
			buttonList.add(buttonFormat[i]);
		}
		
		buttonList.add(buttonSingle);
		buttonList.add(buttonAll);
		
		buttonList.add(buttonBack);
		buttonList.add(buttonForward);
		
		buttonList.add(addPage);
		
		buttonList.add(mode2b2t);
		
		super.initGui();
		
	}
	
	protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
		int i = width / 2;
		int j = height / 2;
		drawPseudoBook(i, j - 10, mouseX, mouseY);
	}
	
	private void drawPseudoBook(int x, int y, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
        this.drawTexturedModalRect(x, y - 192 / 2 + 6, 0, 0, 192, 192);
        ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(updateText());
        List<ITextComponent> lines = GuiUtilRenderComponents.splitText(itextcomponent, 116, this.fontRenderer, true, true);
        int k1 = Math.min(128 / this.fontRenderer.FONT_HEIGHT, lines.size());
        for (int l1 = 0; l1 < k1; ++l1)
        {
            ITextComponent itextcomponent2 = lines.get(l1);
            this.fontRenderer.drawString(itextcomponent2.getUnformattedText(), x + 36, y - 192 / 2 + 32 + l1 * this.fontRenderer.FONT_HEIGHT, 0);
        }
        xStart = x + 36;
        yStart = y - 192 / 2 + 32;
       
	}
	
	private static String updateText() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"text\":\"");
		for(int i = 0; i < pixels.length; i++) {
			for(int j = 0; j < pixels[i].length; j++) {
				if(j > 0 && pixels[i][j].equals(pixels[i][j-1])) {
					builder.append(pixels[i][j].c);
				} else if(j > 0 && pixels[i][j].code == pixels[i][j-1].code){
					builder.append(pixels[i][j].format.getCode() + pixels[i][j].c);
				} else if(j > 0 && pixels[i][j].format == ColorCode.RESET && pixels[i][j - 1].format != ColorCode.RESET) {
					builder.append(ColorCode.RESET.getCode() + pixels[i][j].getPixel());
				} else {
					builder.append(pixels[i][j].getPixel());
				}
			}
			if(i != pixels.length - 1) {
				builder.append("\\n");
			}
		}
		
		if(shorten && builder.length() > "{\"text\":\"".length() + 256) {
			builder.setLength("{\"text\":\"".length() + 256);
		}
		
		builder.append("\"}");
		return builder.toString();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		int yCoord = -1;
		for(int i = 0; i < pixels.length; i++) {
			if(mouseY >= yStart + i * fontRenderer.FONT_HEIGHT && mouseY <= yStart + (i+1) * fontRenderer.FONT_HEIGHT) {
				yCoord = i;
				break;
			}
		}
		
		int xCoord = -1;
		if(yCoord > -1) {
			for(int i = 0; i < pixels[yCoord].length; i++) {
				if(mouseX >= xStart + i * fontRenderer.getCharWidth((char) 0x2588) && mouseX <= xStart + (i + 1) * fontRenderer.getCharWidth((char) 0x2588)) {
					xCoord = i;
					break;
				}
			}
		}
		
		if(xCoord > -1 && yCoord > -1) {
			if(buttonAll.enabled == false) {
				updatePrevCache();
				mapFromPixel(yCoord, xCoord);
			} else {
				updatePrevCache();
				updatePixel(pixels[yCoord][xCoord]);
			}
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	private static void updatePrevCache() {
		
		buttonForward.enabled = false;
		if(!buttonBack.enabled) {
			buttonBack.enabled = true;
		}
		
		deepArrayCopy(prevCache, pixels);
		
	}
	
	private static void mapFromPixel(int yCoord, int xCoord) {
		mapFromPixel0(new Pixel(pixels[yCoord][xCoord]), yCoord, xCoord, true);
	}
	
	private static void mapFromPixel0(Pixel original, int yCoord, int xCoord, boolean first) {
		if(yCoord < 0 || xCoord < 0 || yCoord >= pixels.length || xCoord >= pixels[yCoord].length) {
			return;
		}
		
		Pixel p = pixels[yCoord][xCoord];
		
		if(first) {
			Pixel test = new Pixel();
			updatePixel(test);
			if(p.equals(test)) {
				return;
			}
		}
		
		if(!original.equals(p)) {
			return;
		}
		
		updatePixel(p);
		
		mapFromPixel0(original, yCoord - 1, xCoord, false);
		
		mapFromPixel0(original, yCoord + 1, xCoord, false);
		
		mapFromPixel0(original, yCoord, xCoord - 1, false);

		mapFromPixel0(original, yCoord, xCoord + 1, false);		
	}
	
	private static void updatePixel(Pixel pixel) {
		if(currentButtonSize != null) {
			pixel.setChar(currentButtonSize.displayString.charAt(0));
		}
		
		if(currentButtonColor != null) {
			pixel.setColor(ColorCode.values()[currentButtonColor.id - 32]);
		}
		
		if(currentButtonFormat != null) {
			pixel.setFormat(ColorCode.values()[currentButtonFormat.id - 64]);
		}
	}
	
	private static void deepArrayCopy(Pixel[][] original, Pixel[][] toCopy) {
		for(int i = 0; i < original.length; i++) {
			for(int j = 0; j < original[i].length; j++) {
				original[i][j] = new Pixel(toCopy[i][j]);
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button != back) {
			if(button.id < 32 && button != currentButtonSize) {
				if(currentButtonSize != null) {
					currentButtonSize.enabled = true;
				}
				currentButtonSize = button;
				currentButtonSize.enabled = false;
			} else if(button.id >= 32 && button.id < 64 && button != currentButtonColor) {
				if(currentButtonColor != null) {
					currentButtonColor.enabled = true;
				}
				currentButtonColor = button;
				currentButtonColor.enabled = false;
			} else if(button.id >= 64 && button.id < 96 && button != currentButtonFormat) {
				if(currentButtonFormat != null) {
					currentButtonFormat.enabled = true;
				}
				currentButtonFormat = button;
				currentButtonFormat.enabled = false;
			} else if(button == buttonAll) {
				buttonSingle.enabled = true;
				buttonAll.enabled = false;
			} else if(button == buttonSingle) {
				buttonSingle.enabled = false;
				buttonAll.enabled = true;
			} else if(button == buttonBack) {
				buttonBack.enabled = false;
				buttonForward.enabled = true;
				
				deepArrayCopy(postCache, pixels);
				
				deepArrayCopy(pixels, prevCache);
				
			} else if(button == buttonForward) {
				buttonBack.enabled = true;
				buttonForward.enabled = false;
				
				deepArrayCopy(pixels, postCache);
				
			} else if(button == addPage) {
							
				String text = updateText().replace("{\"text\":\"", "").replace("\"}", "").replace("\\n", "");
				
				//text = text.substring(0, 128);
				
				ItemStack held = mc.player.inventory.getCurrentItem();
				if(held.getItem().equals(Items.WRITABLE_BOOK)) {
					NBTTagCompound nbt = held.getTagCompound();
					
					if(nbt != null) {
						NBTTagList list = nbt.getTagList("pages", 8);
						
						if(list != null) {
							list.appendTag(new NBTTagString(text));
						} else {
							list = new NBTTagList();
							list.appendTag(new NBTTagString(text));
							nbt.setTag("pages", list);
						}
						
					} else {
						nbt = new NBTTagCompound();
						NBTTagList list = new NBTTagList();
						list.appendTag(new NBTTagString(text));
						nbt.setTag("pages", list);
						held.setTagCompound(nbt);
					}
					
					PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
	                packetbuffer.writeItemStack(held);
	                this.mc.getConnection().sendPacket(new CPacketCustomPayload("MC|BEdit", packetbuffer));
					addMessage("Added", width / 2 - 100, height / 2 - 95 + 8 * 22, 250, Color.GREEN);
				} else {
					addMessage("Please hold a book", width / 2 - 100, height / 2 - 95 + 8 * 22, 250, Color.RED);
				}
			} else if(button == mode2b2t) {
				mode2b2t.toggle();
				shorten = mode2b2t.getState();
			}
			
		}

		super.actionPerformed(button);

	}

	@Override
	public void onGuiClosed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateButtonPositions(int x, int y) {
		
		for(int i = 0; i < buttonSize.length; i++) {
			buttonSize[i].x = x - 100 + (i % 5) * 22;
			buttonSize[i].y = y - 100 + (i / 5) * 22;
		}
		
		for(int i = 0; i < buttonColor.length; i++) {
			buttonColor[i].x = x - 160 + (i % 2) * 22;
			buttonColor[i].y = y - 100 + (i / 2) * 22;
		}
		
		for(int i = 0; i < buttonFormat.length; i++) {
			buttonFormat[i].x = x - 100 + i * 22;
			buttonFormat[i].y = (int) (y - 100 + 3.5 * 22);
		}
		
		buttonSingle.x = x - 100;
		buttonSingle.y = y - 100 + 5 * 22;
		
		buttonAll.x = x - 100 + 22;
		buttonAll.y = y - 100 + 5 * 22;
		
		buttonBack.x = x - 100 + 44;
		buttonBack.y = y - 100 + 5 * 22;
		
		buttonForward.x = x - 100 + 66;
		buttonForward.y = y - 100 + 5 * 22;
		
		addPage.x = x - 100;
		addPage.y = y - 100 + 7 * 22;
		
		mode2b2t.x = x + 30;
		mode2b2t.y = y - 95 + 8 * 22;
		
	}
	
	static class Pixel {
		
		private char c;
		private ColorCode code;
		private ColorCode format;
		
		private Pixel() {
			c = 0x2588;
			code = ColorCode.BLACK;
			format = ColorCode.RESET;
		}
		
		private Pixel(Pixel p) {
			c = p.c;
			code = p.code;
			format = p.format;
		}
		
		private void setChar(char c) {
			this.c = c;
		}
		
		private void setColor(ColorCode code) {
			this.code = code;
		}
		
		private void setFormat(ColorCode code) {
			this.format = code;
		}
		
		private String getPixel() {
			return code.getCode() + (format == ColorCode.RESET ? "" : format.getCode()) + c;
		}
		
		@Override
		public boolean equals(Object pixel) {
			
			if(this == pixel) {
				return true;
			} else if(!(pixel instanceof Pixel)) {
				return false;
			} else {
				Pixel p = (Pixel) pixel;
				return this.c == p.c && this.code == p.code && this.format == p.format;
			}
					
		}
		
	}

}
