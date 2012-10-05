package geodate.client.activities;

import geodate.client.proxy.GDServerProxy;
import geodate.client.proxy.GDServerProxyImpl;
import geodate.client.util.Logger;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.maps.MapActivity;

public class GeoDateMapActivity extends MapActivity {

	protected GDServerProxy proxy = null;
	protected Handler handler = new Handler();

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public GeoDateMapActivity() {
		proxy = new GDServerProxyImpl(this);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	
	protected void longAlert(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}