package geodate.client.data;


@Table("category")
public class Category extends DataModel {
	@MetaProperty(wsName = "attributes") 
	protected AttributeList attributes = null;
	
	@MetaProperty(wsName = "controlType", dbName = "control_type")
	protected String controlType = null;
	
	@MetaProperty(wsName = "description", dbName = "description")
	protected String description = null;
	
	@MetaProperty(wsName = "name", dbName = "name")
	protected String name = null;	
	
	public Category() {
		attributes = new AttributeList(this);
	}
	
	public AttributeList getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributeList attributes) {
		this.attributes = attributes;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setProperty(int index, Object v) {
		super.setProperty(index, v);		
		if (PropertyHelper.idxToMeta(this.getClass()).size() > index && 
			PropertyHelper.idxToMeta(this.getClass()).get(index).wsName().equals("attributes")) {
			((AttributeList)v).setCategory(this);
		}
	}
}