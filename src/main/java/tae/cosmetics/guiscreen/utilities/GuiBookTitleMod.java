package tae.cosmetics.guiscreen.utilities;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import tae.cosmetics.exceptions.TAEModException;
import tae.cosmetics.gui.util.ContainerBookTitleMod;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.gui.util.NoFormatFontRenderer;
import tae.cosmetics.guiscreen.AbstractTAEGuiScreen;
import tae.cosmetics.guiscreen.GuiHome;
import tae.cosmetics.guiscreen.GuiScreenUtilities;
import tae.cosmetics.mods.UnicodeKeyboard;
import tae.cosmetics.mods.UnicodeKeyboard.TextType;
import tae.cosmetics.settings.Keybind;

public class GuiBookTitleMod extends GuiContainer {

	public static final Keybind openGui = new Keybind("Open Book Title Mod",0, () -> {
		AbstractTAEGuiScreen.displayScreen(new GuiBookTitleMod());
	});
	
    private static final ResourceLocation BOOKTITLE_RESOURCE = new ResourceLocation("taecosmetics","textures/gui/booktitle.png");
    
    private static final String sign = UnicodeKeyboard.toUnicode("TEMPLAR", TextType.SMALL);
	
    private static final Color[] colorList = new Color[] {
    	Color.BLACK, new Color(0, 0, 170), new Color(0, 170, 0), new Color(0, 170, 170), new Color(170, 0, 0),
    	new Color(170, 0, 170), new Color(255, 170, 0), new Color(170, 170, 170), new Color(85, 85, 85),
    	new Color(85, 85, 255), new Color(85, 255, 85), new Color(85, 255, 255), new Color(255, 85, 85),
    	new Color(255, 85, 255), new Color(255, 255, 85), Color.WHITE
    };
    
    private static final char[] formatList = new char[] {
    	'k', 'l', 'm', 'n', 'o', 'r'	
    };
    	
    private GuiTextField nameField;
    private GuiButton colorcodebutton;
    private ContainerBookTitleMod bookContainer;
    private NoFormatFontRenderer noFormatRenderer;
    
    private Color guiText = new Color(0, 255, 0, 255);
    
    private boolean firstText = true;
    
    private int errorMessageTicks = -1;
    private String errorMessage = "";
    
    private static final int xOffset = 0;
    private static final int yOffset = -10;
	
    public AbstractTAEGuiScreen settingsScreen;
    
	public GuiBookTitleMod() {
		super(new ContainerBookTitleMod(xOffset, yOffset));
		bookContainer = (ContainerBookTitleMod) this.inventorySlots;
				
		//init colorcode button
        colorcodebutton = new GuiButton(0, 0, 0, 100, 20, "Add Color Code");
        
        settingsScreen = new AbstractTAEGuiScreen(new GuiScreenUtilities(new GuiHome())) {

        	{
        		guiwidth = 200;
        		guiheight = 100;
        	}
        	
        	private GuiSetKeybind openGuiKeyBind = null;
        	private String keyBindInfo = "Keybind to open book title mod";
        	
        	@Override
        	public void initGui() {
        		if(openGuiKeyBind == null) {
        			openGuiKeyBind = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, openGui.getInt());
        			openGuiKeyBind.setTextColor(-1);
        			openGuiKeyBind.setDisabledTextColour(-1);
        			openGuiKeyBind.setEnableBackgroundDrawing(true);
        			openGuiKeyBind.setMaxStringLength(32);
        			openGuiKeyBind.setCanLoseFocus(true);
        		}
        		super.initGui();
        	}
        	
			@Override
			public void onGuiClosed() {
				Keyboard.enableRepeatEvents(false);
				
				openGui.updateBinding(openGuiKeyBind.getKeyCode());
								
				mc.gameSettings.saveOptions();
			}
			
			@Override
			protected void keyTyped(char typedChar, int keyCode) throws IOException {
				if (openGuiKeyBind.textboxKeyTyped(typedChar, keyCode)) {
		        } else {	
		            super.keyTyped(typedChar, keyCode);
		        }
		    }
			
			@Override
			protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
				super.mouseClicked(mouseX, mouseY, mouseButton);
				if(openGuiKeyBind.mouseClicked(mouseX, mouseY, mouseButton)) {
				} 				
			}

			@Override
			protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
				
				int i = width / 2;
				int j = height / 2;
				
				openGuiKeyBind.drawTextBox();
				
				this.drawCenteredString(fontRenderer, keyBindInfo, i, j - 20, Color.WHITE.getRGB());
			}

			@Override
			protected void updateButtonPositions(int x, int y) {
				
				openGuiKeyBind.x = x - openGuiKeyBind.width / 2;
				openGuiKeyBind.y = y - 6;
				
				back.x = x - back.width / 2;
				back.y = y + 16;
			}
        	
        };
	}
	
	@Override
    public void initGui() {
		super.initGui();
	        
        Keyboard.enableRepeatEvents(true);
        errorMessageTicks = -1;
        
        //init button list
        if(buttonList.isEmpty()) {
            buttonList.add(colorcodebutton);
        }
        
        int i = xOffset + ((this.width - this.xSize) / 2);
        int j = yOffset + ((this.height - this.ySize) / 2);
		
        //set button location
        colorcodebutton.x = i + 20;
        colorcodebutton.y = j + 32;
        
        //init namefield
        if(nameField == null ) {        	
            noFormatRenderer = new NoFormatFontRenderer(mc.gameSettings, new ResourceLocation("minecraft","textures/font/ascii.png"), mc.renderEngine, fontRenderer.getUnicodeFlag());		
    		noFormatRenderer.onResourceManagerReload(null);
        	
        	this.nameField = new GuiTextField(0, noFormatRenderer, i + 18, j + 19, 103, 12);
        	this.nameField.setTextColor(-1);
        	this.nameField.setDisabledTextColour(-1);
        	this.nameField.setEnableBackgroundDrawing(false);
        	this.nameField.setMaxStringLength(32);
        }
        if(nameField.getText().isEmpty()) {
        	this.nameField.setText("Set Title Here");
        	firstText = true;
        }
        
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
		nameField.setFocused(false);
	}
	
	//TODO: BACK
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == colorcodebutton) {
			String oldText = nameField.getText();
			if(firstText) {
				firstText = false;
				nameField.setText("\u00a7");
			} else {
				nameField.setText(oldText + "\u00a7");
			}
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(BOOKTITLE_RESOURCE);
        
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		
		int i = xOffset + ((this.width - this.xSize) / 2);
        int j = yOffset + ((this.height - this.ySize) / 2);
		
        this.drawTexturedModalRect(i, j, 0, 0, 175, 184);
    	
        drawColorCodeHelp();
        
        if(errorMessageTicks > -1) {
        	errorMessageTicks--;
        	this.drawString(fontRenderer, errorMessage, i + 5, j + 5, Color.RED.getRGB());
        }
        
        this.drawString(fontRenderer, sign, i + 145, j + 42, guiText.getRGB());
        updateTextColor();
        
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {
            this.renameItem();
        }
        else {
            super.keyTyped(typedChar, keyCode);
        }
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
        this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
        if(this.getSlotUnderMouse() != null) {
        	try {
				sendTitlePacket(bookContainer.currentTitle);
				Minecraft.getMinecraft().addScheduledTask(() -> 
	        	Minecraft.getMinecraft().displayGuiScreen(null));
			} catch (TAEModException e) {
				errorMessage = e.getMessage();
				errorMessageTicks = 500;
			}
        }
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        
		int i = xOffset + ((this.width - this.xSize) / 2);
		int j = yOffset + ((this.height - this.ySize) / 2);
			
		//set text field location on screen update
		if(nameField.x != i + 18) nameField.x = i + 18;
		if(nameField.y != j + 19) nameField.y = j + 19;
		
		super.drawScreen(mouseX, mouseY, partialTicks);
				  		
        this.renderHoveredToolTip(mouseX, mouseY);

		GlStateManager.disableLighting();
        GlStateManager.disableBlend();
		
        this.nameField.drawTextBox();

        if(firstText && nameField.isFocused()) {
        	firstText = false;
        	nameField.setText("");
        }
    }
	
	private void renameItem() {
        String s = this.nameField.getText();
        bookContainer.updateTitle(s);
	}
	
	private void updateTextColor() {
		int red = guiText.getRed();
		int green = guiText.getGreen();
		int blue = guiText.getBlue();
		
		if(blue == 255) {
			if(green == 0) {
				red+=5;
			}
			if(red == 0) {
				green-=5;
			}
		}
		if(red == 255) {
			if(blue == 0) {
				green+=5;
			}
			if(green == 0) {
				blue-=5;
			}
		}
		if(green == 255) {
			if(red == 0) {
				blue+=5;
			}
			if(blue == 0) {
				red-=5;
			}
		}
		
		guiText = new Color(red, green, blue, 255);
		
	}
	
	private void drawColorCodeHelp() {
        
		int i = 3 + xOffset + ((this.width - this.xSize) / 2);
        int j = yOffset + ((this.height - this.ySize) / 2);
		
        for(int k = 0; k < 8; k++) {
        	this.drawString(noFormatRenderer, "\u00a7" + k + " is", i + 9, j + 61 + (k*10), Color.WHITE.getRGB());
        	this.drawString(fontRenderer, "\u2588", i + 34, j + 61 + (k*10), colorList[k].getRGB());
        }
       
        for(int k = 0; k < 8; k++) {
        	this.drawString(noFormatRenderer, "\u00a7" + Integer.toHexString(k+8) + " is", i + 59, j + 61 + (k*10), Color.WHITE.getRGB());
        	this.drawString(fontRenderer, "\u2588", i + 84, j + 61 + (k*10), colorList[k+8].getRGB());

        }
    	
        for(int k = 0; k < 6; k++) {
        	this.drawString(noFormatRenderer, "\u00a7" + ((k<5)?(char)('k'+k):'r') + " is", i + 109, j + 61 + (k*12), Color.WHITE.getRGB());
        	this.drawString(fontRenderer, "\u00a7" + formatList[k] + "THIS", i + 134, j + 61 + (k*12), Color.WHITE.getRGB());
        }
        
        this.drawString(fontRenderer, "\u00a7a\u00a7oPlease note that colors must", i + 9, j + 144, Color.WHITE.getRGB());
        this.drawString(fontRenderer, "\u00a7a\u00a7ogo before formats", i + 9, j + 153, Color.WHITE.getRGB());

        this.drawString(fontRenderer, "Click the book when ready!", i + 9, j + 165, Color.WHITE.getRGB());

        
	}
	
	private static void sendTitlePacket(String title) throws TAEModException {
		ItemStack heldItem = Minecraft.getMinecraft().player.inventory.getCurrentItem();
		if(!(heldItem.getItem() instanceof ItemWritableBook)) throw new TAEModException(GuiBookTitleMod.class,"You aren't holding a book!");
		if(heldItem.getTagCompound() == null || 
				heldItem.getTagCompound().getTag("pages") == null || 
				heldItem.getTagCompound().getTag("pages").isEmpty()) throw new TAEModException(GuiBookTitleMod.class,"Nothing has been written!");
		heldItem.setTagInfo("author", new NBTTagString(Minecraft.getMinecraft().getSession().getUsername()));
		heldItem.setTagInfo("title", new NBTTagString(title));
				
		PacketBuffer buff = new PacketBuffer(Unpooled.buffer());
		buff.writeItemStack(heldItem);
		CPacketCustomPayload newPacket = new CPacketCustomPayload("TAE|BSign",buff);
		Minecraft.getMinecraft().addScheduledTask(() ->
		Minecraft.getMinecraft().player.connection.sendPacket(newPacket));
	}
	
}
