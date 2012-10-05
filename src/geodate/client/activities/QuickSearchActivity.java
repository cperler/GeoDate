package geodate.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import geodate.client.R;

public class QuickSearchActivity extends GeoDateActivity {
	private Menu contextMenu = null;
	private Spinner minAgeSpinner = null;
	private Spinner maxAgeSpinner = null;
	
	@Override
	protected int getContentView() {
		return R.layout.mock_quick_search;
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {    
    	super.onCreateOptionsMenu(menu);    	
    	menu.add(0, 0, 0, "Add More Detail");
    	menu.add(0, 1, 0, "Do It!");
    	menu.add(0, 2, 0, "Load a Search");
    	menu.add(0, 3, 0, "Save this Search");
    	contextMenu = menu;
    	return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {				
		if (item.getItemId() == 0) {
			setContentView(R.layout.mock_custom_search);
			contextMenu.removeItem(0);
			contextMenu.add(0, 4, 0, "Just the Basics");
		} else if (item.getItemId() == 1) {
			MainActivity main = (MainActivity)getParent();
			main.getTabHost().setCurrentTab(MainActivity.MainActivityTab.Where.index());			
		} else if (item.getItemId() == 2) {
			Intent nextActivity = new Intent();
			nextActivity.setClass(this, ManageSearchesActivity.class);
			startActivity(nextActivity);
		}
		else if (item.getItemId() == 3) {
			alert("Save the current search here...");
		}
		else if (item.getItemId() == 4) {
			setContentView(R.layout.mock_quick_search);
			contextMenu.removeItem(4);			
			contextMenu.add(0, 0, 0, "Add More Detail");
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		initializeAgeSpinners();
	}
	
	private void initializeAgeSpinners() {
    	minAgeSpinner = initializeSpinner(minAgeSpinner, R.id.their_min_age);
    	maxAgeSpinner = initializeSpinner(maxAgeSpinner, R.id.their_max_age);    	
    }
	
	private Spinner initializeSpinner(Spinner spinner, int id) {
    	if (spinner == null) {
    		spinner = (Spinner)findViewById(id);
    		
    		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,
    				getValidAges());
    		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		spinner.setAdapter(adapter);    		
    	}
    	return spinner;
    }
	
	private Integer[] getValidAges() {
		Integer[] ages = new Integer[103];
		for (int i = 18; i < ages.length; i++) {
			ages[i-18] = i;
		}
		return ages;
	}
}
