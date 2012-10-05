package geodate.client.data;

import android.content.ContentValues;

public class ProfileAttributeList extends DataModelList<ProfileAttribute> {
	private static final long serialVersionUID = 1L;
    
    @Override
    public synchronized String toString() {
    	String list = "[ProfileAttributes {";
    	for (int i = 0; i < size(); i++) {
    		list += ((ProfileAttribute)get(i)).toString();
    		if (i < size() - 1) {
    			list += " ";
    		}
    	}
    	list += "}]";
    	return list;
    }
    
    public ProfileAttribute findByAttributeName(String name) {
    	for (ProfileAttribute attribute : this) {
    		if (attribute.getName().toLowerCase().equals(name.toLowerCase())) {
    			return attribute;
    		}
    	}
    	return null;
    }
        
    public ContentValues toContentValues(int index) {
    	return ((ProfileAttribute)get(index)).toContentValues(); 
    }
}