package geodate.client.activities;

import geodate.client.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends GeoDateListActivity {
	public static int SELECTED_SETTING = -1;
	
	@Override
	protected int getContentView() {
		return R.layout.mock_settings;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		getListView().setEmptyView(findViewById(R.id.settings_empty));		
				
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 
				new String[] {"Location Preferences", "Location Triggers",
					"Time Triggers", "Help"}));
		getListView().setTextFilterEnabled(true);
	}	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {	
		super.onListItemClick(l, v, position, id);
	
		if (position < 3) {
			if (position == 0) {
				SELECTED_SETTING = R.layout.mock_location_prefs;
			} else if (position == 1) {
				SELECTED_SETTING = R.layout.mock_location_triggers;
			} else if (position == 2) {
				SELECTED_SETTING = R.layout.mock_time_triggers;
			}
			Intent setting = new Intent();
			setting.setClass(this, GenericSettingsActivity.class);
			startActivity(setting);
		} else if (position == 3) {
			Intent help = new Intent();
			help.setClass(this, HelpActivity.class);
			startActivity(help);
		}
	}
}