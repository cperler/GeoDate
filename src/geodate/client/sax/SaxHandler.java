package geodate.client.sax;

import geodate.client.data.MetaProperty;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class SaxHandler extends DefaultHandler {
	private StringBuilder builder = null;
	private Object data = null;
	private HashMap<String, Field> propMap;

	public void startDocument() throws SAXException {
		super.startDocument();

		builder = new StringBuilder();
		this.data = initializeData();		
		extractFields();
	}

	protected abstract Object initializeData();
	
	public abstract Object getData();

	@Override
	public void error(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		super.error(e);
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);	    
	}

	protected String getText() {
		String txt = builder.toString();
		builder.setLength(0);
		return txt;
	}

	private void extractFields() {
		propMap = new HashMap<String, Field>();
		Field[] fields = data.getClass().getDeclaredFields();
		for (Field field : fields) {
			MetaProperty meta = field.getAnnotation(MetaProperty.class);			
			if (meta != null && field.getType() == String.class) {
				propMap.put(meta.wsName(), field);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);

		if (propMap.containsKey(localName)) {
			Field field = propMap.get(localName);
			try {
				field.setAccessible(true);
				field.set(data, getText());
			} catch (IllegalArgumentException e) {				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}