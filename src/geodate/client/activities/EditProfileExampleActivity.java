package geodate.client.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import geodate.client.R;

public class EditProfileExampleActivity extends GeoDateActivity implements OnClickListener {

	@Override
	protected int getContentView() {
		return R.layout.empty_scrolling_linear;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		EditProfileExamplesActivity.Control controlToShow = 
			(EditProfileExamplesActivity.Control) getIntent().getExtras().get(EditProfileExamplesActivity.SELECTION);
		
		if (controlToShow == EditProfileExamplesActivity.Control.FreeForm) {
			setContentView(R.layout.edit_text);
			Button ok = (Button)findViewById(R.id.ok); 
			Button cancel = (Button)findViewById(R.id.cancel);
			ok.setOnClickListener(this);
			cancel.setOnClickListener(this);
		} else if (controlToShow == EditProfileExamplesActivity.Control.MultiSelect) {
			setContentView(R.layout.edit_select_many);						
			Button ok = (Button)findViewById(R.id.ok); 
			Button cancel = (Button)findViewById(R.id.cancel);
			ok.setOnClickListener(this);
			cancel.setOnClickListener(this);
		} else if (controlToShow == EditProfileExamplesActivity.Control.SelectOne) {
			setContentView(R.layout.edit_select_one);
			Spinner selection = (Spinner)findViewById(R.id.spinnerEthnicities);
			selection.setSelection(8);
			Button ok = (Button)findViewById(R.id.ok); 
			Button cancel = (Button)findViewById(R.id.cancel);
			ok.setOnClickListener(this);
			cancel.setOnClickListener(this);
		} else {
			setContentView(R.layout.edit_grid);			
			Button ok = (Button)findViewById(R.id.ok); 
			Button cancel = (Button)findViewById(R.id.cancel);
			ok.setOnClickListener(this);
			cancel.setOnClickListener(this);
		}
	}
	
	public void onClick(View v) {
		finish();
	}
}