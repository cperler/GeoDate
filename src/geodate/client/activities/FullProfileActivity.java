package geodate.client.activities;

import geodate.client.R;
import geodate.client.data.Picture;
import geodate.client.data.Profile;
import geodate.client.db.ProfileElements;
import geodate.client.proxy.GDWebService;
import geodate.client.util.Logger;

import java.io.InputStream;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FullProfileActivity extends GeoDateExpandableListActivity { 
	private enum ProfileMenuOption {
		PublicProfile (0, "My Public Profile"), 
		EditProfile (1, "Edit my Profile!"), 
		SetPhoto (2, "Set my Photo"),
		LoadById(3, "Load by ID");

		private final int index;
		private final String text;

		ProfileMenuOption(int index, String text) {
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
	
    private ExpandableListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetSetProfile().execute(Integer.parseInt(getString(R.string.default_profile_id)));
    }
    
    protected class GetSetProfile extends AsyncTask<Integer, String, Profile> {
    	ProgressDialog dialog = new ProgressDialog(FullProfileActivity.this);
    	String error = null;
    	
    	@Override
    	protected void onPreExecute() {
    		dialog.setMessage("Updating...");
    		dialog.show();
    	}
    	
		@Override
		protected Profile doInBackground(Integer... params) {			
			Profile profile = null;
			if (params.length == 1) {
				try {
					publishProgress("Retrieving profile: " + params[0]);
					profile = proxy.getProfileByUserId(params[0]);
					publishProgress("Displaying profile: " + profile.getUserName());
					adapter = ProfileElements.getAdapter(getBaseContext(), profile);
				} catch (Exception e) {
					error = e.getMessage();					
					log(Log.ERROR, e.getMessage(), e);					
				}
			}
			return profile;
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			dialog.setMessage(values[0]);
		}
		
		@Override
		protected void onPostExecute(Profile profile) {
			if (profile != null) {
				bindToProfile(profile);
			}
			else if (error != null) {
				Toast.makeText(getBaseContext(), "Unable to retrieve profile: " + error, Toast.LENGTH_LONG).show();
			}
			dialog.dismiss();
	    }
		
		private void bindToProfile(Profile profile) {					
			((TextView)findViewById(R.id.NameLabel)).setText(profile.getUserName());
			((TextView)findViewById(R.id.Gender)).setText(profile.getGender());
			((TextView)findViewById(R.id.Age)).setText(Integer.toString(profile.getAge()));
			((TextView)findViewById(R.id.OneLiner)).setText(profile.getOneLiner());
			((TextView)findViewById(R.id.CityStateCountry)).setText(profile.getCity() + ", " + profile.getState() + ", " + profile.getCountry());
			
			Picture profilePic = profile.getPictures().findPictureByRank(1);
			if (profilePic != null) {				
				Logger.log(Log.DEBUG, this, "Starting the picture task.");
				new GetSetProfilePic().execute(profilePic.getName());
			}
			
			setListAdapter(adapter);
		}
    }
    
    protected class GetSetProfilePic extends AsyncTask<String, Void, Drawable> {
    	private ImageButton imageButton = (ImageButton)findViewById(R.id.ProfilePic);
    	
		@Override
		protected Drawable doInBackground(String... uris) {
			Drawable d = null;
			if (uris.length == 1) {
				Logger.log(Log.DEBUG, this, "Requesting from the uri " + uris[0]);
				InputStream contents = GDWebService.getURIContents(getBaseContext(), uris[0]);
				if (contents != null) {
					Logger.log(Log.INFO, this, "Streaming from " + uris[0]);					
					d = Drawable.createFromStream(contents, "src");					
				}
			}
			return d;
		}
		
		@Override
		protected void onPreExecute() {
			imageButton.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.no_picture_available));
		}
		
		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
				Logger.log(Log.DEBUG, this, "Setting the drawable.");
				imageButton.setImageDrawable(result);				
			}
	    }
    }
    
    @Override
    protected int getContentView() {
    	return R.layout.full_profile_view;
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		for (ProfileMenuOption option : ProfileMenuOption.values()) {
			menu.add(0, option.index, 0, option.text);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean handled = true;
		if (item.getItemId() == ProfileMenuOption.PublicProfile.index) {
			Intent publicProfile = new Intent();
			publicProfile.setClass(this, PublicProfileActivity.class);
			startActivity(publicProfile);
		} else if (item.getItemId() == ProfileMenuOption.EditProfile.index) {
			Intent editProfile = new Intent();
			editProfile.setClass(this, EditProfileExamplesActivity.class);
			startActivity(editProfile);
		} else if (item.getItemId() == ProfileMenuOption.SetPhoto.index) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*"); 
			intent.putExtra("crop", "true"); 
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 96);
			intent.putExtra("outputY", 96); 				
			intent.putExtra("return-data", true); 
			startActivityForResult(intent, 8008);
		} else if (item.getItemId() == ProfileMenuOption.LoadById.index) {
			Intent getId = new Intent();
			getId.setClass(this, AskForIDActivity.class);
			startActivityForResult(getId, 0); 
		}else {
			handled = false;
		}
		return handled;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {	
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 0) {
			int id = resultCode;
			if (id != 0) {
				new GetSetProfile().execute(id);
			}
		}
	}
}