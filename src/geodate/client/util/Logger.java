package geodate.client.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class Logger {		
	private static List<Object[]> archivedMsgs = null;
	private static Loggable defaultLoggable = null;	
	private static Map<String, Long> timers = null;
	
	static {
		timers = new HashMap<String, Long>();
		archivedMsgs = new ArrayList<Object[]>();
	}
	
	public static void log(int priority, Object source, Loggable loggable, String msg) {		
		String sourceName = "";
		if (source == null) {
			sourceName = "Logger";
		}
		else if (source.getClass() == String.class) {
			sourceName = source.toString();
		}
		else {
			sourceName = source.getClass().getSimpleName();
		}			
		
		Log.println(priority, sourceName, msg);
				
		String formattedMsg = "";
		switch (priority) {
		case Log.ERROR:
			formattedMsg += "[ERROR] ";
			break;
		case Log.INFO:
			formattedMsg += "[INFO] ";
			break;
		case Log.DEBUG:
			formattedMsg += "[DEBUG] ";
			break;
		case Log.WARN:
			formattedMsg += "[WARN] ";
			break;
		}
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss:SSS");
		formattedMsg += formatter.format(new Date()) + " [" + sourceName + "] " + msg.trim();		
		
		if (loggable != null) {
			loggable.printLog(priority, formattedMsg);
		}
		
		archivedMsgs.add(new Object[] {priority, formattedMsg});		
	}
	
	public static void log(int priority, Object source, Loggable loggable, String msg, Throwable error) {		
		log(priority, source, loggable, msg + "\n" + error.getMessage());
		error.printStackTrace();
	}
	
	public static void log(int priority, Object source, String msg) {
		log(priority, source, defaultLoggable, msg);		
	}
	
	public static void log(int priority, Object source, String msg, Throwable error) {
		log(priority, msg + "\n" + error.getMessage());
		error.printStackTrace();
	}
	
	public static void setDefaultLoggable(Loggable loggable) {
		if (defaultLoggable != loggable) {
			defaultLoggable = loggable;
			for (int i = 0; i < archivedMsgs.size(); i++) {
				loggable.printLog(((Integer)archivedMsgs.get(i)[0]).intValue(), (String)archivedMsgs.get(i)[1]);
			}
		}
	}
	
	private static void log(int priority, String msg) {
		log(priority, null, msg);
	}
	
	public static void addTimer(String key) {
		if (timers.containsKey(key)) {
			timers.remove(key);
		}
			
		log(Log.DEBUG, "Adding timer: " + key);
		timers.put(key, System.currentTimeMillis());		
	}
	
	public static void stopTimer(String key) {
		if (timers.containsKey(key)) {						
			long start = timers.get(key);
			long end = System.currentTimeMillis();
			long diff = end - start;		
			log(Log.DEBUG, "Completed timer: " + key + " = " + diff + "ms");
			timers.remove(key);
		}
	}
}