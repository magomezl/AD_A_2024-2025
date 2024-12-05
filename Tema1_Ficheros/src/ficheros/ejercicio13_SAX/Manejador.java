package ficheros.ejercicio13_SAX;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Manejador extends DefaultHandler {

	public Manejador() {
		super();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		System.out.print("<"+ qName);
		if (attributes.getLength()>0) {
			for ( int i=0; i<attributes.getLength(); i++) {
				System.out.print("\t" + attributes.getLocalName(i) + "=" + attributes.getValue(i));
			}
		}
		System.out.print(">");
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		System.out.print("</"+ qName + ">");
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		System.out.println(new String(ch, start, length));
	}
	

}
