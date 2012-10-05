package geodate.client.widget;

import geodate.client.data.Category;
import geodate.client.data.CategoryList;
import geodate.client.view.CategoryEditTextView;
import geodate.client.view.CategoryListView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CategoryListAdapter extends BaseAdapter {
	private Context context;
	private CategoryList categories;
	
	public CategoryListAdapter(Context context, CategoryList categories) {
		this.context = context;
		this.categories = categories;
	}
	
	@Override
	public int getCount() {
		return categories.size();		
	}

	@Override
	public Object getItem(int position) {
		return categories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		
		Category category = (Category)getItem(position);
		if (category.getControlType().equals("List")) {
			v = new CategoryListView(context, category.getDescription(), category.getAttributes()); 
		} else if (category.getControlType().equals("Free Form")) {
			v = new CategoryEditTextView(context, category.getDescription());
		}
		return v;
	}
}
