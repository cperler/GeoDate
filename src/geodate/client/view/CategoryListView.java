package geodate.client.view;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class CategoryListView extends LinearLayout {
	private TextView labelView;
    private Spinner list;
    
    public CategoryListView(Context context, String label, List<String> values) {
        super(context);

        this.setOrientation(HORIZONTAL);
        
        labelView = new TextView(context);        
        labelView.setPadding(5, 5, 5, 5);
        labelView.setTextSize(12);
        labelView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        labelView.setText(label);
        addView(labelView, new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        list = new Spinner(context);        
        list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, values));
        addView(list, new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    }   
}