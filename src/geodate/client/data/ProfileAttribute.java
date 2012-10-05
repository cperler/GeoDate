package geodate.client.data;

import java.util.ArrayList;


public class ProfileAttribute extends DataModel {
	@MetaProperty(wsName = "values") 
	protected ArrayList<String> values = null;
	
	@MetaProperty(wsName = "name")
	protected String name = null;
	
	public ProfileAttribute() {
		values = new ArrayList<String>();
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}