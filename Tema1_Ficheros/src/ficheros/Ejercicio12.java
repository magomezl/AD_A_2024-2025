package ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import ficheros.ejercicio6.Persona;

public class Ejercicio12 {
	private final static String DOCTRABAJO_IN = "FicheroPersonasSerializado.dat";
	private final static String DOCTRABAJO_OUT = "FicheroPersonasSerializado.xml";

	public static void main(String[] args) {
		ObjectInputStream oIS = null;
		Document doc=null;
		try {
			DocumentBuilderFactory dBf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBf.newDocumentBuilder();
			doc = dB.newDocument();
			
			Element elementoRaiz = doc.createElement("personas");
			doc.appendChild(elementoRaiz);
			
			oIS = new ObjectInputStream(new FileInputStream(new File(Ejercicio3.RUTA+DOCTRABAJO_IN)));
			Persona persona = new Persona();
			
			while(true) {
				persona = (Persona) oIS.readObject();
				Element elementoPersona = doc.createElement("persona");
				
				CreaElemento("nombre", persona.getNombre().toString(), elementoPersona, doc);
				CreaElemento("apellido1", persona.getApellido1().toString(), elementoPersona, doc);
				CreaElemento("apellido2", persona.getApellido2().toString(), elementoPersona, doc);
				CreaElemento("nacimiento", persona.getNacimiento().toString(), elementoPersona, doc);
				
				elementoRaiz.appendChild(elementoPersona);
			}
		}catch (EOFException e) {
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				TransformerFactory tF = TransformerFactory.newInstance();
				Transformer t = tF.newTransformer();
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.transform(new DOMSource(doc), new StreamResult(new File(Ejercicio3.RUTA+DOCTRABAJO_OUT)));
				oIS.close();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void CreaElemento(String etiqueta, String valor, Element padre, Document doc) {
		Element elem = doc.createElement(etiqueta);
		Text texto = doc.createTextNode(valor);
		padre.appendChild(elem);
		elem.appendChild(texto);
	}
}
