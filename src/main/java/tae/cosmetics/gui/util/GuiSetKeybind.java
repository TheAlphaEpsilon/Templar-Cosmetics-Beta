package tae.cosmetics.gui.util;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.math.MathHelper;

public class GuiSetKeybind extends GuiTextField {

	private int keyCode;
	private FontRenderer fontRenderer;
	
	public GuiSetKeybind(int componentId, FontRenderer fontrendererObj, int x, int y, int par6Height, int defaultKeyCode) {
		super(componentId, fontrendererObj, x, y, 0, par6Height);
		fontRenderer = fontrendererObj;
		keyCode = defaultKeyCode;
		setWidthAndText();
	}
	
	@Override
	public boolean textboxKeyTyped(char typedChar, int keyCode) {
		if (!isFocused()) {
            return false;
        }
		
		if (keyCode == Keyboard.KEY_BACK) {
			this.keyCode = Keyboard.KEY_NONE;
			setWidthAndText();
			return true;
		}
		
		if (true || ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
			this.keyCode = keyCode;
			setWidthAndText();
			return true;
		}
		return false;
	}
	
	@Override
	public void drawTextBox() {
		 if (this.getVisible()) {
			 
	            if (this.getEnableBackgroundDrawing()) {
	                if(isFocused()) {
		            	drawRect(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, Color.YELLOW.getRGB());
	                } else {
		            	drawRect(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, -6250336);
	                }
	            	
	                drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -16777216);
	            }
	            
	            int i =  14737632;
	            int l = this.x + 4;
	            int i1 = this.y + (this.height - 8) / 2;
	            
	            this.fontRenderer.drawStringWithShadow(getText(), (float)l, (float)i1, i);
	            
		 }
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	private void setWidthAndText() {
		width = MathHelper.clamp(fontRenderer.getStringWidth(Keyboard.getKeyName(keyCode)) + 7, 12, 100);
		super.setText(Keyboard.getKeyName(keyCode));	
	}
	
}
