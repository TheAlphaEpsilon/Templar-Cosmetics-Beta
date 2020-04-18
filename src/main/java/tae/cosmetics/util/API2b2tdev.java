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
	
	public static int getPrioSize() {
		return priosize;
	}
	
	public static void update() {
				
		//Cuz server doesn't wanna place nice with normal https streams
		//I have no idea what I'm doing
		
		
		String data = getDataFrom(prioqueue);	
		
		if(data != null) {
			priosize = Integer.parseInt(data.split(",")[1]);
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
		
		if(diff < 120) {
			return RebaneGetter.getSize();
		}
		
		int hours = (int) (diff / 3600);
		
		hours = hours < 0 ? 1 : hours;
		
		String data = getDataFrom(source + hours +"h");
		
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
					
					if(Math.abs(epoch - closest) > Math.abs(epoch - newLong)) {
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
