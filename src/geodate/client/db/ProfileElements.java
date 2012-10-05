package geodate.client.db;

import geodate.client.R;
import geodate.client.data.Profile;
import geodate.client.data.ProfileAttribute;
import geodate.client.proxy.GDServerProxyImpl;
import geodate.client.util.Config;
import geodate.client.widget.SmartExpandableListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

public class ProfileElements {
	public class Category {
		public String Name;
		public int Idx;
		public List<Field> Fields;

		public Category(String name, Field[] fields) {
			this.Name = name;			
			this.Fields = new ArrayList<Field>();

			for (int i = 0; i < fields.length; i++) {
				this.Fields.add(fields[i]);
				fields[i].Idx = i;
				fields[i].Category = this;
			}
		}
	}

	public class Field {
		public String Name;
		public int Idx;
		public boolean Linkify;
		public Category Category;
		public Integer ProfileOverride = null;

		public Field(String name) {
			this.Name = name;			
			this.Linkify = false;
		}

		public Field(String name, boolean linkify) {
			this.Name = name;			
			this.Linkify = linkify;
		}

		public Field(String name, int profileOverride) {
			this.Name = name;			
			this.Linkify = false;
			this.ProfileOverride = profileOverride;
		}

		public Field(String name, boolean linkify, int profileOverride) {
			this.Name = name;			
			this.Linkify = linkify;
			this.ProfileOverride = profileOverride;
		}
	}

	private static int expanded_group_layout = R.layout.profile_category; 
	private static int collapsed_group_layout = R.layout.profile_category;
	private static String[] group_binding_keys = new String[] {"category"};
	private static int[] group_binding_control = new int[]{R.id.profile_category};
	private static int child_layout = R.layout.profile_data;
	private static String[] child_binding_keys = new String[]{"label", "value"};
	private static int[] child_binding_control = new int[] {R.id.profile_data_label, R.id.profile_data_value};

	private static List<HashMap<String, String>> group_list = null;
	private static HashMap<Integer, HashMap<String,Integer>> layout_override_map = null;
	private static HashMap<Integer, List<String>> layout_link_map = null;

	private static ProfileElements instance;
	public static Category[] Elements = null;
	
	static {
		instance = new ProfileElements();
		
		createFields();
		createGroupList();
		createLayoutOverrideMap();
		createLayoutLinkMap();
	}
			
	private static void createFields() {
		Elements = new Category[]{		
			instance.new Category("The Basics", new Field[] {
				instance.new Field("Name"),
				instance.new Field("Address", true, R.layout.profile_data_private),
				instance.new Field("Gender"),
				instance.new Field("Birthdate"),
				instance.new Field("Email", true),
				instance.new Field("Phone", true, R.layout.profile_data_private),
		}),
		instance.new Category("About Me", new Field[] {
				instance.new Field("My One Liner"),
				instance.new Field("Introduction", R.layout.profile_data_bold),
				instance.new Field("Looking For"),
				instance.new Field("Free Time"),
				instance.new Field("Social Setting")
		}),
		instance.new Category("Physical Traits", new Field[] {
				instance.new Field("Ethnicity"),
				instance.new Field("Body Type"),
				instance.new Field("Eye Color"),
				instance.new Field("Hair Color")
		}),
		instance.new Category("Home Life", new Field[] {
				instance.new Field("Marital Status"),
				instance.new Field("Living Situation"),
				instance.new Field("Want Kids"),
				instance.new Field("Have Kids"),
				instance.new Field("Pets")
		}),
		instance.new Category("Work / Education", new Field[] {
				instance.new Field("Occupation"),
				instance.new Field("Employment Status"),
				instance.new Field("Income Level"),
				instance.new Field("Education")
		}),
		instance.new Category("What's My Deal?", new Field[] {
				instance.new Field("Drinks"),
				instance.new Field("Smokes"),
				instance.new Field("Exercise"),
				instance.new Field("Politics"),
				instance.new Field("Religion"),
				instance.new Field("Religion Practice"),
				instance.new Field("Hobbies"),
				instance.new Field("Languages"),
				instance.new Field("TV")
		})};
	}

	private static void createGroupList() {
		group_list = new ArrayList<HashMap<String, String>>();
		for(int i = 0; i < Elements.length; i++) {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("category", Elements[i].Name);
			group_list.add( m );
		}
	}

	private static List<ArrayList<HashMap<String, String>>> createChildList(HashMap<String, Object> profileData) {
		ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();
		for(int i = 0; i < Elements.length; i++) {
			ArrayList<HashMap<String, String>> secList = new ArrayList<HashMap<String, String>>();
			for(int n = 0; n < Elements[i].Fields.size(); n++) {
				if (profileData.containsKey(Elements[i].Fields.get(n).Name)) {
					HashMap<String, String> child = new HashMap<String, String>();
					child.put("label", Elements[i].Fields.get(n).Name);
					child.put("value", profileData.get(Elements[i].Fields.get(n).Name).toString());
					secList.add( child );
				}
			}
			result.add( secList );
		}
		return result;
	}   
	
	private static HashMap<String, Object> adaptFullProfile(Context context, Profile profile) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("Name", profile.getUserName());
		data.put("Address",  profile.getAddress());
		data.put("Gender", profile.getGender());
		
		SimpleDateFormat parser = null;
		boolean usingLocalUri = Boolean.parseBoolean(Config.getProperty(context, GDServerProxyImpl.class, "useLocalUri"));
		if (usingLocalUri) {
			parser = new SimpleDateFormat(Config.getProperty(context, ProfileElements.class, "localDateFormat"));
		}
		else {
			parser = new SimpleDateFormat(Config.getProperty(context, ProfileElements.class, "remoteDateFormat"));
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			Date birthdate = parser.parse(profile.getBirthdate());
			
			int age = 0;
			Date tmp = (Date)birthdate.clone();
			Date now = new Date();
			while (tmp.before(now)) {
				tmp.setYear(tmp.getYear() + 1);
				age++;
			}
			if (tmp.after(now)) {
				age--;
			}
			profile.setAge(age);
			data.put("Birthdate", formatter.format(birthdate));
		} catch (ParseException e) {
			data.put("Birthdate", profile.getBirthdate());			
		}		
		data.put("Email", profile.getEmail());
		data.put("Phone", profile.getPhone());
		
		data.put("My One Liner", profile.getOneLiner());
		data.put("Introduction", profile.getIntroduction());
		data.put("Looking For", profile.getLookingFor());
		data.put("Free Time", profile.getFreeTime());		
		addAttribute(profile, "Social Setting", data);
		addAttribute(profile, "Ethnicity", data);
		addAttribute(profile, "Body Type", data);
		addAttribute(profile, "Eye Color", data);
		addAttribute(profile, "Hair Color", data);
		
		addAttribute(profile, "Marital Status", data);
		addAttribute(profile, "Living Situation", data);
		addAttribute(profile, "Want Kids", data);
		addAttribute(profile, "Have Kids", data);
		addAttribute(profile, "Pets", data);
		
		addAttribute(profile, "Occupation", data);
		addAttribute(profile, "Employment Status", data);
		addAttribute(profile, "Income Level", data);
		addAttribute(profile, "Education", data);
		
		addAttribute(profile, "Drinks", data);
		addAttribute(profile, "Smokes", data);
		addAttribute(profile, "Exercise", data);
		addAttribute(profile, "Politics", data);
		addAttribute(profile, "Religion", data);
		addAttribute(profile, "Religion Practice", data);
		addAttribute(profile, "Hobbies", data);
		addAttribute(profile, "Languages", data);
		addAttribute(profile, "TV", data);
		
		return data;
	}
	
	private static void addAttribute(Profile profile, String attributeName, HashMap<String, Object> data) {
		for (String alias : getAliases(attributeName)) {
			ProfileAttribute attribute = profile.getAttribute(alias);
			if (attribute != null) {
				data.put(attributeName, join(attribute));
				return;
			}
		}
		data.put(attributeName, "--");
	}
	
	private static List<String> getAliases(String attributeName) {
		List<String> aliases = new ArrayList<String>();
		aliases.add(attributeName);
		aliases.add(attributeName.toLowerCase());
		aliases.add(attributeName.toUpperCase());
		aliases.add(attributeName.toUpperCase().replaceAll(" ", "_"));		
		return aliases;
	}
	
	/*private static void addSeeking(Profile profile, String attributeName, HashMap<String, Object> data) {		
		for (String alias : getAliases(attributeName)) { 
			ProfileAttribute attribute = profile.getSeeking(alias);
			if (attribute != null) {
				data.put(attributeName, join(attribute));
				return;
			}
		}
	}*/
	
	private static String join(ProfileAttribute attribute) {	
		String out = "";
		if (attribute != null) {
			for (int i = 0; i < attribute.getValues().size(); i++) {
				out += attribute.getValues().get(i);
				if (i < attribute.getValues().size() - 1) {
					out += ", ";
				}
			}
		}
		return out;
	}
	
	private static void createLayoutOverrideMap() {
		layout_override_map = new HashMap<Integer, HashMap<String,Integer>>();
		for (int i = 0; i < Elements.length; i++) {
			for (int n = 0; n < Elements[i].Fields.size(); n++) {
				if (Elements[i].Fields.get(n).ProfileOverride != null) {
					if (!layout_override_map.containsKey(i)) {
						layout_override_map.put(i, new HashMap<String, Integer>());
					}
					layout_override_map.get(i).put(Elements[i].Fields.get(n).Name, Elements[i].Fields.get(n).ProfileOverride);
				}
			}
		}
	}

	private static void createLayoutLinkMap() {
		layout_link_map = new HashMap<Integer, List<String>>();
		for (int i = 0; i < Elements.length; i++) {
			for (int n = 0; n < Elements[i].Fields.size(); n++) {
				if (Elements[i].Fields.get(n).Linkify) {
					if (!layout_link_map.containsKey(i)) {
						layout_link_map.put(i, new ArrayList<String>());
					}
					layout_link_map.get(i).add(Elements[i].Fields.get(n).Name);
				}
			}
		}		
	}
	
    public static SmartExpandableListAdapter getAdapter(Context context, Profile profile) {
		SmartExpandableListAdapter adapter = new SmartExpandableListAdapter( 
				context,
				group_list,
				expanded_group_layout,
				collapsed_group_layout,
				group_binding_keys,
				group_binding_control,
				createChildList(adaptFullProfile(context, profile)),
				child_layout,
				child_binding_keys,
				child_binding_control,
				layout_override_map,
				layout_link_map
		);
		return adapter;
	}
}