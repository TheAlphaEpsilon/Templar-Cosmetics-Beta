package tae.cosmetics.webscrapers;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import tae.cosmetics.Globals;
import tae.cosmetics.exceptions.TAEModException;

public class API2b2tdev implements Globals {
	
	private static int errors = 0;
	
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
				errors++;
				 //new TAEModException(API2b2tdev.class, "Prio: " + priodata).post();
			}
		}
		
		String normdata = getDataFrom("https://2b2t.io/api/queue?last=true");
		
		if(normdata != null) {
			try{
				normsize = Integer.parseInt(normdata.replace("[", "").replace("]", "").split(",")[1]);
			} catch (Exception e) {
				errors++;
				// new TAEModException(API2b2tdev.class, "Norm: " + normdata).post();
			}
		}
		
	}
	
	public static int prioQueueFromEpoch(long epoch) {
		
		return (int)getQueueData("https://2b2t.io/api/prioqueue?range=24h", epoch);
		
	}
	
	public static int normQueueFromEpoch(long epoch) {
		
		return (int)getQueueData("https://2b2t.io/api/queue?range=24h", epoch);
		
	}
	
	private static long getQueueData(String source, long epoch) {
			
		JSONParser parser = new JSONParser();

		try {
			
			String data = getDataFrom(source);
						
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
			errors = 0;
			return (Long)((JSONArray)sizes.get(index)).get(1);
			
		} catch (ParseException e) {
			if(++errors > 5) {
				new TAEModException(e.getClass(), e.getMessage()).post();
				new TAEModException(API2b2tdev.class, "Cannot connect to 2b2t.io; queue data may be inaccurate or unavaliable.").post();
			} 
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
			if(++errors > 5) { 
				new TAEModException(e.getClass(), e.getMessage()).post();
			 	new TAEModException(API2b2tdev.class, "Cannot connect to 2b2t.dev; queue data may be inaccurate or unavaliable.").post();
			}
			return null;
		}
	    
	}
	
}
