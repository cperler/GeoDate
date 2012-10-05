package geodate.client.proxy;

import geodate.client.data.CategoryList;
import geodate.client.data.Profile;
import geodate.client.sax.SaxHandler;
import geodate.client.sax.SaxParser;
import geodate.client.util.Config;
import geodate.client.util.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

public class GDServerProxyImpl implements GDServerProxy {
	private static HashMap<Class<?>, SaxHandler> saxHandlers;
	private static String rootUri = "";
		
	public GDServerProxyImpl(Context context) {
		if (saxHandlers == null) {
			try {
				initialize(context);
			} catch (Exception e) {
				Logger.log(Log.ERROR, this, "Unable to instantiate sax handlers.");				
			}
			Logger.log(Log.DEBUG, this, "Root URI set to " + rootUri);
		}
	}
	
	private static void initialize(Context context) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		String[] handlerDetails = Config.getPropertyList(context, GDServerProxyImpl.class, "handlers");
		saxHandlers = new HashMap<Class<?>, SaxHandler>();	
		for (int i = 0; i < handlerDetails.length; i+=2) {
			saxHandlers.put(Class.forName(handlerDetails[i]), (SaxHandler)Class.forName(handlerDetails[i+1]).newInstance());
		}
		
		boolean useLocalUri = Boolean.parseBoolean(Config.getProperty(context, GDServerProxyImpl.class, "useLocalUri"));
		if (useLocalUri) {
			rootUri = Config.getProperty(context, GDServerProxyImpl.class, "localUri");
		}
		else {
			rootUri = Config.getProperty(context, GDServerProxyImpl.class, "remoteUri");
		}
	}
	
	public int getVersion() throws IOException {
		Object response = invoke();
		if (response != null) {
			return Integer.parseInt((String)response);
		}
		return 0;
	}
	
	public CategoryList getControls() throws IOException {		
		Object response = invoke();
		if (response != null) {
			return (CategoryList)response;
		}
		return null;
	}
	
	public Profile getProfileByUserId(int userId) throws IOException {		
		Object response = invoke(userId);		
		if (response != null) {
			return (Profile) response;
		}
		return null;		
	}
	
	public Profile getProfileByUserName(String userName) throws IOException {
		Object response = invoke(userName);
		if (response != null) {
			return (Profile) response;
		}
		return null;
	}

	private Object invoke(Object... args) throws IOException {
		Path proxyCall = getPath();
		String method = proxyCall.methodName();
		ArrayList<PathParam> params = getPathParams();
		
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				method = method.replace("{" + params.get(i).value() + "}", args[i].toString());
			}
		}
		
		Logger.addTimer("Proxy call: " + proxyCall.methodName());			
		Object response = null;
		URL url = new URL(rootUri + method);		
		if (proxyCall.hasSimpleReturnType()) {
			String output = "";

		    output = readFromURL(url, output);
		    response = output;
		}
		else {
			SaxParser parser = new SaxParser(url.toString());
			SaxHandler handler = saxHandlers.get(getReturnType());
			parser.parse(handler);
			response = handler.getData();
		}
		Logger.stopTimer("Proxy call: " + proxyCall.methodName());
		return response;
	}

	private String readFromURL(URL url, String output) throws IOException {
		InputStream in = new BufferedInputStream(url.openStream());
		Reader reader = new InputStreamReader(in);		    
		
		int c;
		while ((c = reader.read()) != -1) {
			output += (char) c;
		}
		return output;
	}
		
	private Path getPath() {		
		return getAnnotations(Path.class);
	}
	
	private ArrayList<PathParam> getPathParams() {
		Annotation[][] params = getParameterAnnotations();
		ArrayList<PathParam> pathParams = new ArrayList<PathParam>();
		for (Annotation[] paramList : params) {
			for (int i = 0; i < paramList.length; i++) {
				if (paramList[i].annotationType() == PathParam.class) {
					pathParams.add((PathParam)paramList[i]);
					break;
				}
			}
		}
		
		return pathParams;
	}
	
	private Method getCallingMethod() {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		for (StackTraceElement frame : stack) {							
			if (frame.getClassName().equals(getClass().getCanonicalName())) {				
				Method[] methods = GDServerProxy.class.getMethods();
				for (Method method : methods) {
					if (method.getName().equals(frame.getMethodName())) {
						return method;
					}
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getAnnotations(Class type) {
		Method method = getCallingMethod();
		if (method != null) {	
			T call = (T)method.getAnnotation(type);
			if (call != null) {
				return call;
			}					
		}
		
		return null;
	}
	
	private Annotation[][] getParameterAnnotations() {
		Method method = getCallingMethod();
		if (method != null) {
			Annotation[][] call = method.getParameterAnnotations();
			if (call != null) {
				return call;
			}			
		}
		
		return null;
	}
	
	private Class<?> getReturnType() {
		Method method = getCallingMethod();
		if (method != null) {
			return method.getReturnType();
		}
		
		return null;
	}
}