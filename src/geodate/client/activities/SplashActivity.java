package geodate.client.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {
	private final int SPLASH_DURATION = 3;
	
	private boolean isRegistered = false;
	private boolean isLoggedIn = false;
	private boolean isStarted = false;

	private ProgressDialog pd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isStarted = false;
		showSplash(this, SPLASH_DURATION);
		isRegistered = getIsRegistered();
		isLoggedIn = getIsLoggedIn();
	}

	private void showSplash(final Context parentActivity, final int seconds) {
		pd = ProgressDialog.show(parentActivity, "       GeoDate", "<< splash + witty catch phrase >>");

		new Thread() {
			public void run() {
				try {
					Thread.sleep(seconds * 1000);
					splashCompleteHandler.handleMessage(null);
				} catch (InterruptedException e) { }
			}
		}.start();
	}

	private Handler splashCompleteHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			startGeoDate();
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		if (isStarted) {
			startGeoDate();
		}
	}

	private void startGeoDate() {
		Intent onStart = new Intent();
		if (isRegistered && isLoggedIn) {
			onStart.setClass(this, StubActivity.class);
		} else if (isRegistered && !isLoggedIn) {
			onStart.setClass(this, LoginActivity.class);
		} else {
			onStart.setClass(this, MockupActivity.class);
		}
		isStarted = true;
		startActivity(onStart);
		finish();
	}

	// Check local preferences to see if the installed client has a registered user account.
	private boolean getIsRegistered() {
		return true;
	}

	// To be used for something like auto-login feature.
	private boolean getIsLoggedIn() {
		return false;
	}
}