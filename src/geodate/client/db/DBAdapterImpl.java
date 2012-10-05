package geodate.client.db;

import geodate.client.util.Config;
import geodate.client.util.Logger;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public abstract class DBAdapterImpl implements DBAdapter {
	private static final String TAG = "DBAdapterImpl";
	
	private boolean refreshing = false;
	
	protected boolean enableLogging = true;
	protected SQLiteDatabase db = null;
	protected Context context = null;
	
	public DBAdapterImpl() {
		enableLogging = Boolean.parseBoolean(Config.getProperty(context, this.getClass(), "enableLogging"));
	}
		
	protected abstract String[] tables();
	protected abstract String[] creates();
	
	public void create(SQLiteDatabase db) {
		if (!refreshing) {			
			rebuild(db);
		}
	}


	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		
		dropAll();
	}
	
	public void rebuild() {		
		transact(new SQLCommand() {
			public void execute() {
				for (String create : creates()) {
					if (enableLogging) {
						Logger.log(Log.INFO, TAG, "Executing: " + create);
					}
					db.execSQL(create);
				}				
			}});
	}
	
	public void rebuild(final SQLiteDatabase db) {		
		for (String create : creates()) {
			if (enableLogging) {
				Logger.log(Log.INFO, TAG, "Executing: " + create);
			}
			db.execSQL(create);				
		}		
	}
	
	public void dropAll() {		
		transact(new SQLCommand() {
			public void execute() {
				for (String table : tables()) {
					String sql = "DROP TABLE IF EXISTS " + table;
					if (enableLogging) {
						Logger.log(Log.INFO, TAG, "Executing: " + sql);
					}
					db.execSQL(sql);
				}				
			}});				
	}
	
	public void refresh() {
		refreshing = true;
		dropAll();
		rebuild();
		refreshing = false;
	}
		
	private DBAdapter open() throws SQLException
	{		
		guard();
		if (db == null || !db.isOpen()) {		
			db = DBHelper.getInstance(context).getWritableDatabase();		
		}
		return this;
	}
	
	private void close() {
		guard();
		DBHelper.getInstance(context).close();
	}
	
	private void guard() {
		if (context == null) {
			throw new RuntimeException("Cannot connect to adapter without instantiating it first.");
		}		
	}
	
	public void transact(SQLCommand sql) {		
		open();
		sql.execute();
		close();		
	}	
	
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	protected void genericSave(final Savable object) {			
		transact(new SQLCommand() {		
			public void execute() {				
				db.insert(object.getTableName(), null, object.toContentValues());
			}});			
	}	
}
