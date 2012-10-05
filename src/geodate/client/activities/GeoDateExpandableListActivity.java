package geodate.client.activities;

import geodate.client.proxy.GDServerProxy;
import geodate.client.proxy.GDServerProxyImpl;
import geodate.client.util.Logger;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.os.Handler;

public abstract class GeoDateExpandableListActivity extends ExpandableListActivity {
	protected GDServerProxy proxy = null;
	protected Handler handler = new Handler();
	
	public GeoDateExpandableListActivity() {
		proxy = new GDServerProxyImpl(this);		
	}
	
	protected abstract int getContentView();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());                       
    }
    
    protected void log(int priority, String msg) {
    	Logger.log(priority, this, msg);
    }
    
    protected void log(int priority, String msg, Throwable error) {
    	Logger.log(priority, this, msg, error);
    }
}
