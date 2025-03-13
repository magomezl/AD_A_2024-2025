package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import modelo.clasesJAXB.Geografia_v1;
import modelo.clasesJAXB.Idioma_v1;
import modelo.clasesJAXB.Pais_v1;

public class Manejador extends DefaultHandler{

	private static Geografia_v1 geografia;
	
	private static ArrayList<Pais_v1> paises = new ArrayList<Pais_v1>();
	private static Pais_v1 pais;
	private static Set<String> idiomas = new HashSet<String>();
	private static boolean esHabitantes = false, esIdioma = false;
	
	public static Geografia_v1 getGeografia() {
		return geografia;
	}

	@Override
	public void startDocument() throws SAXException {
		geografia = new Geografia_v1();
	}

	@Override
	public void endDocument() throws SAXException {
		geografia.setPaises(paises);
		for(String idioma: idiomas) {
			Idioma_v1 idiomaObj = new Idioma_v1(idioma);
			geografia.getIdiomas().add(idiomaObj);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("pais")) {
			pais = new Pais_v1();
			pais.setNombre(attributes.getValue(0));
		}else if (qName.equalsIgnoreCase("habitantes")) {
			esHabitantes = true;
		}else if (qName.equalsIgnoreCase("idioma")) {
			esIdioma =true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("pais")) {	
			paises.add(pais);
		}else  if (qName.equalsIgnoreCase("habitantes")) {
			esHabitantes = false;
		}else if (qName.equalsIgnoreCase("idioma")) {
			esIdioma =false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (esHabitantes) {
			pais.setHabitantes(new String(ch, start, length));
		}else if (esIdioma) {
			 idiomas.add(new String(ch, start, length));
		}
	}

}
