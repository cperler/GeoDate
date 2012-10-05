package geodate.client.data;

import android.content.ContentValues;

public class PictureList extends DataModelList<Picture> {
	private static final long serialVersionUID = 1L;

	@Override
    public synchronized String toString() {
    	String list = "[Picture {";
    	for (int i = 0; i < size(); i++) {
    		list += ((Picture)get(i)).toString();
    		if (i < size() - 1) {
    			list += " ";
    		}
    	}
    	list += "}]";
    	return list;
    }
	
	public Picture findPictureByRank(int rank) {		
    	for (Picture picture : this) {
    		if (picture.getRank() == rank) {
    			return picture;
    		}
    	}
    	return null;
    }
	
	@Override
	public ContentValues toContentValues(int index) {
		return ((Picture)get(index)).toContentValues();
	}
}
