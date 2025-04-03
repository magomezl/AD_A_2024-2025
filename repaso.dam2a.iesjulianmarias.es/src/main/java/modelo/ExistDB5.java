package modelo;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import modelo.conexionesSingleton.CnnXMLDB;
import modelo.conexionesSingleton.CnnXQJ;
import net.xqj.exist.ExistXQDataSource;

public class ExistDB5 {

	public static void main(String[] args) {
		try {
			Collection coleccion_padre = CnnXMLDB.getInstancia("admin", "admin", "db").getCol();
			
			Collection coleccion;
			// Si no existe la colección la crea
			if((coleccion=(Collection) (CnnXMLDB.getInstancia("admin", "admin", "db/EjerciciosRepaso").getCol()))==null) {
				CollectionManagementService servicio = (CollectionManagementService) coleccion_padre.getService("CollectionManagementService", "1.0");
				coleccion = servicio.createCollection("EjerciciosRepaso");
			}
			//Comprobamos si el recurso ya está en la colección
			File file = new File(Utilidades.getRuta()+Utilidades.getFileEjercicio5In());
			XMLResource recurso = (XMLResource) coleccion.getResource(file.getName());
			if (recurso == null) {
				//Creamos el recurso, le asignamos el contenido del fichero y lo almacenamos en la colección
				recurso = (XMLResource) coleccion.createResource(file.getName(), "XMLResource");
				
				if (!file.canRead()) {
					System.out.println("No se puede leer el archivo " + Utilidades.getRuta()+Utilidades.getFileEjercicio5In());
					return;
				}
				
				recurso.setContent(file);
				coleccion.storeResource(recurso);
			}
		
			
			
			XQConnection xqc = CnnXQJ.getInstancia("admin", "admin").getCon();
			
					
			// Para evitar duplicidad en los valores usamos rutas relativas al contexto
			String query = "for $pr in doc('EjerciciosRepaso/religiones.xml')/geografia/religiones_en_paises/practica " +
					" let $p := $pr/../../paises/pais[@id_pais=$pr/@id_pais]/@nombre " +
					" let $r := $pr/../../religiones/religion[@id_religion=$pr/@id_religion]/@denominacion " +
					" return "
					+ 		"<practicaReligion> " +
								" <pais nombre='{$p}'/> " +
								" <religion denominacion='{$r}'/> " +
								" <devotos practicantes='{$pr/@practicantes}'/> " +
							" </practicaReligion>";

			XQPreparedExpression expr = xqc.prepareExpression(query);
			XQResultSequence result = expr.executeQuery();
			
			// Creamos un documento DOM
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.newDocument();
            // Creamos el elemento raiz
            Element raiz = doc.createElement("religiones");
            doc.appendChild(raiz);
			
			while (result.next()) {
				System.out.println("elemento");
				Node node = result.getNode();
				//Importamos el nodo del documento
				Node importedNode = doc.importNode(node, true);
				//Añade nodo importado al elemento raiz
				raiz.appendChild(importedNode);
			}
			
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(doc), new StreamResult(new File(Utilidades.getRuta()+Utilidades.getFileEjercicio5Out())));
           
		
			result = expr.executeQuery();
			
			//Para obtener los valores de cada elemento retornado por la xquery 
			while (result.next()) {
				NodeList nodosHijos = result.getNode().getChildNodes();
				String pais = null, religion = null, devotos = null;
				for (int i=0; i<nodosHijos.getLength(); i++) {
					if (nodosHijos.item(i).getLocalName()!=null) {
						if (nodosHijos.item(i).getLocalName().equalsIgnoreCase("pais")) {
							pais = nodosHijos.item(i).getAttributes().getNamedItem("nombre").getNodeValue();

						}else if (nodosHijos.item(i).getLocalName().equalsIgnoreCase("religion")) {
							religion = nodosHijos.item(i).getAttributes().getNamedItem("denominacion").getNodeValue();

						}else if (nodosHijos.item(i).getLocalName().equalsIgnoreCase("devotos")) {	
							devotos = nodosHijos.item(i).getAttributes().getNamedItem("practicantes").getNodeValue();
							System.out.println("El pais " + pais + " tiene " + devotos + " practicantes de la religión " + religion );

						}
					}
				}
			}
			xqc.close();

			// TODO Crear un método para comprobar la existencia de la colección EjerciciosRepaso y subir recursos. Aplicarlo al ejercicio
			// TODO Completar salida por consola con porcentajes de practicantes de cada religion
			// TODO modificar el documento religiones2.xml y añadir en cada elemento <practicaReligion> 
			// el porcentaje de devotos de cada religión en relación a los habitantes del país correspondiente
			// Crear singletones. HECHO
			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
