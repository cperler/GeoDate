package geodate.client.db;

import geodate.client.R;
import geodate.client.util.Config;
import geodate.client.util.Logger;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {	
	private static final int INITIAL_VERSION = 1;

	private static DBHelper instance = null;
	
	private Context context = null;
	private List<DBAdapter> dbAdapters = null;
	
	private DBHelper(Context context) {				
		super(context, context.getString(R.string.db_name), null, 
				context.getSharedPreferences(context.getString(R.string.prefs_name), 0).getInt(context.getString(R.string.prefs_key_version), INITIAL_VERSION));
		
		this.context = context;
		dbAdapters = new ArrayList<DBAdapter>();
		
		initializeAdapters();
	}
	
	private void initializeAdapters() {
		String[] adapters = Config.getPropertyList(context, this.getClass(), "adapters");
		for (String adapter : adapters) {			
			try {
				Logger.log(Log.DEBUG, this, "Initializing DB adapter for '" + adapter + "'.");
				DBAdapter dbAdapter = (DBAdapter)Class.forName(adapter).newInstance();
				dbAdapter.setContext(context);
				dbAdapters.add(dbAdapter);
			} catch (Exception e) {
				Log.e(DBHelper.class.getName(), "Unable to instantiate db adapter: " + adapter, e);
			}			
		}
	}
	
	public DBAdapter getAdapter(Class<?> clazz) {
		for (DBAdapter adapter : dbAdapters) {
			if (clazz.isInstance(adapter)) {
				return adapter;
			}
		}
		return null;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	};
	
	public static DBHelper getInstance(Context context)
	{
		if (instance == null || (instance != null && !instance.getContext().equals(context))) {
			instance = new DBHelper(context);		
		}
		return instance;
	}
	
	public Context getContext() {
		return context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		for (DBAdapter adapter : dbAdapters) {
			adapter.create(db);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			for (DBAdapter adapter : dbAdapters) {
				adapter.upgrade(db, oldVersion, newVersion);
			}
		}
		onCreate(db);
	}
}