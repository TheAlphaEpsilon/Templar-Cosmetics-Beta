package tae.cosmetics.config;

import org.lwjgl.input.Keyboard;

import net.minecraft.network.Packet;
import tae.cosmetics.ColorCode;
import tae.cosmetics.OnLogin;
import tae.cosmetics.gui.GuiCancelPackets;
import tae.cosmetics.mods.BaseMod;
import tae.cosmetics.mods.CancelPacketMod;
import tae.cosmetics.mods.QueuePeekMod;
import tae.cosmetics.mods.TimeStampMod;
import tae.cosmetics.mods.VisualizePacketsMod;
import tae.cosmetics.util.PlayerAlert;

public class ConfigManager {
	
	public static void load() {
		//send help
		OnLogin.sendHelp = ConfigHandler.getBoolean("values", "sendhelp");
		
		//stalkermod
		int playeralertsize = ConfigHandler.getInt("values", "playeralertsize");
		
		for(int i = 0; i < playeralertsize; i++) {
			String[] values = ConfigHandler.getPlayerAlert("playeralerts", ""+i);
			PlayerAlert.addFromConfig(values[0], values[1], values[2], (values[3].equals("1") ? true: false), (values[4].equals("1") ? true : false), (values[5].equals("1") ? true : false));
		}
		
		//timestamp
		TimeStampMod.enabled = ConfigHandler.getBoolean("values", "timestamp");
		TimeStampMod.hour24 = ConfigHandler.getBoolean("timestamp", "24");
		TimeStampMod.noDate = ConfigHandler.getBoolean("timestamp", "nodate");
		
		TimeStampMod.nobold = ConfigHandler.getBoolean("timestamp", "nobold");
		TimeStampMod.noitalic = ConfigHandler.getBoolean("timestamp", "noitalic");
		TimeStampMod.nounderline = ConfigHandler.getBoolean("timestamp", "nounderline");
		
		int index = ConfigHandler.getInt("timestamp", "codeindex");
		
		TimeStampMod.code = ColorCode.values()[(index == -1)?13:index];

				
		//queuepeek
		int minutes = ConfigHandler.getInt("queuepeek", "timer");
		QueuePeekMod.minutes = (minutes < 0)?10:minutes;
		
		//keybinding
		int key = ConfigHandler.getInt("keybind", "bookmod");
		BaseMod.setBind(0, (key < 0) ? 0 : key);
		key = ConfigHandler.getInt("keybind", "stalkermod");
		BaseMod.setBind(1, (key < 0) ? 0 : key);
		key = ConfigHandler.getInt("keybind", "timestampmod");
		BaseMod.setBind(2, (key < 0) ? 0 : key);
		key = ConfigHandler.getInt("keybind", "homegui");
		BaseMod.setBind(3, (key < 0) ? Keyboard.KEY_BACKSLASH : key);
		key = ConfigHandler.getInt("keybind", "elytra");
		BaseMod.setBind(4, (key < 0) ? 0 : key);
		
		//packet cancel
		int clientamount = ConfigHandler.getInt("packetcancelamount", "client");
		for(int i = 0; i < clientamount; i++) {
			index = ConfigHandler.getInt("client", "" + i );
			GuiCancelPackets.instance().addCancel(GuiCancelPackets.client.get(index));
			GuiCancelPackets.instance().toggleButton(0, index);
		}
		int serveramount = ConfigHandler.getInt("packetcancelamount", "server");
		for(int i = 0; i < serveramount; i++) {
			index = ConfigHandler.getInt("server", "" + i );
			GuiCancelPackets.instance().addCancel(GuiCancelPackets.server.get(index));
			GuiCancelPackets.instance().toggleButton(1, index);
		}
		
		//moveable gui
		
		int cancelx = ConfigHandler.getInt("moveablegui", "cancelx");
		int cancely = ConfigHandler.getInt("moveablegui", "cancely");
		int caughtx = ConfigHandler.getInt("moveablegui", "caughtx");
		int caughty = ConfigHandler.getInt("moveablegui", "caughty");
		
		CancelPacketMod.updateGui(cancelx, cancely);
		VisualizePacketsMod.updateGui(caughtx, caughty);
		
	}
	
	public static void save() {
		int counter = 0;
		
		//send help
		ConfigHandler.writeBoolean("values", "sendhelp", OnLogin.sendHelp);
	
		//stalkermod
		counter = 0;
		for(String uuid : PlayerAlert.getUUIDs()) {
			ConfigHandler.writePlayerAlert("playeralerts", "" + counter++, uuid, PlayerAlert.oldName(uuid), 
					PlayerAlert.prefix(uuid), PlayerAlert.joinAlert(uuid), PlayerAlert.queueAlert(uuid), PlayerAlert.assumePrio(uuid));
		}
		ConfigHandler.writeInt("values", "playeralertsize", counter);
		
		
		//timestamp
		ConfigHandler.writeBoolean("values", "timestamp", TimeStampMod.enabled);
		ConfigHandler.writeBoolean("timestamp", "24", TimeStampMod.hour24);
		ConfigHandler.writeBoolean("timestamp", "nodate", TimeStampMod.noDate);
		
		ConfigHandler.writeBoolean("timestamp", "nobold", TimeStampMod.nobold);
		ConfigHandler.writeBoolean("timestamp", "noitalic", TimeStampMod.noitalic);
		ConfigHandler.writeBoolean("timestamp", "nounderline", TimeStampMod.nounderline);
		
		for(int i = 0; i < ColorCode.values().length; i++) {
			if(TimeStampMod.code == ColorCode.values()[i]) {
				ConfigHandler.writeInt("timestamp", "codeindex", i);
				break;
			}
		}
				
		//queuepeek
		ConfigHandler.writeInt("queuepeek", "timer" , QueuePeekMod.minutes);
		
		//keybinding
		ConfigHandler.writeInt("keybind", "bookmod",BaseMod.getKey(0));
		ConfigHandler.writeInt("keybind", "stalkermod", BaseMod.getKey(1));
		ConfigHandler.writeInt("keybind", "timestampmod", BaseMod.getKey(2));	
		ConfigHandler.writeInt("keybind", "homegui", BaseMod.getKey(3));
		ConfigHandler.writeInt("keybind", "elytra", BaseMod.getKey(4));
				
		//packetcancel
		
		counter = 0;
		
		for(Class<? extends Packet<?>> clazz : GuiCancelPackets.instance().getCancelPackets()) {
			
			int index = GuiCancelPackets.client.indexOf(clazz);
			
			if(index > -1) {
				ConfigHandler.writeInt("client", "" + counter++, index);
			}
			
		}
		
		ConfigHandler.writeInt("packetcancelamount", "client", counter);
		
		counter = 0;
		
		for(Class<? extends Packet<?>> clazz : GuiCancelPackets.instance().getCancelPackets()) {
			
			int index = GuiCancelPackets.server.indexOf(clazz);
			
			if(index > -1) {
				ConfigHandler.writeInt("server", "" + counter++, index);
			}
			
		}
		
		ConfigHandler.writeInt("packetcancelamount", "server", counter);
		
		//moveable gui
		
		ConfigHandler.writeInt("moveablegui", "cancelx", CancelPacketMod.getGuiTitleCopy().x);
		ConfigHandler.writeInt("moveablegui", "cancely", CancelPacketMod.getGuiTitleCopy().y);
		ConfigHandler.writeInt("moveablegui", "caughtx", VisualizePacketsMod.getGuiTitleCopy().x);
		ConfigHandler.writeInt("moveablegui", "caughty", VisualizePacketsMod.getGuiTitleCopy().y);
	}
}
