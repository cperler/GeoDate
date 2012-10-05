package geodate.client.data;


public class Profile extends DataModel {
	@MetaProperty(wsName = "userName") 
	protected String userName = null;
	
	@MetaProperty(wsName = "lastName")
	protected String lastName = null;
	
	@MetaProperty(wsName = "street1")
	protected String street1 = null;
	
	private int age = -1;
	
	@MetaProperty(wsName = "street2")
	protected String street2 = null;

	@MetaProperty(wsName = "city")
	protected String city = null;

	@MetaProperty(wsName = "state")
	protected String state = null;
	
	@MetaProperty(wsName = "zip")
	protected String zip = null;

	@MetaProperty(wsName = "country")
	protected String country = null;

	@MetaProperty(wsName = "birthdate")
	protected String birthdate = null;
	
	@MetaProperty(wsName = "email")
	protected String email = null;
	
	@MetaProperty(wsName = "phone")
	protected String phone = null;

	@MetaProperty(wsName = "oneLiner")
	protected String oneLiner = null;

	@MetaProperty(wsName = "introduction")
	protected String introduction = null;

	@MetaProperty(wsName = "lookingFor")
	protected String lookingFor = null;

	@MetaProperty(wsName = "freeTime")
	protected String freeTime = null;

	@MetaProperty(wsName = "firstName")
	protected String firstName = null;

	@MetaProperty(wsName = "gender")
	protected String gender = null;

	@MetaProperty(wsName = "profileAttributes")
	protected ProfileAttributeList profileAttributes = null;

	@MetaProperty(wsName = "seeking")
	protected ProfileAttributeList seeking = null;

	@MetaProperty(wsName = "pictures")
	protected PictureList pictures = null;

	@MetaProperty(wsName = "userId")
	protected Integer userId = 0;

	public Profile() {
		profileAttributes = new ProfileAttributeList();
		seeking = new ProfileAttributeList();
		pictures = new PictureList();
	}

	public String getAddress() {	
		return getStreet1() +  ((getStreet2() == null || getStreet2() == "") ? "" : "\n" + getStreet2()) +
				getCity() + ", " + getState() + " " + getZip() + " " + getCountry();
	}

	public int getAge() {
		return age;
	}

	public ProfileAttribute getAttribute(String name) {
		return getProfileAttributes().findByAttributeName(name);
	}

	public String getBirthdate() {
		return birthdate;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFreeTime() {
		return freeTime;
	}

	public String getGender() {
		return gender.substring(0, 1).toUpperCase() + gender.substring(1);
	}

	public String getIntroduction() {
		return introduction;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getLookingFor() {
		return lookingFor;
	}
	
	public String getOneLiner() {
		return oneLiner;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public PictureList getPictures() {
		return pictures;
	}
	
	public ProfileAttributeList getProfileAttributes() {
		return profileAttributes;
	}
	
	public ProfileAttributeList getSeeking() {
		return seeking;
	}
	
	public ProfileAttribute getSeeking(String name) {
		return getSeeking().findByAttributeName(name);
	}
	
	public String getState() {
		return state;
	}
	
	public String getStreet1() {
		return street1;
	}
	
	public String getStreet2() {
		return street2;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
		
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setFreeTime(String freeTime) {
		this.freeTime = freeTime;
	}

	public void setGender(String gender) {		
		this.gender = gender;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}

	public void setOneLiner(String oneLiner) {
		this.oneLiner = oneLiner;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPictures(PictureList pictures) {
		this.pictures = pictures;
	}

	public void setProfileAttributes(ProfileAttributeList profileAttributes) {
		this.profileAttributes = profileAttributes;
	}

	public void setSeeking(ProfileAttributeList seeking) {
		this.seeking = seeking;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}