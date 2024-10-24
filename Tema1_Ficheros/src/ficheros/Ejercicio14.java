package ficheros;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Ejercicio14 {
	private final static String DOCTRABAJO_XSL_IN = "Ejercicio14.xsl";
	private final static String DOCTRABAJO_XML_IN = "Ejercicio14.xml";
	private final static String DOCTRABAJO_HTML_OUT = "Ejercicio14.html";
	
	public static void main(String[] args) {
		try {
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer t = tF.newTransformer(new StreamSource(Ejercicio3.RUTA+DOCTRABAJO_XSL_IN));
			t.transform(new StreamSource(Ejercicio3.RUTA+DOCTRABAJO_XML_IN), 
					new StreamResult(Ejercicio3.RUTA+DOCTRABAJO_HTML_OUT));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
