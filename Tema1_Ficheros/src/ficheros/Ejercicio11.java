package ficheros;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class Ejercicio11 {
	private final static String DOCTRABAJO = "Ejercicio11.xml";
	public static void main(String[] args) {
		
		try {
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB =  dBF.newDocumentBuilder();
			Document doc = dB.newDocument();
			
			// Creamos elementos DOM
			Element raiz = doc.createElement("elementoRaiz");
			Element hijo1 = doc.createElement("elementoHijo");
			Element hijo2 = doc.createElement("elementoHijo");
			Text textoh1 = doc.createTextNode("contenido hijo 1");
			Text textoh2 = doc.createTextNode("contenido hijo 2");
			
			// Creamos estructura de árbol DOM
			doc.appendChild(raiz);
			raiz.appendChild(hijo1);
			raiz.appendChild(hijo2);
			hijo1.appendChild(textoh1);
			hijo1.setAttribute("nombre", "hijo1");
			hijo2.appendChild(textoh2);
			hijo2.setAttribute("nombre", "hijo2");
			
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer t = tF.newTransformer();
			
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(doc), new StreamResult(System.out));
			t.transform(new DOMSource(doc), new StreamResult(new File(Ejercicio3.RUTA+DOCTRABAJO)));
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
