package tae.cosmetics;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.exceptions.TAEModException;
import tae.cosmetics.gui.GuiHome;
import tae.cosmetics.mods.BaseMod;
import tae.cosmetics.util.PlayerAlert;
import tae.cosmetics.util.PlayerUtils;
import tae.packetevent.PacketEvent;

public class OnLogin implements Globals {
	
	private static final String[] texts = new String[] {
			"TEMPLAR ON TOP", "TEMPLAR CUBE!", "bruh", "2 PAY 2 WIN", "EZZZZZZ", "Corner base ftw", "Don't grief mapart!",
    		"SalC1 pizza stream!", "Oldest anarchy server", "I slept on SalC1's couch"
	};
	
	private static Random rand = new Random();
	
	private static Field splashText = null;
	
	//Displays error messages during boot and help message
	private static Set<TAEModException> exceptions = new HashSet<TAEModException>();
	
	static {
		try {
			splashText = GuiMainMenu.class.getDeclaredField("field_73975_c");
			splashText.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			addError(new TAEModException(e.getClass(), e.getMessage()));
		}
	}
	
	
	public static void addError(TAEModException exception) {
		exceptions.add(exception);
	}
		
	private static boolean firstJoin = true;
	
	public static boolean sendHelp = true;
	
	@SubscribeEvent
	public void onConnect(PacketEvent.Outgoing event) {
		if(event.getPacket() instanceof CPacketCustomPayload && ((CPacketCustomPayload)event.getPacket()).getChannelName().equals("MC|Brand")) {
			
			if(sendHelp && firstJoin) {
				firstJoin = false;
				PlayerUtils.sendMessage("Thank you for using TEMPLAR cosmetics pack!",ColorCode.GREEN,ColorCode.ITALIC, ColorCode.BOLD);
				PlayerUtils.sendMessage("Press " + Keyboard.getKeyName(GuiHome.openGui.getInt()) + " to open the gui",ColorCode.GREEN,ColorCode.ITALIC);
			}
			
			for(TAEModException exception : exceptions) {
				exception.post();
			}
			
			PlayerAlert.updateQueuePositions();
						
		}
	}
	
	//Change splash text
	@SubscribeEvent
	public void mainTitle(InitGuiEvent.Post event) {
		if(event.getGui() instanceof GuiMainMenu) {
				
				GuiMainMenu menu = (GuiMainMenu) event.getGui();
				
				if(rand.nextInt() % 10 == 0) {	
					try {
						splashText.set(menu, texts[rand.nextInt(texts.length)]);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						addError(new TAEModException(e.getClass(), e.getMessage()));
					}
					
				}
						
		}
	}
	
}
