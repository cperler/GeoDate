package geodate.client.data;

import android.content.ContentValues;

public class AttributeList extends DataModelList<String> {
	private static final long serialVersionUID = 1L;
	
	private Category category = null;
	
	public AttributeList() {
		super();
	}
	
	public AttributeList(Category category) {
		super();
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

    @Override
    public synchronized String toString() {
    	String list = "[Attributes {";
    	for (int i = 0; i < size(); i++) {
    		list += get(i).toString();
    		if (i < size() - 1) {
    			list += " ";
    		}
    	}
    	list += "}]";
    	return list;
    }
        
    public ContentValues toContentValues(int index) {    	
    	ContentValues args = new ContentValues();
		args.put("category_name", category.getName());
		args.put("value", get(index).toString());
		return args;
    }
}