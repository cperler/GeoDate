package geodate.client.view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryEditTextView extends LinearLayout {
	private TextView labelView;
    private EditText field;
    
    public CategoryEditTextView(Context context, String label) {
        super(context);

        this.setOrientation(HORIZONTAL);
        
        labelView = new TextView(context);
        labelView.setPadding(5, 5, 5, 5);
        labelView.setTextSize(12);
        labelView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        labelView.setText(label);
        addView(labelView, new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        field = new EditText(context);
        field.setGravity(Gravity.FILL_HORIZONTAL);
        field.setPadding(5, 5, 5, 5);
        field.setHorizontallyScrolling(true);
        addView(field, new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    }   
}