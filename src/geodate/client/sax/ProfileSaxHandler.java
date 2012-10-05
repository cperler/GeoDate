package geodate.client.sax;

import geodate.client.data.Picture;
import geodate.client.data.Profile;
import geodate.client.data.ProfileAttribute;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ProfileSaxHandler extends SaxHandler {

	private Profile profile;
	private ProfileAttribute profileAtt;

	private boolean inProfileAttributes = false;
	private boolean inSeeking = false;
	private boolean inProfileAttribute = false;
	private boolean inValues = false;
	
	private Picture picture;
	private boolean inPictures = false;
	private boolean inPicture = false;

	protected Object initializeData() {
		profile = new Profile();
		return profile;
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);

		if (localName.equals("profilePic")) {  getText(); }

		if (inProfileAttributes) {
			if (inProfileAttribute) {
				if (inValues) {
					if (localName.equals("value")) {	        
						profileAtt.getValues().add(getText());
					}
					if (localName.equals("values")) {
						inValues = false;
					}
				}
				if (!inValues) {
					if (localName.equals("name")) {	        		
						profileAtt.setName(getText());
					}
					if (localName.equals("profileAttribute")) {
						profile.getProfileAttributes().add(profileAtt);
						inProfileAttribute = false;
					}
				}	        		
			}
			if (localName.equals("profileAttributes")) {
				inProfileAttributes = false;	        	
			}
		}

		if (inSeeking) {
			if (inProfileAttribute) {
				if (inValues) {
					if (localName.equals("value")) {	        
						profileAtt.getValues().add(getText());
					}
					if (localName.equals("values")) {
						inValues = false;
					}
				}
				if (!inValues) {
					if (localName.equals("name")) {	        		
						profileAtt.setName(getText());
					}
					if (localName.equals("profileAttribute")) {
						profile.getSeeking().add(profileAtt);
						inProfileAttribute = false;
					}
				}	        		
			}
			if (localName.equals("seeking")) {
				inSeeking = false;	        	
			}
		}
		
		if (inPictures) {
			if (inPicture) {
				if (localName.equals("description")) {
					picture.setDescription(getText());
				} else if (localName.equals("name")) {				
					picture.setName(getText());
				} else if (localName.equals("rank")) {
					picture.setRank(Integer.parseInt(getText()));
				} else if (localName.equals("picture")) {
					profile.getPictures().add(picture);
					inPicture = false;					
				}
			} else if (localName.equals("pictures")) {			
				inPictures = false;
			}
		}

		if (localName.equals("userId")) {  profile.setUserId(Integer.parseInt(getText()));  }
	}


	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);

		if (localName.equals("profileAttributes")) { inProfileAttributes = true; }
		if (localName.equals("profileAttribute")) { 
			inProfileAttribute = true;
			profileAtt = new ProfileAttribute();
		}
		if (localName.equals("values")) { inValues = true; }
		if (localName.equals("seeking")) { inSeeking = true; }
		if (localName.equals("pictures")) { inPictures = true; }
		if (localName.equals("picture")) {
			inPicture = true;
			picture = new Picture();
		}
	}

	public Profile getProfile() {
		return profile;
	}
	
	public Object getData() {
		return getProfile();
	}
}
