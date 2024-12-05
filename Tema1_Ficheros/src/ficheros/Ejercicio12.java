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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import ficheros.ejercicio6.Persona;

public class Ejercicio12 {
	private final static String DOCTRABAJO_IN = "FicheroPersonasSerializado.dat";
	private final static String DOCTRABAJO_OUT = "FicheroPersonasSerializado.xml";
	private static DocumentBuilderFactory dBf = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder dB; 
	public static void main(String[] args) {
		ObjectInputStream oIS = null;
		Document doc=null;
		try {
			dB = dBf.newDocumentBuilder();
			System.out.println("dBf: " + dBf.toString() + "\ndB: " + dB.toString());
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
				LeerXML();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void LeerXML() {
		try {
			//Cargamos en memoria el doc XML        
			Document doc = dB.parse(new File(Ejercicio3.RUTA + DOCTRABAJO_OUT));
			LeeNodo(doc.getDocumentElement());
		} catch (Exception e) {e.printStackTrace();}
	}

	public static void LeeNodo(Node nodo) {
		if ( nodo.getNodeType()==Node.ELEMENT_NODE) {
			System.out.print("<" + nodo.getNodeName() + ">");
			
			NodeList nodosHijos = nodo.getChildNodes();
			if (nodosHijos.item(0).getNodeType()==Node.ELEMENT_NODE) { 
				System.out.println("\t");
			}
			for (int i=0; i<nodosHijos.getLength(); i++) {
				LeeNodo(nodosHijos.item(i));
			}
			System.out.println("</" + nodo.getNodeName() + ">");
			
		}else if (nodo.getNodeType()==Node.TEXT_NODE) {
			System.out.print(nodo.getNodeValue());
		}
	}

	private static void CreaElemento(String etiqueta, String valor, Element padre, Document doc) {
		Element elem = doc.createElement(etiqueta);
		Text texto = doc.createTextNode(valor);
		padre.appendChild(elem);
		elem.appendChild(texto);
	}
}
