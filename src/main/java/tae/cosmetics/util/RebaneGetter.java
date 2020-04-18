package tae.cosmetics.util;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import tae.cosmetics.Globals;
import tae.cosmetics.exceptions.TAEModException;

public class RebaneGetter implements Globals{
	
	private static JSONObject json = null;
	
	private static ArrayList<String> UUIDArray = new ArrayList<String>();
	
	private static int size = -1;
	
	public static void init() {
		getBaseJSON();
		UUIDArray = getUUIDArray();
		size = getAmount();		
	}
	
	public static int getSize() {
		return size;
	}
	
	private static void getBaseJSON() {
		try {
			String rawjson = getDataFromURL(rebaneQueue);
			JSONParser parser = new JSONParser();
			json = (JSONObject) parser.parse(rawjson);
		} catch (Exception e) {
			new TAEModException(e.getClass(), e.getMessage()).post();			
			new TAEModException(RebaneGetter.class, "Cannot connect to rebane2001.com; queue positions may be inaccurate or unavaliable.").post();
		}
	}
	
	private static ArrayList<String> getUUIDArray() {
		if(json == null) return null;
		ArrayList<String> uuids = new ArrayList<String>();
		JSONArray namejson = (JSONArray) json.get("players");
		int jsonsize = namejson.size();
		for(int i = 0; i < jsonsize; i++) {
			String uuid = (String) ((JSONObject)namejson.get(i)).get("uuid");
			uuids.add(uuid.replace("-", ""));
		}
		return uuids;
	}
	
	private static int getAmount() {
		if(json == null) return -1;
		try {
			return Integer.parseInt((String) json.get("queuepos"));
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static ArrayList<String> getUUIDs() {
		if(UUIDArray == null) {
			return new ArrayList<String>();
		}
		return new ArrayList<String>(UUIDArray);
	}
	
	public static boolean hasJSON() {
		return json!=null;
	}
	
	public static long getWhenJoined(String uuid) {
		
		try {
			
			if(json != null && UUIDArray.contains(uuid)) {
				JSONArray playersjson = (JSONArray) json.get("players");
				
				for(int i = 0; i < playersjson.size(); i++) {
					JSONObject obj = (JSONObject) playersjson.get(i);
					
					if(uuid.equals( ((String)obj.get("uuid")).replace("-", "") ) ){
						return (Long)obj.get("updated");
					}
					
				}
			}
			
			return -1;
			
		} catch (Exception e) {
			new TAEModException(e.getClass(), e.getMessage()).post();			
			new TAEModException(RebaneGetter.class, "Cannot connect to rebane2001.com; queue positions may be inaccurate or unavaliable.").post();
			return -1;
		}
				
	}
	
	private static String getDataFromURL(String url) {
		try {
			return IOUtils.toString(new URL(url), Charset.defaultCharset());
		} catch (Exception e) {
			return null;
		}
	}
	
}
