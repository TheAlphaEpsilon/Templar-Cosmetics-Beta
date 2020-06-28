package tae.cosmetics.mods;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.gui.GuiCancelPackets;
import tae.cosmetics.gui.util.mainscreen.CancelPacketsGuiList;
import tae.cosmetics.settings.Keybind;
import tae.cosmetics.settings.Setting;
import tae.packetevent.PacketEvent;

public class CancelPacketMod extends BaseMod {
	
	public static final Keybind toggle = new Keybind("Toggle Cancel Packets",0, () -> {
		toggle();
	});
	
	private static Setting<Integer> xCoord = new Setting<>("Cancel Packets X Coord", 50);
	private static Setting<Integer> yCoord = new Setting<>("Cancel Packets Y Coord", 50);
	
	private static CancelPacketsGuiList gui = new CancelPacketsGuiList("Canceling Packets:", (int)xCoord.getValue(), (int)yCoord.getValue());
	
	public static boolean enabled = false;
	
	public static void toggle() {
		enabled = enabled ? false : true;
	}
	
	public static CancelPacketsGuiList getGuiTitleCopy() {
		return new CancelPacketsGuiList(gui.getTitle(), gui.x, gui.y);
	}
	
	public static void updateGui(int x, int y) {
		xCoord.setValue(x);
		yCoord.setValue(y);
		gui.x = x;
		gui.y = y;
	}
	
	@SubscribeEvent
	public void onPacket(PacketEvent.Outgoing event) {
		if(enabled) {
			
			for(Class<?> clazz : GuiCancelPackets.instance().getCancelPackets()) {
				
				if(clazz.isInstance(event.getPacket())) {
					event.setCanceled(true);
					break;
				}
				
			}
		}
	}
	
	@SubscribeEvent
	public void onDrawGameScreen(RenderGameOverlayEvent event) {
		
		if(!enabled || event.getType() != ElementType.TEXT) {
			return;
		}
		
		gui.draw(-1, -1);
		
	}
	
}
