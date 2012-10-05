package geodate.client.db;

import geodate.client.util.Logger;
import android.content.Context;
import android.util.Log;

public class DBAdapterFactory {
	private static final String TAG = "DBAdapterFactory";
	
	@SuppressWarnings( "unchecked" )
	public static <T extends DBAdapter> T getAdapter(Context context, Class<T> clazz) {
		Logger.log(Log.DEBUG, TAG, "Retrieving DB adapter for " + clazz.getSimpleName() + ".");
		DBAdapter adapter = DBHelper.getInstance(context).getAdapter(clazz);
		if (adapter == null) {
			throw new RuntimeException("Unable to find specified adapter.");
		}
		return (T) adapter;
	}
}