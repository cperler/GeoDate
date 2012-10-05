package geodate.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public interface DBAdapter {
	void create(SQLiteDatabase db);
	void upgrade(SQLiteDatabase db, int oldVersion, int newVersion);
	void setContext(Context context);
	void refresh();
	void rebuild();
	void rebuild(SQLiteDatabase db);
	void dropAll();
}
