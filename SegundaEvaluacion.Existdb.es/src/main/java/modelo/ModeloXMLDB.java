package modelo;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xquery.XQConnection;

import org.w3c.dom.Node;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;


public class ModeloXMLDB {
	private static final DatabaseManager DatabaseManager = null;


	public static DatabaseManager getCon() {
		try {
			Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 	DatabaseManager;
	}

	public static void creaBorraColeccion(Collection colPadre, String nameCol, char creaBorra) {
		try {
			if (colPadre==null) {
				System.out.println("La colección no existe o ha habido un error");
				return;
			}
			CollectionManagementService serCMS = (CollectionManagementService) 
					colPadre.getService("CollectionManagementService", "1.0");
			switch(creaBorra) {
			case 'c':
				serCMS.createCollection(nameCol);
				break;
			case 'd': 
				serCMS.removeCollection(nameCol);
				break;
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	public static void listarContenido(Collection col) {
		
		try {
			if (col==null) {
				System.out.println("La colección no existe o ha habido un error");
				return;
			}
			
			Service servicios[] = col.getServices();
			System.out.println("Servicios proporcinados:" + servicios.length);
			for(Service s: servicios) {
				System.out.println("\t" + s.getName() + "(" + s.getVersion() + ")");
			}
			
			System.out.println("Nombre de la colección: " + col.getName());
			System.out.println("Colecciones hijas: "  + col.getChildCollectionCount() );
			String nameColHijas[] =  col.listChildCollections();
			for(String nCH: nameColHijas) {
				System.out.println("\t" + nCH);
			}
			
			System.out.println("Documentos o recursos contenidos: "  + col.getResourceCount() );
			String nameColResources[] =  col.listResources();
			for(String nCR: nameColResources) {
				System.out.println("\t" + nCR);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void creaCreaDocumentoString(Collection col, String nameResource, String contenido) {
		try {
			if (col==null) {
				System.out.println("La colección no existe o ha habido un error");
				return;
			}
			
			Resource recurso = col.createResource(nameResource, XMLResource.RESOURCE_TYPE);
			recurso.setContent(contenido);
			col.storeResource(recurso);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void descargarDocumento(Collection col, String nameResource, String destinoURI) {
		try {
			if (col==null) {
				System.out.println("La colección no existe o ha habido un error");
				return;
			}
			XMLResource recurso = (XMLResource) col.getResource(nameResource);
			Node documento = recurso.getContentAsDOM();
			File f = new File(destinoURI+"/"+nameResource);
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.transform(new DOMSource(documento), new StreamResult(f));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void subirDocumento(Collection col, String origenURI) {
		try {
			if (col==null) {
				System.out.println("La colección no existe o ha habido un error");
				return;
			}
			
			File archivo = new File(origenURI);
			XMLResource recurso = (XMLResource) col.createResource(archivo.getName(), XMLResource.RESOURCE_TYPE);
			recurso.setContent(archivo);
			col.storeResource(recurso);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
