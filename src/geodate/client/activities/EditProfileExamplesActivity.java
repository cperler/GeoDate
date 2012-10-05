package geodate.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import geodate.client.R;

public class EditProfileExamplesActivity extends GeoDateActivity implements OnClickListener {
	public enum Control
	{
		FreeForm,
		SelectOne,
		MultiSelect,
		Grid
	}
	
	public static final String SELECTION = "CONTROL_SELECTION";
	
	private RadioGroup radioGroup;
	private Button goTime;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);

		radioGroup = (RadioGroup)findViewById(R.id.radioGroupOptions);
		goTime = (Button)findViewById(R.id.buttonGoTime);
		goTime.setOnClickListener(this);
	}
	
	@Override
	protected int getContentView() {
		return R.layout.edit_profile_example;
	}

	public void onClick(View v) {
		Intent intent = new Intent();		
		intent.setClass(this, EditProfileExampleActivity.class);		
		int selected = radioGroup.getCheckedRadioButtonId();
		if (selected == R.id.rb_text) {
			intent.putExtra(SELECTION, Control.FreeForm);	
		}
		else if (selected == R.id.rb_multi) {
			intent.putExtra(SELECTION, Control.MultiSelect);
		}
		else if (selected == R.id.rb_list) {
			intent.putExtra(SELECTION, Control.SelectOne);
		}
		else {
			intent.putExtra(SELECTION, Control.Grid);
		}		
    	startActivity(intent);
	}
}
