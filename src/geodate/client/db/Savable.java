package geodate.client.db;

import android.content.ContentValues;

public interface Savable {	
	public String getTableName();		
	public ContentValues toContentValues();
}