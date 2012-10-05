package geodate.client.sax;

import geodate.client.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

public class SaxParser {
	private final URL url;
	
	public SaxParser(String feedUrl){
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	protected InputStream getInputStream() {
		try {			
			URLConnection connection = url.openConnection();			
			connection.setConnectTimeout(1000);			
			return connection.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}		
	
	public void parse(DefaultHandler handler) {		
		try {
			Logger.addTimer("SAX Parsing");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.newSAXParser().parse(getInputStream(), handler);
			Logger.stopTimer("SAX Parsing");
        } catch (Exception e) {
        	if (e.getMessage() != null && 
        			e.getMessage().contains("At line 1, column 0: no element found")) {
        		throw new RuntimeException("No content available at specified url.");        		
        	} else {
        		throw new RuntimeException(e);
        	}
        }        
	}
}