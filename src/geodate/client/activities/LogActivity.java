package geodate.client.activities;

import geodate.client.R;
import geodate.client.util.Loggable;
import geodate.client.util.Logger;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LogActivity extends GeoDateActivity implements Loggable {	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.setDefaultLoggable(this);
    }
    
    @Override
    protected int getContentView() {
    	return R.layout.log;
    }    

    public void printLog(int priority, String text) {
    	TextView msg = new TextView(this);
		msg.setPadding(5, 1, 5, 1);		
		if (priority == Log.ERROR) {
			msg.setTextSize(12.0f);
			SpannableString formattedMsg = new SpannableString(text);
			formattedMsg.setSpan(new StyleSpan(Typeface.BOLD) , 0, formattedMsg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			msg.setText(formattedMsg);
		}
		else if (priority == Log.INFO) {
			msg.setTextSize(10.0f);
			SpannableString formattedMsg = new SpannableString(text);
			formattedMsg.setSpan(new StyleSpan(Typeface.ITALIC) , 0, formattedMsg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			msg.setText(formattedMsg);
		}
		else {
			msg.setTextSize(12.0f);
			msg.setText(text);
		}
		((LinearLayout)findViewById(R.id.log_container)).addView(msg);
    }   
}