package geodate.client.activities;

import geodate.client.R;
import geodate.client.data.CategoryList;
import geodate.client.widget.CategoryListAdapter;

import java.io.IOException;

import android.os.Bundle;
import android.util.Log;

public class CategoryTestActivity extends GeoDateListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setEmptyView(findViewById(R.id.search_empty));
		
		CategoryList categories = null;
		try {
			categories = proxy.getControls();
		} catch (IOException e) {
			log(Log.ERROR, "Unable to get controls.");			
		}
		
		if (categories != null) {
			setListAdapter(new CategoryListAdapter(this, categories));			
		}						
	}
	
	@Override
	protected int getContentView() {
		return R.layout.mock_manage_searches;
	}
}
