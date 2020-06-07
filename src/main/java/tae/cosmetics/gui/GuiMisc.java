package tae.cosmetics.gui;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tae.cosmetics.gui.util.GuiOnOffButton;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.mods.BaseMod;
import tae.cosmetics.mods.ChatEncryption;

public class GuiMisc extends AbstractTAEGuiScreen {

	private static final ResourceLocation BACKGROUND = new ResourceLocation("taecosmetics","textures/gui/playeroptions.png");

	private GuiOnOffButton sendEncrypt;
	private GuiOnOffButton showEncryptRaw;
	
	private GuiSetKeybind elytra = null;
	private GuiSetKeybind hover = null;
	
	public GuiMisc(GuiScreen parent) {
		super(parent);
		sendEncrypt = new GuiOnOffButton(0, 0, 0, 135, 20, "Send Encrypted Chat ", ChatEncryption.enabled);
		showEncryptRaw = new GuiOnOffButton(0, 0, 0, 135, 20, "Show Raw Text ", ChatEncryption.showRaw);
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
		
		buttonList.add(sendEncrypt);
		buttonList.add(showEncryptRaw);
		
		super.initGui();
		
	}

	@Override
	public void onGuiClosed() {
		BaseMod.setBind(4, elytra.getKeyCode());
		BaseMod.setBind(9, hover.getKeyCode());
		mc.gameSettings.saveOptions();
		
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		
		if (elytra.textboxKeyTyped(typedChar, keyCode)) {
        	
        } else if (hover.textboxKeyTyped(typedChar, keyCode)) {
        
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
			}
			
		}
		
		super.actionPerformed(button);
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		if(elytra.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		} else if(hover.mouseClicked(mouseX, mouseY, mouseButton)) {
			
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		int i = width / 2;
		int j = height / 2;
		
		updateButtonPositions(i, j);
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		
		mc.getTextureManager().bindTexture(BACKGROUND);
		Gui.drawScaledCustomSizeModalRect(i - 145, j - 110, 0, 0, 242, 192, 290, 200, 256, 256);	
		
		this.drawString(fontRenderer, "Encrypted Chat", i - 100, j - 94, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "Please note that this can be easily broken", i - 100, j - 22, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "by looking through the client's code" , i - 100, j - 8, Color.WHITE.getRGB());

		this.drawString(fontRenderer, "Send elytra packet", i - 100, j + 44, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "View maps and books", i - 100, j + 58, Color.WHITE.getRGB());
		this.drawString(fontRenderer, "(Press this key while hovering over an item)", i - 100, j + 72, Color.WHITE.getRGB());
		
		elytra.drawTextBox();
		hover.drawTextBox();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}
	
	@Override
	protected void updateButtonPositions(int x, int y) {
		
		sendEncrypt.x = x - 100;
		sendEncrypt.y = y - 70;
		
		showEncryptRaw.x = x - 100;
		showEncryptRaw.y = y - 46;
		
		elytra.x = x + 70;
		elytra.y = y + 44;
		
		hover.x = x + 70;
		hover.y = y + 58;
		
	}
	
}
