package geodate.client.activities;

import geodate.client.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ProxyParametersActivity extends GeoDateActivity implements OnClickListener {
	private List<EditText> editFields = null;
	private TextView results = null; 
	
	@Override
	protected int getContentView() {
		return R.layout.empty_scrolling_linear;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);		
		
		editFields = new ArrayList<EditText>();
		
		TableLayout table = new TableLayout(this);		
		table.setColumnStretchable(1, true);
		
		Method methodToInvoke = ProxyActivity.LastInvokedMethod.getMethod();
		this.setTitle(methodToInvoke.getName());
		Class<?>[] parameters = methodToInvoke.getParameterTypes();
		for (int i = 0; i < parameters.length; i++) {
			TableRow row = new TableRow(this);			
			
			TextView type = new TextView(this);
			type.setText(i + ":" + parameters[i].getSimpleName() + "  ");
			type.setPadding(5, 5, 5, 5);
			type.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); 
			row.addView(type);
			
			EditText edit = new EditText(this);
			edit.setGravity(Gravity.FILL_HORIZONTAL);
			edit.setPadding(5, 5, 5, 5);
			edit.setHorizontallyScrolling(true);
			editFields.add(edit);
			row.addView(edit);			
			
			table.addView(row);
		}
		
		Button invoke = new Button(this);
		invoke.setText("Invoke");
		invoke.setOnClickListener(this);
		
		results = new TextView(this);
		results.setPadding(5, 5, 5, 10);		
		
		((LinearLayout)findViewById(R.id.empty_linear_container)).addView(table);
		((LinearLayout)findViewById(R.id.empty_linear_container)).addView(invoke);
		((LinearLayout)findViewById(R.id.empty_linear_container)).addView(results);
		
	}


	public void onClick(View v) {
		results.setText("");
		Object[] args = null;
		Method method = null;
		
		try {
			args = new Object[editFields.size()];
			for (int i = 0; i < args.length; i++) {
				args[i] = editFields.get(i).getText();
			}
						
			method = ProxyActivity.LastInvokedMethod.getMethod();
			Class<?>[] parameterTypes = method.getParameterTypes();
			for (int i = 0; i < args.length; i++) {
				if (parameterTypes[i] == int.class) {
					args[i] = Integer.parseInt(args[i].toString());
				}
				else if (parameterTypes[i] == String.class) {
					args[i] = args[i].toString();
				}
				else if (parameterTypes[i] == boolean.class) {
					args[i] = Boolean.parseBoolean(args[i].toString());
				}
			}
		}
		catch (Exception e) {
			results.setText("Error parsing arguments.");			
			return;
		}
		try {			
			Object resultValue = method.invoke(proxy, args);
			if (resultValue != null) {
				results.setText("Results:\n" + resultValue.toString());
			}
			else {
				results.setText("Server call resulted in null value response.");
			}
		} catch (IllegalArgumentException e) {
			results.setText("Invocation parameter types do not match the method signature.  Exception is: " + e.getMessage());			
		} catch (IllegalAccessException e) {
			results.setText("Illegal method call.  Exception is: " + e.getMessage());
		} catch (InvocationTargetException e) {
			results.setText("Result was null.  Target invocation exception: " + e.getTargetException().getClass().getSimpleName() + ", " + 
					e.getTargetException().getMessage());
		}
	}
}