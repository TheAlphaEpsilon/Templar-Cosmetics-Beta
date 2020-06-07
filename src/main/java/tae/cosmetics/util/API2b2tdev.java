package tae.cosmetics.util;

import java.time.Instant;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import tae.cosmetics.Globals;
import tae.cosmetics.exceptions.TAEModException;

public class API2b2tdev implements Globals {
	
	private static int priosize = -1;
	
	private static int normsize = -1;
	
	public static int getPrioSize() {
		return priosize;
	}
	
	public static int getNormSize() {
		return normsize;
	}
	
	public static void update() {
				
		//Cuz server doesn't wanna place nice with normal https streams
		//I have no idea what I'm doing
		
		
		String priodata = getDataFrom(prioqueue);	
		
		if(priodata != null) {
			try{
				priosize = Integer.parseInt(priodata.split(",")[1]);
			} catch (Exception e) {
				 new TAEModException(API2b2tdev.class, "Prio: " + priodata).post();
			}
		}
		
		String normdata = getDataFrom("https://2b2t.io/api/queue?last=true");
		
		if(normdata != null) {
			try{
				normsize = Integer.parseInt(normdata.replace("[", "").replace("]", "").split(",")[1]);
			} catch (Exception e) {
				 new TAEModException(API2b2tdev.class, "Norm: " + normdata).post();
			}
		}
		
	}
	
	public static int prioQueueFromEpoch(long epoch) {
		
		return (int)getQueueData("https://2b2t.io/api/prioqueue?range=", epoch);
		
	}
	
	public static int normQueueFromEpoch(long epoch) {
		
		return (int)getQueueData("https://2b2t.io/api/queue?range=", epoch);
		
	}
	
	private static long getQueueData(String source, long epoch) {
		
		long diff = Instant.now().getEpochSecond() - epoch;
		
		long rawhours = diff / 3600;
		
		int decimal = (int) (rawhours - (int) rawhours);
		
		if(decimal < 33) {
			return dataFromHour(getDataFrom(source + Math.floor(rawhours) +"h"), epoch);
		} else if(decimal >= 33 && decimal <= 66) {
			return (dataFromHour(getDataFrom(source + Math.floor(rawhours) +"h"), epoch) + dataFromHour(getDataFrom(source + Math.floor(rawhours) +"h"), epoch)) / 2;
		} else {
			return dataFromHour(getDataFrom(source + Math.ceil(rawhours) +"h"), epoch);
		}
				
		
	}
	
	private static long dataFromHour(String data, long rawEpoch) {
		JSONParser parser = new JSONParser();

		try {
			JSONArray sizes = (JSONArray) parser.parse(data);
			
			long closest = -1;
			int index = -1;
			
			for(int i = 0; i < sizes.size(); i++) {
				
				JSONArray sub = (JSONArray) sizes.get(i);
				
				if(closest == -1) {
					closest = (Long)sub.get(0);
					index = i;
				} else {
					
					long newLong = (Long)sub.get(0);
					
					if(Math.abs(rawEpoch - closest) > Math.abs(rawEpoch - newLong)) {
						closest = newLong;
						index = i;
					}
					
				}
				
			}
			
			return (Long)((JSONArray)sizes.get(index)).get(1);
			
		} catch (Exception e) {
		    new TAEModException(e.getClass(), e.getMessage()).post();
		    new TAEModException(API2b2tdev.class, "Cannot connect to 2b2t.io; queue data may be inaccurate or unavaliable.").post();
			return - 1;
		}
	}
	
	private static String getDataFrom(String url) {
		
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
			sslcontext.init(null, null, null);
		    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslcontext,
		        SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER); // Socket
		    HttpClient client =
		        HttpClients.custom().setSSLSocketFactory(socketFactory).build();
		    HttpGet httpget = new HttpGet(url);
		    HttpResponse response = client.execute(httpget);
		    
		    return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			 new TAEModException(e.getClass(), e.getMessage()).post();
			 new TAEModException(API2b2tdev.class, "Cannot connect to 2b2t.dev; queue data may be inaccurate or unavaliable.").post();
			 return null;
		}
	    
	}
	
}
