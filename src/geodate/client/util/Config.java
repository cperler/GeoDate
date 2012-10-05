package geodate.client.util;

import geodate.client.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

public class Config {
	private static final String TAG = "Config";
	private static Properties props = null;
	
	private Config() {}	
	
	private static Properties getInstance(Context context) {
		if (props == null) {
			props = new Properties();
														
			try {				
				Logger.log(Log.DEBUG, TAG, "Loading properties from local resources.");
				InputStream in = context.getResources().openRawResource(R.raw.config);
				props.load(in);
				in.close();
			} catch (IOException e) {
				Logger.log(Log.ERROR, TAG, "Unable to load properties.", e);
			}
		}
		return props;		
	}		
	
	public static String getProperty(Context context, Class<?> clazz, String key) {
		String value = getInstance(context).getProperty(clazz.getCanonicalName() + "." + key);
		if (value == null) {
			value = getInstance(context).getProperty(clazz.getPackage() + "." + key);
			if (value == null) {
				value = getInstance(context).getProperty(key);
			}
		}
		return value;
	}
	
	public static String[] getPropertyList(Context context, Class<?> clazz, String key) {
		String value = getProperty(context, clazz, key);
		String[] values = value.split(",");
		return values;
	}
}