package geodate.client.data;


import geodate.client.util.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class PropertyHelper {
	private static final String TAG = "PropertyHelper";
	private static List<Class<?>> Initialized = null;
	private static Map<Class<?>, ArrayList<Field>> IdxToFields = null;
	private static Map<Class<?>, ArrayList<MetaProperty>> IdxToMetas = null;
	
	static { 
		Initialized = new ArrayList<Class<?>>(); 
		IdxToFields = new HashMap<Class<?>, ArrayList<Field>>();
		IdxToMetas = new HashMap<Class<?>, ArrayList<MetaProperty>>();
	}
	
	public static void register(Class<?> clazz, ArrayList<Field> idxToField, ArrayList<MetaProperty> idxToMeta) {
		if (!Initialized.contains(clazz)) {
			Logger.log(Log.DEBUG, TAG, "Registering metadata for: " + clazz.getSimpleName());
			IdxToFields.put(clazz, idxToField);
			IdxToMetas.put(clazz, idxToMeta);
			Initialized.add(clazz);
		}
	}
	
	public static boolean initialized(Class<?> clazz) { 
		return Initialized.contains(clazz);
	}
	
	public static ArrayList<Field> idxToField(Class<?> clazz) {
		return IdxToFields.get(clazz);
	}
	
	public static ArrayList<MetaProperty> idxToMeta(Class<?> clazz) {
		return IdxToMetas.get(clazz);
	}
}
