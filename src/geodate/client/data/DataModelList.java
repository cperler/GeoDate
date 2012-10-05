package geodate.client.data;

import geodate.client.db.Savable;

import java.util.ArrayList;

import android.content.ContentValues;

public abstract class DataModelList<T> extends ArrayList<T> implements Savable {
	private static final long serialVersionUID = 1L;
	
	public abstract ContentValues toContentValues(int index);
	
	public String getTableName() {
		Table table = getClass().getAnnotation(Table.class);
		if (table != null) {
			return table.value();
		}
		return null;
	}	
	
	public ArrayList<ContentValues> toContentValuesList() {
		ArrayList<ContentValues> data = new ArrayList<ContentValues>();
		for (int i = 0; i < size(); i++) {			
			ContentValues args = toContentValues(i);
			data.add(args);
		}
		return data;
	}

	public ContentValues toContentValues() {
		return new ContentValues();
	}
}
