package jaxb;

import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jaxb.clasesEjercicio16.Examen;
import utilidades.Utilidades;

public class Ejercicio16 {
	private static JAXBContext jC; 
	private static Examen examen; 
	private static final String DOCUMENTO = "ejercicio16.xml";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * Carga en el javabean (clases creadas y etiquetadas) en xml
	 */
	public static void unmarshalling() {
		try {
			jC = JAXBContext.newInstance(Examen.class);
			Unmarshaller  uM= jC.createUnmarshaller();
			examen = (Examen) uM.unmarshal(new File(Utilidades.RUTA+DOCUMENTO));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}

}
