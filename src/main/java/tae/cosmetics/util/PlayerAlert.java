package tae.cosmetics.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Set;

import tae.cosmetics.ColorCode;
import tae.cosmetics.Globals;
import tae.cosmetics.webscrapers.API2b2tdev;
import tae.cosmetics.webscrapers.MojangGetter;
import tae.cosmetics.webscrapers.RebaneGetter;

public class PlayerAlert implements Globals{
			
	private static final String fileName = "playeralerts.txt";
	
	private static HashMap<String, PlayerData> playeruuidmap = new HashMap<>();
	
	static {
		FileHelper.createFile(fileName);
		load();
	}
		
	public static void save() {
		StringBuilder builder = new StringBuilder();
		for(String uuid : playeruuidmap.keySet()) {
			PlayerData data = playeruuidmap.get(uuid);
			
			StringBuilder prefixBuilder = new StringBuilder();
			
			for(int i : data.chatPrefix.toCharArray()) {
				prefixBuilder.append(Integer.toString(i)).append(',');
			}
			
			String chatPrefix = "";
			
			try {
				chatPrefix = Base64.getEncoder().encodeToString(prefixBuilder.toString().getBytes());
			} catch (Exception e) {
				
			}
			
		
			char keylength = (char) (32 + uuid.length());
			char namelength = (char) (32 + data.oldname.length());
			char prefixlength = (char) (32 + chatPrefix.length());
			builder.append(keylength).append(namelength).append(prefixlength).append(uuid).append(data.oldname).append(chatPrefix)
			.append(data.alert ? 1 : 0).append(data.queue ? 1 : 0).append(data.assumePrio ? 1 : 0).append("\n");
		}
		FileHelper.overwriteFile(fileName, builder.toString());
	}
	
	private static void load() {
		for(String s : FileHelper.readFile(fileName)) {
			int firstlength = s.charAt(0) - 32;
			int secondlength = s.charAt(1) - 32;
			int thirdlength = s.charAt(2) - 32;
			
			String first = s.substring(3,firstlength+3);
			String second = s.substring(firstlength+3,secondlength + firstlength + 3);
			String third = s.substring(secondlength + firstlength + 3, thirdlength + secondlength + firstlength + 3);
			
			String chatPrefix;
			
			StringBuilder prefixBuilder = new StringBuilder();
			
			try {
				
				String[] ints = new String(Base64.getDecoder().decode(third)).split(",");
				for(String i : ints) {
					prefixBuilder.append((char)Integer.parseInt(i));
				}
				chatPrefix = prefixBuilder.toString();
				
			} catch (Exception e) {
				chatPrefix = "";
			}
			
			String alertbool = s.substring(thirdlength + secondlength + firstlength + 3, thirdlength + secondlength + firstlength + 4);
			String queuebool = s.substring(thirdlength + secondlength + firstlength + 4, thirdlength + secondlength + firstlength + 5);
			String prio = s.substring(thirdlength + secondlength + firstlength + 5, thirdlength + secondlength + firstlength + 6);
			
			addFromConfig(first, second, chatPrefix, alertbool.equals("1") ? true : false, queuebool.equals("1") ? true : false, prio.equals("1") ? true : false);
			
		}
	}
	
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
	
	
	private static void addFromConfig(String uuid, String name, String prefix, boolean alert, boolean queue, boolean assumePrio) {
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
				
				data.epochJoined = RebaneGetter.getWhenJoined(uuid);
				
				if(data.inQueueOnBoot) {//Was in queue before player joined
					
					if(data.epochJoined == -1) {
						
						if(data.prioQueueOnJoin == -1) {
							data.prioQueueOnJoin = API2b2tdev.getPrioSize();
						}
						if(data.normQueueOnJoin == -1) {
							data.normQueueOnJoin = API2b2tdev.getNormSize();
						}
						if(data.rebaneQueueOnJoin == -1) {
							data.rebaneQueueOnJoin = RebaneGetter.getUUIDs().size();
						}
						
					} else {
						
						int prioQueue = API2b2tdev.prioQueueFromEpoch(data.epochJoined);
						if(data.prioQueueOnJoin == -1) {
							if(prioQueue == -1) {
								data.prioQueueOnJoin = API2b2tdev.getPrioSize();
							} else {
								data.prioQueueOnJoin = prioQueue;
							}
						}
						
						int normQueue = API2b2tdev.normQueueFromEpoch(data.epochJoined);
						if(data.normQueueOnJoin == -1) {
							if(normQueue == -1) {
								data.normQueueOnJoin = API2b2tdev.getNormSize();
							} else {
								data.normQueueOnJoin = normQueue;
							}
						}
						
						if(data.rebaneQueueOnJoin == -1) {
							if(normQueue == -1 || prioQueue == -1) {
								data.rebaneQueueOnJoin = RebaneGetter.getUUIDs().size();
							} else {
								data.rebaneQueueOnJoin = data.prioQueueOnJoin + data.normQueueOnJoin;
							}
						}
						
					}
					
				} else {
					if(data.prioQueueOnJoin == -1) {
						data.prioQueueOnJoin = API2b2tdev.getPrioSize();
					}
					if(data.normQueueOnJoin == -1) {
						data.normQueueOnJoin = API2b2tdev.getNormSize();
					}
					if(data.rebaneQueueOnJoin == -1) {
						data.rebaneQueueOnJoin = RebaneGetter.getUUIDs().size();
					}
				}
				
				data.prioOutOfBounds = false;
				
				if(data.assumePrio) {
					
					data.queuePos = (int) Math.ceil(((RebaneGetter.getUUIDs().indexOf(uuid) + 1 - bestFitIndex(data.prioQueueOnJoin, data.rebaneQueueOnJoin)) 
							* data.prioQueueOnJoin) / (float)(data.rebaneQueueOnJoin - bestFitIndex(data.prioQueueOnJoin, data.rebaneQueueOnJoin)));					
					
					if(RebaneGetter.getUUIDs().indexOf(uuid) + 1 - bestFitIndex(data.prioQueueOnJoin, data.rebaneQueueOnJoin) < 1) {
						data.prioOutOfBounds = true;
					}
					
				} else {
					data.queuePos = (int) Math.ceil(((RebaneGetter.getUUIDs().indexOf(uuid) + 1) * data.normQueueOnJoin) / (float)data.rebaneQueueOnJoin);
				}
				
				if(!data.sentInQueue && !data.inGame) {
					PlayerUtils.sendMessage(String.format(getMessage(uuid), "joined the queue. [" + data.queuePos + "]"), ColorCode.GOLD);
					data.sentInQueue = true;
				}
				
				if(data.inGame) { //Player is online
					data.queuePos = -1;
					data.prioQueueOnJoin = -1;
					data.normQueueOnJoin = -1;
					data.rebaneQueueOnJoin = -1;
					data.epochJoined = -1;
					data.prioOutOfBounds = false;

				}
				
				data.wasInQueue = true;
				data.sentLeftQueue = false;
				
			} else { //uuid isnt in rebane's site
				
				data.inQueueOnBoot = false;
				
				data.queuePos = -1;
				
				data.prioQueueOnJoin = -1;
				
				data.normQueueOnJoin = -1;
				
				data.rebaneQueueOnJoin = -1;
				
				data.epochJoined = -1;
				
				data.sentInQueue = false;
				
				data.prioOutOfBounds = false;
				
				if(!data.inGame && !data.sentLeftQueue && data.wasInQueue) {
					PlayerUtils.sendMessage(String.format(getMessage(uuid), "left the queue."), ColorCode.GOLD);
					data.sentLeftQueue = true;
				}
				
				data.wasInQueue = false;
				
			}
			
		}
	}
	
	private static double bestFitIndex(double prioQueueOnJoin, double rebaneQueueOnJoin) {
		return rebaneQueueOnJoin - 2.378 * prioQueueOnJoin;
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
	
	public static boolean prioOutOfBounds(String uuid) {
		
		if(!playeruuidmap.containsKey(uuid)) {
			return false;
		}
		
		return playeruuidmap.get(uuid).prioOutOfBounds;
		
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
		
		private boolean inQueueOnBoot;
		private boolean prioOutOfBounds;
		
		private int queuePos;
		private int prioQueueOnJoin;
		private int normQueueOnJoin;
		private int rebaneQueueOnJoin;
		private long epochJoined;
		
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
			prioQueueOnJoin = -1;
			normQueueOnJoin = -1;
			rebaneQueueOnJoin = -1;
			epochJoined = -1;
			inQueueOnBoot = true;
			this.alert = alert;
			this.queue = queue;
			this.assumePrio = assumePrio;
		}
		
		private boolean needNameUpdate() {
			if(newname == null || newname.equals("")) return false;
			return !oldname.equals(newname);
		}
		
		
	}

}