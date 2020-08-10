package tae.cosmetics.webscrapers;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import tae.cosmetics.OnLogin;
import tae.cosmetics.exceptions.TAEModException;

public class TrustManagerSetup {

	//This section was ripped from nakov.com cuz i have low iq
	//https://nakov.com/blog/2009/07/16/disable-certificate-validation-in-java-ssl-connections/
	
	private static TrustManager[] allgood = new TrustManager[] {
		new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}	
			
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		}
	};
	
	public static void initTrustManager() {
		try {
			//install manager
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, allgood, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
			
			//host verify
			HostnameVerifier allHost = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			
			//install host verify
			HttpsURLConnection.setDefaultHostnameVerifier(allHost);
			
		} catch (Exception e) {
			OnLogin.addError(new TAEModException(TrustManagerSetup.class,"Unable to install trust manager."));;
		}
	}
	
}
