package tae.cosmetics.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;
import net.minecraft.client.gui.GuiSlider.FormatHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;
import tae.cosmetics.gui.util.GuiDisplayListButton;
import tae.cosmetics.gui.util.GuiOnOffButton;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.mods.BaseMod;
import tae.cosmetics.mods.ChatEncryption;
import tae.cosmetics.mods.HoverMapAndBook;
import tae.cosmetics.mods.PearlTracking;
import tae.cosmetics.mods.QueuePeekMod;

public class GuiMisc extends AbstractTAEGuiScreen {

	private static final ResourceLocation BACKGROUND = new ResourceLocation("taecosmetics","textures/gui/playeroptions.png");

	private GuiOnOffButton sendEncrypt;
	private GuiOnOffButton showEncryptRaw;
	
	private GuiOnOffButton pearlLogging;
	
	private GuiSetKeybind elytra = null;
	private GuiSetKeybind hover = null;
	private GuiSetKeybind pearl = null;
	
	private GuiDisplayListButton<Integer> bookandmapscale;
	
	public GuiMisc(GuiScreen parent) {
		super(parent);
		guiheight = 220;
		sendEncrypt = new GuiOnOffButton(0, 0, 0, 135, 20, "Send Encrypted Chat ", ChatEncryption.enabled);
		showEncryptRaw = new GuiOnOffButton(0, 0, 0, 135, 20, "Show Raw Text ", ChatEncryption.showRaw);
		pearlLogging = new GuiOnOffButton(0, 0, 0, 135, 20, "Pearl Logging ", PearlTracking.enabled);
		
		ArrayList<Integer> list = new ArrayList<>();
		for(int i = 1; i <= 16; i++) {
			list.add(i);
		}
		
		bookandmapscale = new GuiDisplayListButton<Integer>(0, 0, 0, 30, 20, list, HoverMapAndBook.getScale());
	}

	@Override
	public void initGui() {
		
		elytra = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, BaseMod.getKey(4));
		elytra.setTextColor(-1);
		elytra.setDisabledTextColour(-1);
		elytra.setEnableBackgroundDrawing(true);
		elytra.setMaxStringLength(32);
		
		hover = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, BaseMod.getKey(9));
		hover.setTextColor(-1);
		hover.setDisabledTextColour(-1);
		hover.setEnableBackgroundDrawing(true);
		hover.setMaxStringLength(32);
		
		pearl = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, BaseMod.getKey(10));
		pearl.setTextColor(-1);
		pearl.setDisabledTextColour(-1);
		pearl.setEnableBackgroundDrawing(true);
		pearl.setMaxStringLength(32);
		
		buttonList.add(sendEncrypt);
		buttonList.add(showEncryptRaw);
		buttonList.add(pearlLogging);
		
		buttonList.add(bookandmapscale);
		
		super.initGui();
		
	}

	@Override
	public void onGuiClosed() {
		BaseMod.setBind(4, elytra.getKeyCode());
		BaseMod.setBind(9, hover.getKeyCode());
		BaseMod.setBind(10, pearl.getKeyCode());
		mc.gameSettings.saveOptions();
		
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		
		if (elytra.textboxKeyTyped(typedChar, keyCode)) {
        	
        } else if (hover.textboxKeyTyped(typedChar, keyCode)) {
        	
        } else if (pearl.textboxKeyTyped(typedChar, keyCode)) {
        
        } else {
        	
        	super.keyTyped(typedChar, keyCode);
        	
        }
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		if(button instanceof GuiOnOffButton) {
			
			((GuiOnOffButton) button).toggle();
			
			if(button == sendEncrypt) {
				ChatEncryption.enabled = ((GuiOnOffButton) button).getState();
			} else if(button == showEncryptRaw) {
				ChatEncryption.showRaw = ((GuiOnOffButton) button).getState();
			} else if(button == pearlLogging) {
				PearlTracking.enabled = ((GuiOnOffButton) button).getState();
			}
			
		} else if(button == bookandmapscale) {
			bookandmapscale.nextValue();
			HoverMapAndBook.updateScale(bookandmapscale.getValue());
		}
		
		super.actionPerformed(button);
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		if(elytra.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} else if(hover.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} else if(pearl.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
		
	}
	
	protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
		
		int i = width / 2;
		int j = height / 2;
		
		this.drawString(fontRenderer, "Encrypted Chat", i - 100, j - 94, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "Please note that this can be easily broken", i - 100, j - 22, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "by looking through the client's code" , i - 100, j - 8, Color.WHITE.getRGB());

		this.drawString(fontRenderer, "Send elytra packet", i - 100, j + 44, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "View maps and books", i - 100, j + 58, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "(Press this key while hovering over an item)", i - 100, j + 72, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "Map and book scale:", i - 100, j + 86, Color.WHITE.getRGB());
		
		elytra.drawTextBox();
		hover.drawTextBox();
		pearl.drawTextBox();
	}

	@Override
	protected void updateButtonPositions(int x, int y) {
		
		sendEncrypt.x = x - 100;
		sendEncrypt.y = y - 70;
		
		showEncryptRaw.x = x - 100;
		showEncryptRaw.y = y - 46;
		
		pearlLogging.x = x - 100;
		pearlLogging.y = y + 10;
		
		pearl.x = x + 70;
		pearl.y = y + 10;
		
		elytra.x = x + 70;
		elytra.y = y + 44;
		
		hover.x = x + 70;
		hover.y = y + 58;
		
		bookandmapscale.x = x + 10;
		bookandmapscale.y = y + 82;
		
	}
	
}
