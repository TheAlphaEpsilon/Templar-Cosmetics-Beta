package tae.cosmetics.mods;

import java.time.Instant;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import tae.cosmetics.Globals;
import tae.cosmetics.gui.GuiBookTitleMod;
import tae.cosmetics.gui.GuiHome;
import tae.cosmetics.gui.GuiScreenStalkerMod;
import tae.cosmetics.gui.GuiTimestampMod;
import tae.cosmetics.gui.util.packet.TimestampModule;

public class BaseMod implements Globals {
	
	private static KeyBinding[] keyBindings;
		
	static {
		// declare an array of key bindings
		keyBindings = new KeyBinding[10]; 
		
		// instantiate the key bindings
		keyBindings[0] = new KeyBinding("Open Booktitle Mod GUI", Keyboard.CHAR_NONE, "TAE Cosmetics");
		keyBindings[1] = new KeyBinding("Open Stalker Mod GUI", Keyboard.CHAR_NONE, "TAE Cosmetics");
		keyBindings[2] = new KeyBinding("Open Timestamp Mod GUI", Keyboard.CHAR_NONE, "TAE Cosmetics");
		keyBindings[3] = new KeyBinding("Open home GUI", Keyboard.KEY_BACKSLASH, "TAE Cosmetics");
		
		keyBindings[4] = new KeyBinding("Send elytra packet", Keyboard.CHAR_NONE, "TAE Cosmetics");
		
		keyBindings[5] = new KeyBinding("Freecam", Keyboard.CHAR_NONE, "TAE Cosmetics");
						  
		keyBindings[6] = new KeyBinding("Cancel Packets", Keyboard.CHAR_NONE, "TAE Cosmetics");
		
		keyBindings[7] = new KeyBinding("Toggle Track Packets", 0, "TAE Cosmetics");
		
		keyBindings[8] = new KeyBinding("Create Packet Marker", 0, "TAE Cosmetics");
		
		keyBindings[9] = new KeyBinding("View Maps and Books", 0, "TAE Cosmetics");
		
		// register all the key bindings
		for (int i = 0; i < keyBindings.length; ++i) 
		{
		    ClientRegistry.registerKeyBinding(keyBindings[i]);
		    
		}
		
	}
	
	@SubscribeEvent
	public void onKey(KeyInputEvent event) {
		if(keyBindings[0].isPressed()) {
			mc.addScheduledTask(() -> mc.displayGuiScreen(GuiBookTitleMod.instance()));
		} else if(keyBindings[1].isPressed()) {
			mc.addScheduledTask(() -> mc.displayGuiScreen(new GuiScreenStalkerMod(GuiHome.instance())));
		} else if(keyBindings[2].isPressed()) {
			mc.addScheduledTask(() -> mc.displayGuiScreen(GuiTimestampMod.instance()));
		} else if(keyBindings[3].isPressed()) {
			mc.addScheduledTask(() -> mc.displayGuiScreen(GuiHome.instance()));
		} else if(keyBindings[4].isPressed()) {
			mc.addScheduledTask(() -> {
				//TODO: make everythin automatic
				//Vector2d vector = PlayerMathHelper.getXZComponentsOfYaw();
				mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
			});
		} else if(keyBindings[5].isPressed()) {
			if(ModifiedFreecam.enabled) {
				ModifiedFreecam.onDisabled();
			} else {
				ModifiedFreecam.onEnabled();
			}
		} else if(keyBindings[6].isPressed()) {
			CancelPacketMod.toggle();
		} else if(keyBindings[7].isPressed()) {
			VisualizePacketsMod.toggle();
		} else if(keyBindings[8].isPressed()) {
			VisualizePacketsMod.addMarker(new TimestampModule(Instant.now().toEpochMilli()));
		}
	}
	
	public static int getHoverKey() {
		return keyBindings[9].getKeyCode();
	}
	
	public static int bindSize() {
		return keyBindings.length;
	}
	
	public static void setBind(int index, int key) {
		keyBindings[index].setKeyCode(key);
		KeyBinding.resetKeyBindingArrayAndHash();
	}
	
	public static int getKey(int index) {
		return keyBindings[index].getKeyCode();
	}
	
}
