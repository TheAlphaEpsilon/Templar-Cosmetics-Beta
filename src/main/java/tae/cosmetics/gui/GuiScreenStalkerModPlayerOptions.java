package tae.cosmetics.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tae.cosmetics.gui.util.GuiOnOffButton;
import tae.cosmetics.gui.util.NoFormatFontRenderer;
import tae.cosmetics.util.PlayerAlert;

public class GuiScreenStalkerModPlayerOptions extends AbstractTAEGuiScreen {
	
	private static final ResourceLocation PLAYER_OPTIONS = new ResourceLocation("taecosmetics","textures/gui/playeroptions.png");
	
	private static final int xOffset = 0;
	private static final int yOffset = -105;    
	
	private ResourceLocation skin;
	private int skinwidth;
	private int skinheight;
	
	private GuiTextField chatPrefix;
    private NoFormatFontRenderer noFormatRenderer;
    
    private GuiOnOffButton toggleAlert;
    private GuiOnOffButton toggleQueue;
    private GuiOnOffButton assumePrio;
   
    private GuiButton addColorCode = new GuiButton(3, 0, 0, 70, 20, "Colorcode");
	private GuiButton resetPrefix = new GuiButton(4, 0, 0, 35, 20, "Reset");
	private GuiButton updateName = new GuiButton(5, 0, 0, 90, 20, "Update Name");
	private GuiButton delete = new GuiButton(6, 0, 0, 70, 20, "Delete");
    
    private boolean firstText;
    
    //player data
    private String uuid;
    private String oldname;
    private String newname;
    private String prefix;
    private boolean updatename;
    private boolean deleted;
    
	public GuiScreenStalkerModPlayerOptions(GuiScreen parent, String uuid, ResourceLocation skin, int skinwidth, int skinheight) {
		super(parent);
		
		this.skin = skin;
		this.skinwidth = skinwidth;
		this.skinheight = skinheight;
				
    	this.uuid = uuid;
    	oldname = PlayerAlert.oldName(uuid);
    	newname = PlayerAlert.newName(uuid);
    	prefix = PlayerAlert.prefix(uuid);
    	deleted = false;
    	
 		toggleAlert = new GuiOnOffButton(0, 0, 0, 135, 20, "Alert Notification ", PlayerAlert.joinAlert(uuid));
 		toggleQueue = new GuiOnOffButton(1, 0, 0, 135, 20, "Queue Notification ", PlayerAlert.queueAlert(uuid));
 		assumePrio = new GuiOnOffButton(2, 0, 0, 135, 20, "Assume Priority ", PlayerAlert.assumePrio(uuid));
    	
 		firstText = prefix.isEmpty();
	}
	
	@Override
	public void initGui() {
		
		int i = width/2 + xOffset;
		int j = height/2 + yOffset;	
				
		noFormatRenderer = new NoFormatFontRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, fontRenderer.getUnicodeFlag());		
 		noFormatRenderer.onResourceManagerReload(null);

 		this.chatPrefix = new GuiTextField(0, noFormatRenderer, i - 144, j + 114, 135, 12);
    	this.chatPrefix.setTextColor(-1);
    	this.chatPrefix.setDisabledTextColour(-1);
    	this.chatPrefix.setEnableBackgroundDrawing(true);
    	this.chatPrefix.setMaxStringLength(16);
    	this.chatPrefix.setText((prefix.isEmpty())?"ADD PREFIX HERE":prefix);
 		
		Keyboard.enableRepeatEvents(true);

		this.buttonList.add(toggleAlert);
		this.buttonList.add(toggleQueue);
		this.buttonList.add(assumePrio);
		this.buttonList.add(addColorCode);
		this.buttonList.add(resetPrefix);
		this.buttonList.add(updateName);
		this.buttonList.add(delete);
		
		super.initGui();
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		if(!deleted) {
			PlayerAlert.updatePrefix(uuid, prefix);
		}	
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
        
		if(chatPrefix.mouseClicked(mouseX, mouseY, mouseButton) && firstText) {
			firstText = false;
			chatPrefix.setText("");
		}
        
    }
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (chatPrefix.textboxKeyTyped(typedChar, keyCode)) {
        }
        else {
            super.keyTyped(typedChar, keyCode);
        }
    }
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == addColorCode) {
			if(firstText) {
				firstText = false;
				chatPrefix.setText("\u00a7");
			} else {
				String prev = chatPrefix.getText();
				chatPrefix.setText(prev+"\u00a7");
			}
		} else if(button == resetPrefix) {
			prefix = "";
			chatPrefix.setText("ADD PREFIX HERE");
			firstText = true;
		} else if(button == updateName) {
			oldname = newname;
			updatename = false;
			PlayerAlert.updateName(uuid, newname);
		} else if(button == delete) {
			deleted = true;
			PlayerAlert.removeUUID(uuid);
			button = back;
		} else if(button instanceof GuiOnOffButton) {
			
			((GuiOnOffButton) button).toggle();
			
			if(button == toggleAlert) {

				PlayerAlert.updateSendAlert(uuid, ((GuiOnOffButton) button).getState());
		
			} else if(button == toggleQueue) {

				PlayerAlert.updateQueueSendAlert(uuid, ((GuiOnOffButton) button).getState());
		
			} else if(button == assumePrio) {
				
				PlayerAlert.updateAssumePrio(uuid, ((GuiOnOffButton) button).getState());
				
			}
			
		}
		super.actionPerformed(button);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		int i = width/2 + xOffset;
		int j = height/2 + yOffset;	
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		
		mc.getTextureManager().bindTexture(PLAYER_OPTIONS);
		Gui.drawScaledCustomSizeModalRect(i - 160, j - 10, 0, 0, 242, 192, 330, 230, 256, 256);	
		
		this.drawCenteredString(fontRenderer, oldname, i, j, Color.WHITE.getRGB());
		this.drawCenteredString(fontRenderer, uuid, i, j + 12, Color.WHITE.getRGB());
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		mc.getTextureManager().bindTexture(skin);
		Gui.drawScaledCustomSizeModalRect(i - 12, j + 24 , 8, 8, 8, 8, 24, 24, skinwidth, skinheight);

		chatPrefix.drawTextBox();
		if(!firstText) {
			prefix = chatPrefix.getText();
		}
		
		String preview = prefix + "<" + oldname+">";
		if(preview.length() > 22) {
			preview = preview.substring(0, 22);
		}
		this.drawString(fontRenderer, preview, i - 144, j + 130, Color.WHITE.getRGB());
		
		if(updatename) {
			this.drawString(fontRenderer, "This player's name is now:", i + 12, j + 114, Color.WHITE.getRGB());
			this.drawString(fontRenderer, newname, i + 12, j + 126, Color.WHITE.getRGB());
		} else {
			this.drawString(fontRenderer, "Name up to date.", i + 12, j + 126, Color.WHITE.getRGB());
		}
				
		updateName.enabled = updatename;
		
		updateButtonPositions(i, j);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
        
	}

	@Override
	protected void updateButtonPositions(int x, int y) {
		
		toggleAlert.x = x - 146;
		toggleAlert.y = y + 60;
		
		toggleQueue.x = x + 12;
		toggleQueue.y = y + 60;
		
		assumePrio.x = x - 70;
		assumePrio.y = y + 85;
		
		addColorCode.x = x - 85;
		addColorCode.y = y + 142;
		
		resetPrefix.x = x - 145;
		resetPrefix.y = y + 142;
		
		updateName.x = x + 12;
		updateName.y = y + 142;
		
		back.x = x - 70;
		back.y = y + 190;
		
		delete.x = x + 12;
		delete.y = y + 190;
		
	}
	
}
