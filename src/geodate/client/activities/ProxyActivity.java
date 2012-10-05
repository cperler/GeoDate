package geodate.client.activities;

import geodate.client.R;
import geodate.client.proxy.GDServerProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProxyActivity extends GeoDateListActivity {
	public static DisplayableMethod LastInvokedMethod = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		getListView().setEmptyView(findViewById(R.id.soap_empty));		

		List<DisplayableMethod> methodDetails = new ArrayList<DisplayableMethod>();
		Method[] methods = GDServerProxy.class.getMethods();
		for (int i = 0; i < methods.length; i++) {						
			methodDetails.add(new DisplayableMethod(methods[i]));			
		}
		setListAdapter(new ArrayAdapter<DisplayableMethod>(this, android.R.layout.simple_list_item_1, methodDetails));
		getListView().setTextFilterEnabled(true);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {	
		super.onListItemClick(l, v, position, id);
		LastInvokedMethod = (DisplayableMethod)getListAdapter().getItem(position);
		Intent intent = new Intent();
		intent.setAction("geodate.client.proxytest");
    	startActivity(intent);
	}
	
	protected class DisplayableMethod
	{
		private Method method;
		private String displayString;
		
		public DisplayableMethod(Method method) {
			this.method = method;
			
			displayString = "";
			
			if (method != null) {
				String returnType = method.getReturnType().getSimpleName();
				String methodName = method.getName();
				
				displayString += returnType + " " + methodName + "(";
				
				Class<?>[] params = method.getParameterTypes();				
				for (int j = 0; j < params.length; j++) {
					Class<?> param = params[j];
					String parameterType = param.getSimpleName();
					displayString += parameterType;
					if (j < params.length - 1) {
						displayString += ", ";
					}
				}
				displayString += ")";
			}
		}
		
		public Method getMethod() {
			return method;
		}

		@Override
		public String toString() {
			return displayString;
		}
	}
	
	@Override
	protected int getContentView() {
		return R.layout.soap_main;
	}	
}
