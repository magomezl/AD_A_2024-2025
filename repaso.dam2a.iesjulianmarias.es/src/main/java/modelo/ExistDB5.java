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

import net.xqj.exist.ExistXQDataSource;

public class ExistDB5 {

	public static void main(String[] args) {
		try {
			//Cargamos el driver eXist
			Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
			// Creamos una instancia de la bbdd
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			// Registro del driver
			DatabaseManager.registerDatabase(database);
			
			Collection coleccion_padre = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db", "admin", "admin");
			Collection coleccion;
			// Si no existe la colección la crea
			if((coleccion=(DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/EjerciciosRepaso", "admin", "admin")))==null) {
				CollectionManagementService servicio = (CollectionManagementService) coleccion_padre.getService("CollectionManagementService", "1.0");
				coleccion = servicio.createCollection("EjerciciosRepaso");
			}
			//Comprobamos si el recurso ya está en la colección
			File file = new File(Utilidades.getRuta()+Utilidades.getFileEjercicio5In());
			XMLResource recurso = (XMLResource) coleccion.getResource(file.getName());
			if (recurso == null) {
				//Creamos el recurso, le asignamos el contenido del fichero y lo almacenamos en la colección
				recurso = (XMLResource) coleccion.createResource(null, "XMLResource");
				
				if (!file.canRead()) {
					System.out.println("No se puede leer el archivo " + Utilidades.getRuta()+Utilidades.getFileEjercicio5In());
					return;
				}
				recurso.setContent(file);
				coleccion.storeResource(recurso);
			}
		
			ExistXQDataSource xqs =  new ExistXQDataSource();
			xqs.setProperty("serverName", "localhost");
			xqs.setProperty("port", "8080");
			xqs.setProperty("user", "admin");
			xqs.setProperty("password", "admin");
			
			XQConnection xqc = xqs.getConnection();
			
			// Para evitar duplicidad en los valores usamos rutas relativas al contexto
			String query = "for $pr in doc('EjerciciosRepaso/bcfc3eca.xml')/geografia/religiones_en_paises/practica " +
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
			
			
			// Solucionar problema de nombres en eXistdb
			// Crear un método para comprobar la existencia de la colección EjerciciosRepaso y subir recursos 
			// Completar salida por consola con porcentajes de practicantes de cada religion
			// crear singletones
			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
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
