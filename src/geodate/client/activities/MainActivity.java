package geodate.client.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
	public enum MainActivityTab {
		Me (0, "Me", FullProfileActivity.class),
		Them (1, "Them", QuickSearchActivity.class),
		Where (2, "Where", MapSrchResultActivity.class),
		Prefs (3, "Prefs", SettingsActivity.class),
		Tests(4, "Tests", StubActivity.class);
		
		private final int index;
		private final String text;
		private Class<?> clazz;

		MainActivityTab(int index, String text, Class<?> clazz) {
			this.index = index;
			this.text = text;
			this.clazz = clazz;
		}

		int index() {
			return index;
		}

		String text() {
			return text;
		}
		
		Class<?> clazz() {
			return clazz;
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);

         final TabHost tabHost = getTabHost();         
         
         for (MainActivityTab activityTab : MainActivityTab.values()) {
        	 tabHost.addTab(tabHost.newTabSpec(activityTab.text).
        			 setIndicator(activityTab.text).
        			 setContent(new Intent(this, activityTab.clazz)));
        	 tabHost.getTabWidget().getChildAt(tabHost.getTabWidget().getChildCount() - 1).getLayoutParams().height = 30;
         }         
    }
}