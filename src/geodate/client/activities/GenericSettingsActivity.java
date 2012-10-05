package geodate.client.activities;

import android.view.Menu;
import android.view.MenuItem;

public class GenericSettingsActivity extends GeoDateActivity {	
	@Override
	protected int getContentView() {
		return SettingsActivity.SELECTED_SETTING;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Ok");
		menu.add(0, 1, 0, "Cancel");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return super.onOptionsItemSelected(item);
	}
}