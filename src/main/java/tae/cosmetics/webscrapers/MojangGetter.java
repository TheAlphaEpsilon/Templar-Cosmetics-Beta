package tae.cosmetics.webscrapers;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import tae.cosmetics.Globals;

public class MojangGetter implements Globals {
	
	public static String[] getInfoFromName(String name) {
		try {
			String[] toReturn = new String[2];
			JSONParser jsonparse = new JSONParser();
			JSONObject json = (JSONObject) jsonparse.parse(IOUtils.toString(new URL(dataFromName+name), Charset.defaultCharset()));
			toReturn[0] = (String) json.get("id");
			toReturn[1] = (String) json.get("name");
			return toReturn;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public static String getCurrentNameFromUUID(String uuid) {
		try {
			String url = String.format(dataFromUUID,uuid);
			JSONParser jsonparse = new JSONParser();
			JSONArray json = (JSONArray) jsonparse.parse(IOUtils.toString(new URL(url), Charset.defaultCharset()));
			int last = json.size()-1;
			String name = (String) ((JSONObject)json.get(last)).get("name");
			return name;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String[] getTexturesFromUUID(String uuid) {
		try {
			String url = textureFromUUID + uuid;			
			
			
			JSONParser jsonparse = new JSONParser();
			

			
			JSONObject data = (JSONObject) jsonparse.parse(IOUtils.toString(new URL(url), Charset.defaultCharset()));
			

			JSONArray properties = (JSONArray) data.get("properties");
			

			String value64 = (String) ((JSONObject)(properties.get(0))).get("value");
			

			byte[] decodedbytes = Base64.getDecoder().decode(value64);
			

			JSONObject fromBytes = (JSONObject) jsonparse.parse(new String(decodedbytes));
			

			String[] toReturn = new String[3];
			
			JSONObject textures = (JSONObject) fromBytes.get("textures");
			

			JSONObject skin = (JSONObject) textures.get("SKIN");
			
			if(skin.containsKey("metadata")) {
				toReturn[1] = "slim";
			} else {
				toReturn[1] = "default";
			}

			toReturn[0] = (String) skin.get("url");
			
			try{
				JSONObject cape = (JSONObject) textures.get("CAPE");
				toReturn[2] = (String) cape.get("url");
			} catch (Exception e) {
				
			}
			
			
			return toReturn;
		} catch (Exception e) {
						
			return null;
		}
	}
	
}
