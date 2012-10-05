package geodate.client.activities;

import geodate.client.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MockupActivity extends GeoDateActivity {
	private Button viewSearchList = null;
	private Button viewSearchMap = null;
	private Button viewPersonalComm = null;
	private Button viewProfileListItemButton = null;
	private Button viewFullProfile = null;
	private Button viewPublicProfile = null;
	private Button viewEditProfile = null;
	private Button viewPhotoPicker = null;
	private Button viewLocationPrefs = null;
	private Button viewLocationTrigs = null;
	private Button viewTimeTrigs = null;
	private Button viewHelp = null;
	private Button viewQuickSearch = null;
	private Button viewBasicSearch = null;
	private Button viewLogin = null;
	private Button viewSavedSearches = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(getContentView()); 
        
        try {
        
		this.viewSearchList = (Button)this.findViewById(R.id.go_searchList);
		this.viewSearchList.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			setContentView(R.layout.mock_search_list);
		}
		});
		
		this.viewLocationTrigs = (Button)this.findViewById(R.id.go_location_trigs);
		this.viewLocationTrigs.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				setContentView(R.layout.mock_location_triggers);
			}
		});
      
		
		this.viewSearchMap = (Button)this.findViewById(R.id.go_searchMap);
		this.viewSearchMap.setOnClickListener(new OnClickListener() {
			
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(arg0.getContext(), MapSrchResultActivity.class);
			startActivity(intent);
		}
		});
		
		this.viewTimeTrigs = (Button)this.findViewById(R.id.go_timeTrigs);
		this.viewTimeTrigs.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				setContentView(R.layout.mock_time_triggers);
			}
		});
		
		this.viewPersonalComm = (Button)this.findViewById(R.id.go_persComm);
		this.viewPersonalComm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				setContentView(R.layout.mock_personal_comm);				
			}
		});
		
		this.viewHelp = (Button)this.findViewById(R.id.go_help);
		this.viewHelp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(arg0.getContext(), HelpActivity.class);
				startActivity(intent);
			}
			});
		
		this.viewProfileListItemButton = (Button)this.findViewById(R.id.go_profileListItem);
		this.viewProfileListItemButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setAction("geodate.client.profilelistitemactivity");
				startActivity(intent);
			}
		});
		
		this.viewFullProfile = (Button)this.findViewById(R.id.go_fullProfile);
		this.viewFullProfile.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(arg0.getContext(), FullProfileActivity.class);
				startActivity(intent);
			}
		});
		
		this.viewPublicProfile = (Button)this.findViewById(R.id.go_publicProfile);
		this.viewPublicProfile.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(arg0.getContext(), PublicProfileActivity.class);
				startActivity(intent);
			}
		});
		
		this.viewEditProfile = (Button)this.findViewById(R.id.go_editProfile);
		this.viewEditProfile.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(arg0.getContext(), EditProfileExamplesActivity.class);
				startActivity(intent);
			}
		});
		
		this.viewPhotoPicker = (Button)this.findViewById(R.id.go_photoPicker);
		this.viewPhotoPicker.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setType("image/*"); 
				intent.putExtra("crop", "true"); 
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", 96);
				intent.putExtra("outputY", 96); 				
				intent.putExtra("return-data", true); 
				startActivityForResult(intent, 8008); 				
			}
		});
		
		this.viewLocationPrefs = (Button)this.findViewById(R.id.go_locationPrefs);
		this.viewLocationPrefs.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				setContentView(R.layout.mock_location_prefs);
			}
		});
        }
        catch(Exception e)
        {
        	log(1, e.getMessage());
        }
        
        this.viewQuickSearch = (Button)this.findViewById(R.id.go_quickSearch);
        this.viewQuickSearch.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		Intent intent = new Intent();
        		intent.setClass(arg0.getContext(), QuickSearchActivity.class);
        		startActivity(intent);
        	}
        });
        
        this.viewBasicSearch = (Button)this.findViewById(R.id.go_basicSearch);
        this.viewBasicSearch.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		Intent intent = new Intent();
        		intent.setClass(arg0.getContext(), CustomSearchActivity.class);
        		startActivity(intent);
        	}
        });
        
        this.viewLogin = (Button)this.findViewById(R.id.go_Login);
        this.viewLogin.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		Intent intent = new Intent();
        		intent.setClass(arg0.getContext(), LoginActivity.class);
        		startActivity(intent);
        	}
        });
        
        this.viewSavedSearches = (Button)this.findViewById(R.id.go_manageSearches);
        this.viewSavedSearches.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		Intent intent = new Intent();
        		intent.setClass(arg0.getContext(), ManageSearchesActivity.class);
        		startActivity(intent);
        	}
        });
	}
	
	@Override
	protected int getContentView() {
		return R.layout.mockups;
	}

}
