package geodate.client.activities;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("unchecked")
public class MapSrchResItemOverlay extends ItemizedOverlay {
	private Context context = null;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	
	public MapSrchResItemOverlay(Context context, Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}
	
	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected boolean onTap(int index) {
		Intent communicate = new Intent();
		communicate.setClass(context, CommunicateActivity.class);
		context.startActivity(communicate);
		return true;
	}
}