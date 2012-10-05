package geodate.client.data;

public class Picture extends DataModel {
	@MetaProperty(wsName = "description") 
	protected String description = null;
	
	@MetaProperty(wsName = "name")
	protected String name = null;
	
	@MetaProperty(wsName = "rank")
	protected int rank = 0;

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

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
