package geodate.client.activities;

import geodate.client.proxy.GDServerProxy;
import geodate.client.proxy.GDServerProxyImpl;
import geodate.client.util.Logger;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public abstract class GeoDateActivity extends Activity {
	protected GDServerProxy proxy = null;
	protected Handler handler = new Handler(); 
	
	protected abstract int getContentView(); 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proxy = new GDServerProxyImpl(this);
        setContentView(getContentView());                       
    }
    
    protected void log(int priority, String msg) {
    	Logger.log(priority, this, msg);
    }
    
    protected void log(int priority, String msg, Throwable error) {
    	Logger.log(priority, this, msg, error);
    }
    
    protected void alert(String message) {
    	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}