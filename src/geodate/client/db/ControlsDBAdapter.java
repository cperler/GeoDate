package geodate.client.db;

import geodate.client.data.AttributeList;
import geodate.client.data.Category;
import geodate.client.data.CategoryList;
import geodate.client.util.Logger;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class ControlsDBAdapter extends DBAdapterImpl {
	private final String CREATE_CATEGORIES = 
		"CREATE TABLE category (" +
			"name TEXT PRIMARY KEY, " +			
			"control_type TEXT NOT NULL CHECK(control_type in ('List', 'Free Form')), " +
			"description TEXT " +			
		");";	
	
	private final String CREATE_ATTRIBUTES =
		"CREATE TABLE attribute (" +
			"category_name TEXT NOT NULL, " +				
			"value TEXT NOT NULL" +
		");";	
	
	public ControlsDBAdapter() {
		super();	
	}
	
	@Override
	protected String[] tables() {
		return new String[] {"category", "attribute"};
	}
	
	@Override
	protected String[] creates() {
		return new String[] {CREATE_CATEGORIES, CREATE_ATTRIBUTES};
	}	
	
	public CategoryList getAllCategories() {		
		Logger.log(Log.DEBUG, this, "Retrieving all categories from DB.");
		final CategoryList categories = new CategoryList();
		
		transact(new SQLCommand() {
			public void execute() {
				Cursor cursor = db.query("category", null, null, null, null, null, null);				
				if (cursor != null) {
					while (!cursor.isLast() && cursor.getCount() > 0) {				
						cursor.moveToNext();										
						
						Category category = new Category();					
						category.setName(cursor.getString(cursor.getColumnIndex("name")));
						category.setControlType(cursor.getString(cursor.getColumnIndex("control_type")));
						category.setDescription(cursor.getString(cursor.getColumnIndex("description")));
						category.setAttributes(getAttributes(category));
						categories.add(category);
					}
					cursor.close();
				}				
			}
		});
							
		return categories;
	}
	
	public AttributeList getAttributes(final Category category) {
		Logger.log(Log.DEBUG, this, "Retrieving attributes for " + category.getName() + " from DB.");
		final AttributeList attributes = new AttributeList(category);
		
		transact(new SQLCommand() {
			public void execute() {
				Cursor cursor = db.query("attribute", null, "category_name = '" + category.getName() + "'", null, null, null, null);
				if (cursor != null) {
					while (!cursor.isLast() && cursor.getCount() > 0) {
						cursor.moveToNext();				
						attributes.add(cursor.getString(cursor.getColumnIndex("value")));
					}
					cursor.close();
				}
			}
		});
				
		return attributes; 
	}
	
	public void save(CategoryList categories) {
		Logger.log(Log.DEBUG, this, "Saving all categories to DB.");
		for (int i = 0; i < categories.size(); i++) {
			Category category = (Category) categories.get(i);
			save(category);
		}
	}
		
	public void save(Category category) {
		Logger.log(Log.DEBUG, this, "Saving category " + category.getName() + " to DB.");
		genericSave(category);
		save(category.getAttributes());		
	}
	
	public void save(AttributeList attributes) {
		Logger.log(Log.DEBUG, this, "Saving attributes for " + attributes.getCategory().getName() + " to DB.");
		List<ContentValues> data = attributes.toContentValuesList();
		for (final ContentValues args : data) {
			transact(new SQLCommand() {		
				public void execute() {				
					db.insert("attribute", null, args);
				}});
		}
	}	
}