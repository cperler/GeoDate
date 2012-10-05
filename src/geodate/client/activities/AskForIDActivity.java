package geodate.client.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import geodate.client.R;

public class AskForIDActivity extends GeoDateActivity {
	public static int ID = 1;
	
	@Override
	protected int getContentView() {
		return R.layout.debug_profile_get_id;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		
		((Button)findViewById(R.id.btnOk)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ID = Integer.parseInt(((EditText)findViewById(R.id.editProfileID)).getText().toString());
				setResult(ID);
				finish();
			}			
		});
	}
}
