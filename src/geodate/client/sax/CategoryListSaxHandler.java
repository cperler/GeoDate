package geodate.client.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import geodate.client.data.Category;
import geodate.client.data.CategoryList;

public class CategoryListSaxHandler extends SaxHandler {
	private CategoryList categories;
	private Category category;
	
	private boolean inCategories = false;
	private boolean inCategory = false;
	private boolean inAttributes = false;
	
	@Override
	protected Object initializeData() {
		categories = new CategoryList();
		return categories;
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);
		
		if (inCategories) {
			if (inCategory) {
				if (inAttributes) {
					if (localName.equals("attribute")) {
						category.getAttributes().add(getText());
					}
					else if (localName.equals("attributes")) {
						categories.add(category);
						inAttributes = false;
					}
				}
				else if (localName.equals("controlType")) {
					category.setControlType(getText());
				}
				else if (localName.equals("description")) {
					category.setDescription(getText());
				}
				else if (localName.equals("name")) {
					category.setName(getText());
				}				
				else if (localName.equals("category")) {
					inCategory = false;
				}
			}
			else if (localName.equals("categories")) {
				inCategories = false;
			}
		}
	}
	
	public CategoryList getCategories() {
		return categories;
	}
	
	public Object getData() {
		return getCategories();
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {	
		super.startElement(uri, localName, name, attributes);
		
		if (localName.equals("categories")) { inCategories = true; }
		if (localName.equals("category")) { 
			inCategory = true;
			category = new Category();
		}
		if (localName.equals("attributes")) { inAttributes = true; }
	}
}
