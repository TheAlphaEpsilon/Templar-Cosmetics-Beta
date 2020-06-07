package tae.cosmetics.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.minecraft.util.text.TextComponentString;
import tae.cosmetics.ColorCode;
import tae.cosmetics.Globals;

public class PlayerAlert implements Globals{
	
	private static HashMap<String, PlayerData> playeruuidmap = new HashMap<String, PlayerData>();
	
	public static Set<String> getUUIDs() {
		return playeruuidmap.keySet();
	}
	
	public static boolean alertExists(String name) {
		String[] data = MojangGetter.getInfoFromName(name);
		if(data == null) return false;
		return playeruuidmap.containsKey(data[0]);
	}
	
	public static String addName(String name) {
		if(alertExists(name)) return null;
		String[] data = MojangGetter.getInfoFromName(name);
		if(data == null) return null;
		playeruuidmap.put(data[0], new PlayerData(data[0], data[1]));
		return data[1];
	}
	
	public static boolean removeName(String name) {
		String[] data = MojangGetter.getInfoFromName(name);
		if(data == null) return false;
		String playeruuid = data[0];
		if(playeruuidmap.remove(playeruuid) == null) {
			return false;
		} 
		return true;
	}
	
	public static void removeUUID(String uuid) {
		playeruuidmap.remove(uuid);
	}
	
	
	public static void addFromConfig(String uuid, String name, String prefix, boolean alert, boolean queue, boolean assumePrio) {
		playeruuidmap.put(uuid, new PlayerData(uuid, name, prefix, alert, queue, assumePrio));
	}
	
	public static String getMessage(String uuid) {
		if(playeruuidmap.get(uuid) == null) return null;
		String setname = playeruuidmap.get(uuid).oldname;
		String realname = MojangGetter.getCurrentNameFromUUID(uuid);
		if(realname == null || setname.equals(realname)) {
			return "Player " + setname + " has %s";
		} else {
			return "Player " + realname + " has %s. Previously " + setname;
		}
	}
	
	public static void updateQueuePositions() {
		
		ArrayList<String> queueUUIDS = RebaneGetter.getUUIDs();
		
		for(String uuid : playeruuidmap.keySet()) {
			
			PlayerData data = playeruuidmap.get(uuid);
			
			if(queueUUIDS.contains(uuid)) { //If that uuid is in rebane's site
				
				//depreciated
				 /* if(data.initQueueState) { //If was online on boot
					
					if(data.epochJoined == -1) {
						data.epochJoined = RebaneGetter.getWhenJoined(uuid);
					}
					
					if(data.epochJoined != -1 && data.prioQueueOnJoin == -1) {
						data.prioQueueOnJoin = API2b2tdev.prioQueueFromEpoch(data.epochJoined);
					}
					
					if(data.epochJoined != -1 && data.totalQueueOnJoin == -1) {
						data.totalQueueOnJoin = API2b2tdev.normQueueFromEpoch(data.epochJoined);
					}
					
					if(data.assumePrio) {
						data.queuePos = data.prioQueueOnJoin - (data.totalQueueOnJoin - RebaneGetter.getUUIDs().indexOf(uuid) + 1);
					} else {
						data.queuePos = data.totalQueueOnJoin - RebaneGetter.getUUIDs().indexOf(uuid) + 1;
					}
					
				} else { //Only join q once user in game
					
					if(data.epochJoined == -1) {
						data.epochJoined = Instant.now().getEpochSecond();
					}
					
					if(data.prioQueueOnJoin == -1) {
						data.prioQueueOnJoin = API2b2tdev.getPrioSize();
					}
					
					if(data.totalQueueOnJoin == -1) {
						data.totalQueueOnJoin = API2b2tdev.normQueueFromEpoch(data.epochJoined);
					}
					
					if(data.assumePrio) {
						data.queuePos = ((RebaneGetter.getUUIDs().indexOf(uuid) + 1) * API2b2tdev.getPrioSize()) / RebaneGetter.getSize();
					} else {
						data.queuePos = ((RebaneGetter.getUUIDs().indexOf(uuid) + 1) * API2b2tdev.normQueueFromEpoch(Instant.now().getEpochSecond()) ) / RebaneGetter.getSize();
					}
					
				} */
				
				if(data.assumePrio) {
					data.queuePos = ((RebaneGetter.getUUIDs().indexOf(uuid) + 1) * API2b2tdev.getPrioSize()) / RebaneGetter.getUUIDs().size();
				} else {
					data.queuePos = ((RebaneGetter.getUUIDs().indexOf(uuid) + 1) * RebaneGetter.getSize()) / RebaneGetter.getUUIDs().size();
				}
				
				if(!data.sentInQueue) {
					PlayerUtils.sendMessage(String.format(getMessage(uuid), "joined the queue. [" + data.queuePos + "]"), ColorCode.GOLD);
					data.sentInQueue = true;
				}
				
				if(data.inGame) { //Player is online
					data.queuePos = -1;
				}
				
				data.wasInQueue = true;
				data.sentLeftQueue = false;
				
			} else { //uuid isnt in rebane's site
				
				data.queuePos = -1;
				
				data.sentInQueue = false;
				
				if(!data.inGame && !data.sentLeftQueue && data.wasInQueue) {
					PlayerUtils.sendMessage(String.format(getMessage(uuid), "left the queue."), ColorCode.GOLD);
					data.sentLeftQueue = true;
				}
				
				data.wasInQueue = false;
				
			}
			
		}
	}
	
	public static void updateSendAlert(String uuid, boolean bool) {
		if(playeruuidmap.containsKey(uuid)) {
			playeruuidmap.get(uuid).alert = bool;
		}
	}
	
	public static void updateQueueSendAlert(String uuid, boolean bool) {
		if(playeruuidmap.containsKey(uuid)) {
			playeruuidmap.get(uuid).queue = bool;
		}
	}
	
	public static void updatePrefix(String uuid, String prefix) {
		if(playeruuidmap.containsKey(uuid)) {
			playeruuidmap.get(uuid).chatPrefix = prefix;
		}
	}
	
	public static void updateName(String uuid, String newname) {
		if(playeruuidmap.containsKey(uuid)) {
			playeruuidmap.get(uuid).oldname = newname;
		}
	}
	
	public static void toggleOnline(String uuid, boolean bool) {
		if(playeruuidmap.containsKey(uuid)) {
			playeruuidmap.get(uuid).inGame = bool;
		}
	}
	
	public static void updateQueuePos(String uuid, int indexOf) {
		if(playeruuidmap.containsKey(uuid)) {
			playeruuidmap.get(uuid).queuePos = indexOf;
		}
	}
	
	public static void updateAssumePrio(String uuid, boolean state) {
		if(playeruuidmap.containsKey(uuid)) {
			playeruuidmap.get(uuid).assumePrio = state;
		}
	}
	
	public static void updateOnline(String uuid, boolean state) {
		if(playeruuidmap.containsKey(uuid)) {
			playeruuidmap.get(uuid).inGame = state;
		}
	}
	
	public static boolean inGame(String uuid) {
		
		if(!playeruuidmap.containsKey(uuid)) {
			return false;
		}
		
		return playeruuidmap.get(uuid).inGame;
	}
	
	public static int queuePos(String uuid) {
		
		if(!playeruuidmap.containsKey(uuid)) {
			return -1;
		}
		
		return playeruuidmap.get(uuid).queuePos;
	}
	
	public static String oldName(String uuid) {
		
		if(!playeruuidmap.containsKey(uuid)) {
			return "";
		}
		
		return playeruuidmap.get(uuid).oldname;
	}
	
	public static boolean needsUpdate(String uuid) {
		if(!playeruuidmap.containsKey(uuid)) {
			return false;
		}
		return playeruuidmap.get(uuid).needNameUpdate();
	}
	
	public static String prefix(String uuid) {
		if(!playeruuidmap.containsKey(uuid)) {
			return "";
		}
		return playeruuidmap.get(uuid).chatPrefix;
	}
	
	public static boolean joinAlert(String uuid) {
		if(!playeruuidmap.containsKey(uuid)) {
			return false;
		}
		return playeruuidmap.get(uuid).alert;
	}
	
	public static boolean queueAlert(String uuid) {
		if(!playeruuidmap.containsKey(uuid)) {
			return false;
		}
		return playeruuidmap.get(uuid).queue;
	}
	
	public static boolean assumePrio(String uuid) {
		if(!playeruuidmap.containsKey(uuid)) {
			return false;
		}
		return playeruuidmap.get(uuid).assumePrio;
	}
	
	public static String newName(String uuid) {
		if(!playeruuidmap.containsKey(uuid)) {
			return "";
		}
		return playeruuidmap.get(uuid).newname;
	}
	
	static class PlayerData {
		
		private String uuid;
		private String oldname;
		private String newname;
		private String chatPrefix;
		private boolean inGame;
		private boolean alert;
		private boolean queue;
		private boolean sentInQueue;
		private boolean sentLeftQueue;
		private boolean wasInQueue;
		private boolean assumePrio;
		private int queuePos;
		
		private PlayerData(String uuid, String oldname) {
			this(uuid, oldname, "", true, true, false);
		}
		
		private PlayerData(String uuid, String oldname, String prefix, boolean alert, boolean queue, boolean assumePrio) {
			this.oldname = oldname;
			this.uuid = uuid;
			chatPrefix = prefix;
			newname = MojangGetter.getCurrentNameFromUUID(uuid);
			inGame = false;
			sentInQueue = false;
			sentLeftQueue = false;
			wasInQueue = false;
			queuePos = -1;
			this.alert = alert;
			this.queue = queue;
			this.assumePrio = assumePrio;
		}
		
		private boolean needNameUpdate() {
			if(newname == null) return false;
			return !oldname.equals(newname);
		}
		
		
	}

}