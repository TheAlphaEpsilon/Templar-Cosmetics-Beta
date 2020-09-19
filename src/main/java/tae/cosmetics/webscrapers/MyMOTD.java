package tae.cosmetics.webscrapers;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import tae.cosmetics.exceptions.TAEModException;

public class MyMOTD {

	private static int errors = 0;
	
	public static String getMOTD() {
		return getDataFrom("https://pastebin.com/raw/ydw60Pqn");
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
			 	new TAEModException(API2b2tdev.class, "Cannot get MOTD information.").post();
			}
			return null;
		}
	    
	}

}
