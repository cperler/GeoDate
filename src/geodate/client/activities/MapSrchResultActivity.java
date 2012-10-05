package geodate.client.activities;

import geodate.client.R;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapSrchResultActivity extends GeoDateMapActivity {
	private Menu contextMenu = null;
	
	private MapView mapView;	
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private MapSrchResItemOverlay itemizedOverlay;
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_search_map);
        
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true); 
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.hot_chick);
        itemizedOverlay = new MapSrchResItemOverlay(this, drawable);        
        
        GeoPoint point = new GeoPoint(19240000,-99120000);
        OverlayItem overlayitem = new OverlayItem(point, "Swine", "flu");
        GeoPoint point2 = new GeoPoint(35410000, 139460000);
        OverlayItem overlayitem2 = new OverlayItem(point2, "Below", "Me");

        itemizedOverlay.addOverlay(overlayitem);
        itemizedOverlay.addOverlay(overlayitem2);
        mapOverlays.add(itemizedOverlay);               
        
        MapController mc = mapView.getController();
        mc.animateTo(point);
        mc.setZoom(10);
        
        longAlert("Click on the hot chick to talk to her.");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	contextMenu = menu;
    	menu.add(0, 0, 0, "View as List");    	
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	if (item.getItemId() == 0) {
    		setContentView(R.layout.mock_search_list);
    		contextMenu.removeItem(0);
    		contextMenu.add(0, 1, 0, "View as Map");
    		longAlert("Note that clicking icons will not yet lead to the communications page from here.");
    	} else if (item.getItemId() == 1) {
    		longAlert("At this point the view would revert to a map, but a ViewGroup needs to be employed to allow that...");    		
    	}
    	return super.onMenuItemSelected(featureId, item);
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}