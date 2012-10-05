package geodate.client.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class SmartExpandableListAdapter extends SimpleExpandableListAdapter {
	private List<? extends List<? extends Map<String, ?>>> childData;
	private int childLayout;
	private String[] childFrom;
	private int[] childTo;
	private HashMap<Integer, ? extends Map<String, Integer>> viewMap;
	private HashMap<Integer, List<String>> linkifyMap;
	private LayoutInflater inflater;
	private HashMap<Integer, HashMap<Integer, View>> inflatedViews;
	
	public SmartExpandableListAdapter(
			Context context, List<? extends Map<String, ?>> groupData, int expandedGroupLayout,
			int collapsedGroupLayout, String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo,
			HashMap<Integer, ? extends Map<String, Integer>> viewMap,
			HashMap<Integer, List<String>> linkifyMap) {
		super(context, groupData, expandedGroupLayout, collapsedGroupLayout, groupFrom,
				groupTo, childData, childLayout, childFrom, childTo);
		this.childData = childData;
		this.childLayout = childLayout;
		this.childFrom = childFrom;
		this.childTo = childTo;
		this.viewMap = viewMap;
		this.linkifyMap = linkifyMap;
		
		if (this.viewMap == null) {
			this.viewMap = new HashMap<Integer, Map<String,Integer>>();
		}
		
		if (this.linkifyMap == null) {
			this.linkifyMap = new HashMap<Integer, List<String>>();
		}
		
		inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		inflatedViews = new HashMap<Integer, HashMap<Integer,View>>();
		
		for (int i = 0; i < groupData.size(); i++) {
			inflatedViews.put(i, new HashMap<Integer, View>());
			for (int j = 0; j < groupData.get(i).size(); j++) {
				inflatedViews.get(i).put(j, null);
			}
		}
	}	

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}	
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View inflatedView;
		Map<String, ?> data = childData.get(groupPosition).get(childPosition);
		if (inflatedViews.get(groupPosition).get(childPosition) != null) {
			inflatedView = inflatedViews.get(groupPosition).get(childPosition);
		} else {
			Integer layoutId = childLayout;			
			for (Object value : data.values()) {
				if (viewMap.containsKey(groupPosition) && viewMap.get(groupPosition).containsKey(value)) {
					layoutId = viewMap.get(groupPosition).get(value);
				}
			}
			inflatedView = inflater.inflate(layoutId, parent, false);
			inflatedViews.get(groupPosition).put(childPosition, inflatedView);
		}
		
		boolean linkify = false;
		if (linkifyMap.containsKey(groupPosition)) {
			for (Object value : data.values()) {
				if (linkifyMap.get(groupPosition).contains(value)) {
					linkify = true;
				}
			}
		}
		
		bindView(inflatedView, childData.get(groupPosition).get(childPosition), childFrom, childTo, linkify);
		return inflatedView;
	}
	
	private void bindView(View view, Map<String, ?> data, String[] from, int[] to, boolean linkify) { 
		int len = to.length; 
		for (int i = 0; i < len; i++) { 
			TextView v = (TextView)view.findViewById(to[i]); 
			if (v != null) { 
				v.setText((String)data.get(from[i]));
				if (linkify) {
					Linkify.addLinks(v, Linkify.ALL);
				}
			} 
		} 
	}
}