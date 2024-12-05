package ficheros.ejercicio13_SAX;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Manejador2 extends DefaultHandler {
	private String receta;
	private boolean encontrado = false;

	public Manejador2(String receta) {
		super();
		this.receta = receta.toLowerCase();
	}

	//Vamos a tener que comparar el nombre de la receta que nos pasen con el atributo 
			// nombre del elemento receta. Cuando lo encontremos, mostramos el atributo
			// nombre de sus elementos hijos ingrediente
			
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if (qName.equalsIgnoreCase("receta") && attributes.getValue("nombre").toLowerCase().contains(receta)) {
			encontrado = true;
			System.out.println("Ingredientes de la receta " + receta);
		}
		if (qName.equalsIgnoreCase("ingrediente") && encontrado){
			System.out.println(attributes.getValue("nombre"));
		}
		
		
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if (qName.equalsIgnoreCase("receta") && encontrado){
			encontrado = false;
		}
	} 
	
	
	
	
	
	

}
