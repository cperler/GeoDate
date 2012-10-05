package geodate.client.data;

import android.content.ContentValues;

public class CategoryList extends DataModelList<Category> {
	private static final long serialVersionUID = 1L;
	
    @Override
    public synchronized String toString() {
    	String list = "[Categories {";
    	for (int i = 0; i < size(); i++) {
    		list += ((Category)get(i)).toString();
    		if (i < size() - 1) {
    			list += " ";
    		}
    	}
    	list += "}]";
    	return list;
    }
        
    public ContentValues toContentValues(int index) {
    	return ((Category)get(index)).toContentValues(); 
    }    
}