package geodate.client.activities;

import geodate.client.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends GeoDateActivity implements OnClickListener {
	private enum LoginMenuOption {
		ForgotPassword(0, "Forgot my Password"), 
		Help(1, "Help!"), 
		Exit(2, "Exit GeoDate");

		private final int index;
		private final String text;

		LoginMenuOption(int index, String text) {
			this.index = index;
			this.text = text;
		}

		int index() {
			return index;
		}

		String text() {
			return text;
		}
	}
	
	private Button buttonRegister = null;
	private Button buttonLogin = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		
		buttonRegister = (Button)findViewById(R.id.buttonRegister);
		buttonLogin = (Button)findViewById(R.id.buttonLogin);
		
		buttonRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alert("The registration screen (or link to the website) is not yet coded.");
			}
		});
		
		buttonLogin.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		login();		
	}
	
	private void login() {
		Intent onLogin = new Intent();
		onLogin.setClass(this, MainActivity.class);
		startActivity(onLogin);
		finish();
	}

	@Override
	protected int getContentView() {
		return R.layout.mock_login;
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		for (LoginMenuOption option : LoginMenuOption.values()) {
			menu.add(0, option.index, 0, option.text);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean handled = true;
		if (item.getItemId() == LoginMenuOption.ForgotPassword.index) {
			alert("The forgotten password screen is not yet coded.");
		} else if (item.getItemId() == LoginMenuOption.Help.index) {
			Intent help = new Intent();
			help.setClass(this, HelpActivity.class);
			startActivity(help);
		} else if (item.getItemId() == LoginMenuOption.Exit.index) {
			finish();
		} else {
			handled = false;
		}
		return handled;
	}
}