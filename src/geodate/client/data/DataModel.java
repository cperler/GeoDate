package geodate.client.data;

import geodate.client.db.Savable;
import geodate.client.util.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.ContentValues;
import android.util.Log;

public abstract class DataModel implements Savable {
	private ArrayList<Field> idxToField = null;
	private ArrayList<MetaProperty> idxToMeta = null;
	
	public DataModel() {
		initialize(getClass());
		this.idxToField = PropertyHelper.idxToField(getClass());
		this.idxToMeta = PropertyHelper.idxToMeta(getClass());
	}
	
	protected static void initialize(Class<?> clazz) {
		if (!PropertyHelper.initialized(clazz)) {
			ArrayList<Field> idxToField = new ArrayList<Field>();
			ArrayList<MetaProperty> idxToMeta = new ArrayList<MetaProperty>();
			
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				MetaProperty meta = field.getAnnotation(MetaProperty.class);
				if (meta != null) {
					idxToField.add(field);
					idxToMeta.add(meta);
				}
			}
			
			PropertyHelper.register(clazz, idxToField, idxToMeta);
		}
	}
			
	public int getPropertyCount() {	
		return idxToField.size();
	}
	
	public void setProperty(int index, Object v) {		
		if (idxToField.size() > index) {
			try {
				Class<?> type = idxToField.get(index).getType();				
				idxToField.get(index).set(this, type.cast(v));								
			} catch (Exception e) { 
				Logger.log(Log.ERROR, this, "Unable to set property " + index + " to " + v.toString() + " on " + this.toString() + ".");
			}
		}
	}
		
	public Object getProperty(int index) {		
		if (idxToField.size() > index) {
			try {
				return idxToField.get(index).get(this);
			} catch (Exception e) {
				Logger.log(Log.ERROR, this, "Unable to get property " + index + " on " + this.toString() + ".");
				return null;
			}
		}
		
		return null;
	}	
	
	public String getTableName() {
		Table table = getClass().getAnnotation(Table.class);
		if (table != null) {
			return table.value();
		}
		return null;
	}
	  
	public ContentValues toContentValues() {
		ContentValues args = new ContentValues();
		for (int i = 0; i < getPropertyCount(); i++) {
			try {				
				Object property = idxToField.get(i).get(this);
				MetaProperty meta = idxToMeta.get(i);
				if (meta.dbName() != null && meta.dbName() != "" && property != null) {
					args.put(meta.dbName(), property.toString());
				}
			}
			catch (Exception e) { 
				Logger.log(Log.ERROR, this, "Unable to toContentValues() property " + i + " on " + this.toString() + ".");
			}
		}
		return args;
	}
		
	public String toString() {
		String str = "[" + getClass().getSimpleName() + " ";
		for (int i = 0; i < getPropertyCount(); i++) {
			try {
				Object property = idxToField.get(i).get(this);
				MetaProperty meta = idxToMeta.get(i); 
				if (meta.wsName() != null && meta.wsName() != "" && property != null) {
					str += "{" + idxToField.get(i).getName() + "=" + 
						idxToField.get(i).get(this).toString() + "}";
				}
				if (i < getPropertyCount() - 1) {
					str += " ";
				}
			} 
			catch (Exception e) { 
				Logger.log(Log.ERROR, this, "Unable to toString() property " + i + " on " + this.toString() + ".");
			}
		}
		str += "]";
		return str;
	}
}