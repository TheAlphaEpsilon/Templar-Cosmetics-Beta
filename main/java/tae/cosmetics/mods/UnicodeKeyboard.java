package tae.cosmetics.mods;

import java.lang.reflect.Method;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Sets;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.exceptions.TAEModException;
import tae.cosmetics.gui.GuiBookTitleMod;
import tae.cosmetics.gui.GuiScreenStalkerModPlayerOptions;
import tae.cosmetics.gui.util.GuiDisplayListButton;

public class UnicodeKeyboard extends BaseMod {
			
	private boolean enabled = false;
	
	private static GuiScreen gui = null;
		
	private TextType thisType = TextType.NONE;
	
	//put the button in
	@SubscribeEvent
	public void initGui(InitGuiEvent.Post event) {
		
		if(
				event.getGui() instanceof GuiCommandBlock ||
				event.getGui() instanceof GuiBookTitleMod ||
				event.getGui() instanceof GuiRepair ||
				event.getGui() instanceof GuiEditSign ||
				event.getGui() instanceof GuiScreenStalkerModPlayerOptions ||
				event.getGui() instanceof GuiScreenBook) {
				
			//int i = event.getGui().width / 2;
			//int j = event.getGui().height / 2;
				
			gui = event.getGui();
			
			event.getButtonList().add(new GuiDisplayListButton<TextType>(69, 0, 0, 80, 20, Sets.newHashSet(TextType.values()), TextType.NONE));
		}
		
	}
	
	//handle button click
	@SubscribeEvent
	public void click(ActionPerformedEvent.Post event) {
		GuiButton button = event.getButton();
		if(button.id == 69 && button instanceof GuiDisplayListButton<?>) {
			((GuiDisplayListButton<?>) button).nextValue();
			thisType = (TextType) ((GuiDisplayListButton<?>) button).getValue();
			if(thisType == TextType.NONE) {
				enabled = false;
			} else {
				enabled = true;
			}
		}
	}
		
	//reset button on close
	@SubscribeEvent
	public void close(GuiOpenEvent event) {
		if(event.getGui() == null) {
			enabled = false;
		}
	}
	
	
	@SubscribeEvent
	public void onKeyProcess(KeyboardInputEvent.Pre event) {
		
		if(enabled) {
			
			char c = Keyboard.getEventCharacter();
			
			if(ChatAllowedCharacters.isAllowedCharacter(c)) {
				event.setCanceled(true);
				handleChar(getUnicode(c, thisType));
			}
		}
		
	}
	
	
	
	private static char getUnicode(char c, TextType type) {
		
		if(c == ' ') {
			return c;
		}
		
		switch(type) {
		case NONE:
			return c;
		case WIDE:	
			 
			return (char)(0xff01 + c - 33);
			
		case CIRCLE:
			
			if(c == '0') {
				return (char)(0x24ea);
			} else if(c >= '1' && c <= '9') {
				return (char)(0x2460 + c - '1');
			} else if(c >= 'a' && c <= 'z') {
				return (char)(0x24D0 + c - 'a');
			} else if(c >= 'A' && c <= 'Z') {
				return (char)(0x24B6 + c - 'A');
			} else {
				return c;
			}
		
		case SMALL:
			
			char[] chars = {0x1d00, 0x0299, 0x1d04, 0x1d05, 0x1d07, 0x0493, 0x0262, 0x029c, 0x026a, 0x1d0a, 0x1d0b, 0x029f, 0x1d0d, 0x0274, 0x1d0f, 0x1d18, 0x01eb, 0x0280, 's', 0x1d1b, 0x1d1c, 0x1d20, 0x1d21, 'x', 0x028f, 0x1d22};
			
			char x = Character.toUpperCase(c);
			
			if(x >= 'A' && x <= 'Z') {
				return chars[x - 'A'];
			} else {
				return c;
			}
			
		case UPPER:
			
			char[] chars1 = {0x1d43, 0x1d47, 0x1d9c, 0x1d48, 0x1d49, 0x1da0, 0x1d4d, 0x02b0, 0x1da6, 0x02b2, 0x1d4f, 0x02e1, 0x1d50, 0x207f, 0x1d52, 0x1d56, 0x146b, 0x02b3, 0x02e2, 0x1d57, 0x1d58, 0x1d5b, 0x02b7, 0x02e3, 0x02b8, 0x1dbb};
			
			char x1 = Character.toUpperCase(c);
			
			if(x1 >= 'A' && x1 <= 'Z') {
				return chars1[x1 - 'A'];
			} else {
				return c;
			}
			
		default:
			return c;
		}
				
	}
	
	
	private static void handleChar (char c) {

		try {
		
			Class<?> clazz = gui.getClass();
			Method keyTyped = clazz.getDeclaredMethod("func_73869_a", char.class, int.class);
			keyTyped.setAccessible(true);
						
			keyTyped.invoke(gui, c, 0);
			
		} catch (Exception e) {
			new TAEModException(e.getClass(),e.getMessage()).post();
		}
		
	}
	
	public void none(char i, int j) {}
	
	public static String toUnicode(String in, TextType type) {
		
		StringBuffer buff = new StringBuffer();
		
		for(char c : in.toCharArray()) {
			buff.append(getUnicode(c,type));
		}
		
		return buff.toString();
		
	}
	
	public static enum TextType {
		
		
		NONE, WIDE, CIRCLE, SMALL, UPPER;
		
		public String toString() {
			return toUnicode(super.toString(), this);
		}
		
	}
	
}
