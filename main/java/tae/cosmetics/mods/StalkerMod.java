package tae.cosmetics.mods;

import java.util.Set;

import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import tae.cosmetics.ColorCode;
import tae.cosmetics.gui.GuiScreenStalkerMod;
import tae.cosmetics.util.PlayerAlert;
import tae.packetevent.PacketEvent;

public class StalkerMod extends BaseMod {
	
	@SubscribeEvent
	public void disconnect(ClientDisconnectionFromServerEvent event) {
		
		PlayerAlert.getUUIDs().forEach(x -> PlayerAlert.updateOnline(x, false));
		
	}
		
	@SubscribeEvent
	public void incoming(PacketEvent.Incoming event) {
		
		if(mc.currentScreen instanceof GuiScreenStalkerMod && event.getPacket() instanceof SPacketTabComplete) {
			
			SPacketTabComplete packet = (SPacketTabComplete) event.getPacket();
			
			for(String s : packet.getMatches()) {
				for(String uuid : PlayerAlert.getUUIDs()) {
					String name = PlayerAlert.oldName(uuid);
					if(s.equals(name)) {
							PlayerAlert.toggleOnline(uuid, true);
						break;
					}
				}
			}
			
		}
		
		if(event.getPacket() instanceof SPacketPlayerListItem) {
			
			SPacketPlayerListItem packet = (SPacketPlayerListItem) event.getPacket();
			
			String uuid = packet.getEntries().get(0).getProfile().getId().toString().replace("-","");
			
			Set<String> uuids = PlayerAlert.getUUIDs();
			
			if(packet.getAction()==SPacketPlayerListItem.Action.ADD_PLAYER) {
				
				removeFromCache(uuid);
				if(uuids.contains(uuid)) {
					PlayerAlert.toggleOnline(uuid, true);
					
					PlayerAlert.updateQueuePositions();
					
					if(PlayerAlert.joinAlert(uuid)) {	
						String raw = PlayerAlert.getMessage(uuid);
						String message = String.format(raw,"joined");
						sendMessage(message,ColorCode.GOLD,ColorCode.BOLD);
					}
				}
			
			}
			
			
			if(packet.getAction()==SPacketPlayerListItem.Action.REMOVE_PLAYER) {
				
				if(!inCache(uuid)) {
					addToCache(uuid);
					if(uuids.contains(uuid)) {
						PlayerAlert.toggleOnline(uuid, false);
						if(PlayerAlert.joinAlert(uuid)) {	
							String raw = PlayerAlert.getMessage(uuid);
							String message = String.format(raw,"left");
							sendMessage(message,ColorCode.GOLD,ColorCode.BOLD);
						}
					}
				}
			}
		}
	}
	
	private static final int cachesize = 10;
	private static String[] leaveCache = new String[cachesize];
	
	private static void addToCache(String uuid) {
		String[] temp = new String[cachesize];
		temp[0] = uuid;
		for(int i = 0; i < cachesize - 1; i++) {
			temp[i+1] = leaveCache[i];
		}
		leaveCache = temp;
	}
	
	private static void removeFromCache(String uuid) {
		for(int i = 0; i < cachesize; i++) {
			if(uuid.equals(leaveCache[i])) {
				leaveCache[i] = null;
			}
		}
	}
	
	private static boolean inCache(String uuid) {
		for(String string : leaveCache) {
			if(uuid.equals(string)) {
				return true;
			}
		}
		return false;
	}

}