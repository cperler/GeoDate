package geodate.client.util;

import geodate.client.R;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	public enum Key {
		VERSION
	}
	
	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(context.getString(R.string.app_name), 0);
	}
	
	public static void setVersion(Context context, int version) {
		SharedPreferences prefs = getSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(Key.VERSION.name(), version);
		editor.commit();
	}
	
	public static int getVersion(Context context, int defaultVersion) {
		SharedPreferences prefs = getSharedPreferences(context);
		return prefs.getInt(Key.VERSION.name(), defaultVersion);
	}
}
