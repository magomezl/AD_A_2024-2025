package modelo;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
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
			// TODO modificar el documento religiones2.xml y añadir en cada elemento <practicaReligion> 
			// el porcentaje de devotos de cada religión en relación a los habitantes del país correspondiente
			
			subeRecurso("EjerciciosRepaso", Utilidades.getRuta()+Utilidades.getFileEjercicio5In(), "admin", "admin");
			crea_sube_Recurso1("EjerciciosRepaso", Utilidades.getRuta()+Utilidades.getFileEjercicio5Out(), "admin", "admin");
			subeRecurso("EjerciciosRepaso", Utilidades.getRuta()+Utilidades.getFileEjercicio5Out(), "admin", "admin");
			muestraPorcentajes("db", "EjerciciosRepaso", Utilidades.getRuta()+Utilidades.getFileEjercicio5In(), "admin", "admin");

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void muestraPorcentajes(String colPadreName, String colName, String urlFile, String usuario, String contrasenia) {
		try {
			
			String query = "for $pr in doc('" + colName + "/religiones.xml')/geografia/religiones_en_paises/practica " +
					" let $p := $pr/../../paises/pais[@id_pais=$pr/@id_pais]/@nombre " + 
					" let $r := $pr/../../religiones/religion[@id_religion=$pr/@id_religion]/@denominacion " + 
					" let $h := $pr/../../paises/pais[@id_pais=$pr/@id_pais]/@num_habitantes " +
					" let $por := ($pr/@practicantes div $h) * 100 " +
					  "  return " +
					  "	<practicaReligion> " + 
						   " <pais nombre='{$p}'/> " +
						   " <religion denominacion='{$r}'/> " +
						   " <devotos practicantes='{$pr/@practicantes}' porcentaje='{format-number($por, '#.00')}'/> " + 
					   " </practicaReligion> ";

			XQConnection xqc = CnnXQJ.getInstancia(usuario, contrasenia).getCon();
			XQPreparedExpression expr = xqc.prepareExpression(query);
			XQResultSequence result = expr.executeQuery();
			
//			TODO completar este método para modificar el documento xml para añadirle el atributo porcentaje al elemento devotos
//			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//			Document doc = db.parse(urlFile);	
			
			//Para obtener los valores de cada elemento retornado por la xquery 
			while (result.next()) {
				NodeList nodosHijos = result.getNode().getChildNodes();
				String pais = null, religion = null, devotos = null, porcentaje = null;
				for (int i=0; i<nodosHijos.getLength(); i++) {
					if (nodosHijos.item(i).getLocalName()!=null) {
						if (nodosHijos.item(i).getLocalName().equalsIgnoreCase("pais")) {
							pais = nodosHijos.item(i).getAttributes().getNamedItem("nombre").getNodeValue();
						}else if (nodosHijos.item(i).getLocalName().equalsIgnoreCase("religion")) {
							religion = nodosHijos.item(i).getAttributes().getNamedItem("denominacion").getNodeValue();
						}else if (nodosHijos.item(i).getLocalName().equalsIgnoreCase("devotos")) {	
							devotos = nodosHijos.item(i).getAttributes().getNamedItem("practicantes").getNodeValue();
							porcentaje = nodosHijos.item(i).getAttributes().getNamedItem("porcentaje").getNodeValue();
							System.out.println("El pais " + pais + " tiene " + devotos + " practicantes de la religión " + religion + " (" + porcentaje + ")" );
						}
					}
				}
			}
		}catch(XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * Crea un documento XML con DOM haciendo una consulta XQuery y lo sube a eXistdb 
	 * @param colPadreName nombre de la colección contenedora
	 * @param colName nombre de la colección a la que se subirá el recurso. Si no existe se crea
	 * @param urlFile url del fichero que se subirá
	 * @param usuario nombre de usuario en eXistdb
	 * @param contrasenia contraseña en eXistdb
	 */
	private static void crea_sube_Recurso1(String colName, String urlFile, String usuario, String contrasenia) {
		try {
			
			// Para evitar duplicidad en los valores usamos rutas relativas al contexto
			String query = "for $pr in doc('" + colName + "/religiones.xml')/geografia/religiones_en_paises/practica " +
					" let $p := $pr/../../paises/pais[@id_pais=$pr/@id_pais]/@nombre " +
					" let $r := $pr/../../religiones/religion[@id_religion=$pr/@id_religion]/@denominacion " +
					" return "
					+ 		"<practicaReligion> " +
					" <pais nombre='{$p}'/> " +
					" <religion denominacion='{$r}'/> " +
					" <devotos practicantes='{$pr/@practicantes}'/> " +
					" </practicaReligion>";
			
			XQConnection xqc = CnnXQJ.getInstancia(usuario, contrasenia).getCon();
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
			// Hacemos la transformación del árbol DOM que está en memoria a un fichero físico
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(doc), new StreamResult(new File(urlFile)));
			

		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	/**
	 * Sube un fichero a una colección de eXistdb cuyo nombre se le pasa como parámetro
	 * @param colName nombre de la colección a la que se subirá el recurso. Si no existe se crea
	 * @param urlFile url del fichero que se subirá 
	 * @param usuario nombre de usuario en eXistdb
	 * @param contrasenia contraseña en eXistdb
	 */
	private static void subeRecurso(String colName, String urlFile, String usuario, String contrasenia) {
		try {
			Collection coleccion, coleccion_padre = CnnXMLDB.getInstancia(usuario, contrasenia).getCol();
			
			// Si no existe la colección la crea
			if((coleccion= coleccion_padre.getChildCollection(colName))==null) {
				CollectionManagementService servicio = (CollectionManagementService) coleccion_padre.getService("CollectionManagementService", "1.0");
				coleccion = servicio.createCollection(colName);
			}
			//Comprobamos si el recurso ya está en la colección
			File file = new File(urlFile);
			XMLResource recurso = (XMLResource) coleccion.getResource(file.getName());
			if (recurso == null) {
				//Creamos el recurso, le asignamos el contenido del fichero y lo almacenamos en la colección
				recurso = (XMLResource) coleccion.createResource(file.getName(), "XMLResource");
				recurso.setContent(file);
				coleccion.storeResource(recurso);
			}
		}catch(XMLDBException e) {
			e.printStackTrace();
		}

	}
}
