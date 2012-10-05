package geodate.client.proxy;

import geodate.client.util.Config;
import geodate.client.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.util.Log;

public class GDWebService {
	public static InputStream getURIContents(Context context, String uri) {
		boolean useLocalUri = Boolean.parseBoolean(Config.getProperty(context, GDServerProxyImpl.class, "useLocalUri"));
		if (useLocalUri) {
			return null;
		}
		String rootUri = Config.getProperty(context, GDWebService.class, "remoteUri");
		
		URL url = null;
		InputStream content = null;
		try {
			url = new URL(rootUri + uri);
		} catch (MalformedURLException e) {
			Logger.log(Log.ERROR, "GDWebService", "Malformed URL Exception.");
			return null;
		}	
		
		if (url != null) {
			try {				
				content = (InputStream)url.getContent();
			} catch (IOException e) {
				Logger.log(Log.ERROR, "GDWebService", "IO Exception");				
				return null;
			}
						
			return content;			
		}
		return null;
	}
}
