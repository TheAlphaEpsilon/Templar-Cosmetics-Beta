package tae.cosmetics.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.minecraft.util.text.TextComponentString;
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
		ArrayList<String> uuidArray = RebaneGetter.getUUIDs();
		for(String uuid : playeruuidmap.keySet()) {
			
			//name is in queue cache
			if(uuidArray.contains(uuid)) {
				
				//player isnt online
				PlayerData player;
				if(!((player = playeruuidmap.get(uuid)).inGame)) {
					
					//set queue pos
					int currentQueuePos = uuidArray.indexOf(uuid) + 1;
					
					if(player.epochJoined < 0) {
						player.epochJoined = RebaneGetter.getWhenJoined(uuid);
					}
					
					if(player.totalQueueOnJoin < 0 || player.queuePos < 1) {
						if(player.epochJoined > 0) {
							player.totalQueueOnJoin = API2b2tdev.normQueueFromEpoch(player.epochJoined);
						} else {
							player.totalQueueOnJoin = RebaneGetter.getSize();
						}
						
					}
					
					if(player.prioQueueOnJoin < 0 || player.queuePos < 1) {
						if(player.epochJoined > 0) {
							player.prioQueueOnJoin = API2b2tdev.prioQueueFromEpoch(player.epochJoined);
						} else {
							player.prioQueueOnJoin = API2b2tdev.getPrioSize();
						}
					}
					
					if(player.assumePrio && player.totalQueueOnJoin > 0 && player.prioQueueOnJoin > 0) {
						
						int toTest = player.prioQueueOnJoin - (player.totalQueueOnJoin - currentQueuePos);
						
						//Smoothing out times and not letting q pos go neg
						if(toTest <= 10) {
														
							toTest = (int) Math.ceil((10 * Math.exp(0.25D * (toTest - 10))));
							
						}
						
						player.queuePos = toTest;//(toTest < 1) ? currentQueuePos : toTest;
						
					} else {
						
						player.queuePos = currentQueuePos;
						
					}
					
					//Once on in q
					if(player.queue && !player.sentInQueue) {
						
						player.sentInQueue = true;
						
						player.sentLeftQueue = false;
						
						player.wasInQueue = true;
						
						String toSend = colorcode+"6Player " + playeruuidmap.get(uuid).oldname + " is in queue "+colorcode+"l[" + player.queuePos +"]"; //" + player.totalQueueOnJoin + " " + player.prioQueueOnJoin;
						if(mc.player != null) {
							mc.player.sendMessage(new TextComponentString(toSend));
						}
						
					}
					
				//player is online	
				} else {
					
					if(player.queuePos > 0) {
						int posPast = player.totalQueueOnJoin - player.queuePos;
						
						//TODO: get prio to normal ratio
						
					}
										
					if(!player.sentLeftQueue && player.wasInQueue) {
						
						player.wasInQueue = false;
						
						player.sentLeftQueue = true;
						
						String toSend = colorcode+"6Player " + playeruuidmap.get(uuid).oldname + " has left the queue." + player.queuePos;
						if(mc.player != null && player.queue && !(player.inGame && player.alert)) {
							mc.player.sendMessage(new TextComponentString(toSend));
						}
						
					}
					
					player.queuePos = -1;
					
				}
				
			//name isnt in queue		
			} else {
				
				PlayerData player = playeruuidmap.get(uuid);
				
				player.epochJoined = -1;
				
				if(player.queuePos > 0) {
					int posPast = player.totalQueueOnJoin - player.queuePos;
				
					//TODO: get prio to normal ratio
					
				}
								
				player.sentInQueue = false;
				
				if(!player.sentLeftQueue && player.wasInQueue) {
					
					player.wasInQueue = false;
					
					player.sentLeftQueue = true;
					
					String toSend = colorcode+"6Player " + playeruuidmap.get(uuid).oldname + " has left the queue.";
					if(mc.player != null && player.queue && !(player.inGame && player.alert)) {
						mc.player.sendMessage(new TextComponentString(toSend));
					}
					
				}
				
				player.queuePos = -1;
				
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
		private int totalQueueOnJoin;
		private int prioQueueOnJoin;
		private long epochJoined = -1;
		
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
			totalQueueOnJoin = -1;
			prioQueueOnJoin = -1;
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