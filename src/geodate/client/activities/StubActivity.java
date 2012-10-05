package geodate.client.activities;

import geodate.client.R;
import geodate.client.data.CategoryList;
import geodate.client.data.Profile;
import geodate.client.db.ControlsDBAdapter;
import geodate.client.db.DBAdapterFactory;
import geodate.client.util.Preferences;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StubActivity extends GeoDateActivity {
	private ControlsDBAdapter controlsDB = null;
	private int serverVersion = -2;
	private int clientVersion = -1;
	
	private ProgressDialog pd;
	private String dataForView = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlsDB = DBAdapterFactory.getAdapter(this, ControlsDBAdapter.class);        
                        
        pd = ProgressDialog.show(this, "GeoDate", "Loading...");        
        updateSchemaIfNecessary();
    }	
    
    public void updateProgress(final String progressMsg) 
    {
        handler.post( new Runnable() 
        { 
            public void run() 
            { 
            	pd.setMessage(progressMsg);
            	((TextView)findViewById(R.id.main_text)).append(dataForView);
            	dataForView = "";
            	
            	if (progressMsg == "Finished.") {
            		pd.dismiss();
            	}
            } 
        }); 
    } 
    
    @Override
    protected void log(int priority, String msg) {    
    	super.log(priority, msg);
    	if (priority == Log.ERROR) {
    		dataForView = "\n\nError: " + msg + "\n";
    	}
    	updateProgress(msg);
    }
    
    @Override
    protected void log(int priority, String msg, Throwable error) {    
    	super.log(priority, msg, error);
    	if (priority == Log.ERROR) {
    		dataForView = "\n\nError: " + msg + "\n";
    	}
    	
    	if (error != null) {
    		dataForView += error.getClass().getSimpleName() + " : " + error.getMessage() + "\n\n"; 
    	}
    	
    	updateProgress(msg);
    }
    
    @Override
    protected int getContentView() {
    	return R.layout.main;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    
    	super.onCreateOptionsMenu(menu);    	
    	menu.add(0, 0, 0, "Service Invoker");
    	menu.add(0, 1, 0, "Log");
    	menu.add(0, 2, 0, "Mockup Screens");
    	menu.add(0, 3, 0, "HTTP Request Tester");
    	menu.add(0, 4, 0, "Categories -> Controls");
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	if (item.getItemId() == 0) {
    		Intent intent = new Intent(this, ProxyActivity.class);
    		startActivity(intent);
    	}
    	else if (item.getItemId() == 1) {
    		Intent intent = new Intent(this, LogActivity.class);
    		startActivity(intent);
    	}
    	else if (item.getItemId() == 2) {
    		Intent intent = new Intent(this, MockupActivity.class);
    		startActivity(intent);
    	}
    	else if (item.getItemId() == 3) {
    		Intent intent = new Intent(this, WebRequestActivity.class);
    		startActivity(intent);
    	}
    	else if (item.getItemId() == 4) {
    		Intent intent = new Intent(this, CategoryTestActivity.class);
    		startActivity(intent);
    	}
    	return true;
    }
    
    private class ContextThread extends Thread {
    	private Context context = null;
    	
    	public ContextThread(Context context) {
    		this.context = context; 
    	}
    	
    	public Context getContext() {
    		return context;
    	}
    }
    
    private void updateSchemaIfNecessary() {
    	Thread t = new ContextThread(this) {
    		public void run() {    			
		    	try {    		
			    	if (!isVersionCurrent(getContext())) {
			    		if (updateSchema()) {
			    			if (serverVersion >= 0) {
			    				log(Log.DEBUG, "Saving client version: " + serverVersion);
			    				Preferences.setVersion(getContext(), serverVersion);
			    			}
			    			else {
			    				log(Log.DEBUG, "Server version indicated forced refresh, reverting client version to 0.");
				    			Preferences.setVersion(getContext(), 0);
				    		}
			    		}
			    	}	    	
		    	}
		    	catch (Exception e) {
		    		log(Log.ERROR, "Unable to check schema version.", e);    		    	
		    	}
    
		    	try {
		    		log(Log.DEBUG, "Getting categories from DB...");
    				dataForView = controlsDB.getAllCategories().toString() + "\n\n";
		    	}
    			catch (Exception e) {
		    		log(Log.ERROR, "Unable to get categories.", e);    		    	
		    	}
    			
    			try {
    				
    				log(Log.DEBUG, "Getting profile from server...");
    				Profile profile = proxy.getProfileByUserId(1);
    				if (profile == null) {
    					log(Log.ERROR, "No profile found for the given id.");
    				}
    				else {
    					dataForView = profile.toString() + "\n\n";
    				}    				    		    				
    			} catch (Exception e) {
    				log(Log.ERROR, "Unable to retrieve profile.", e);			
    			}
    			updateProgress("Finished.");
    		}    		
    	};
    	t.start();
    }
    
    private boolean isVersionCurrent(Context context) throws IOException, XmlPullParserException {    	
    	clientVersion = Preferences.getVersion(context, 0);
    	log(Log.DEBUG, "Client Version = " + clientVersion);
    	serverVersion = proxy.getVersion();
    	log(Log.DEBUG, "Server Version = " + serverVersion);    	
    	return serverVersion == clientVersion;
    }
    
    private boolean updateSchema() {
    	try {    		
    		log(Log.DEBUG, "Updating the schema...");
    		CategoryList categories = proxy.getControls();
    		if (categories == null) {
    			log(Log.ERROR, "No categories found.");
    		}
    		else {
	    		log(Log.DEBUG, "Refreshing DB...");
	    		controlsDB.refresh();
	    		log(Log.DEBUG, "Saving categories...");
	    		controlsDB.save(categories);
	    		log(Log.DEBUG, "Categories saved.");
    		}
    	}
    	catch (Exception e) {    		
    		log(Log.ERROR, "Unable to update the schema.", e);    		
    		return false;
    	}

    	return true;
    }       
}