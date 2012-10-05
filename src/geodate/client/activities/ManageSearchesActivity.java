package geodate.client.activities;

import geodate.client.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ManageSearchesActivity extends GeoDateListActivity {
	@Override
	protected int getContentView() {
		return R.layout.mock_manage_searches;
	}	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		getListView().setEmptyView(findViewById(R.id.search_empty));		
				
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 
				new String[] {"For Love", "For Money"}));
		getListView().setTextFilterEnabled(true);
	}	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {	
		super.onListItemClick(l, v, position, id);		
		finish();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {    
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, 0, 0, "Delete a Search");
    	menu.add(0, 1, 0, "Delete all Searches");    	
    	return true;
    }
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		alert("This option does not yet have an activity, view or implementation.");
		return super.onContextItemSelected(item);
	}
}