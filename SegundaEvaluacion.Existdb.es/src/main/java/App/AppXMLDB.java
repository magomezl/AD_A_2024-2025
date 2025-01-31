package App;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

import modelo.ModeloXMLDB;

public class AppXMLDB {
	private static String user ="admin";
	private static String password = "admin";
	private static String URICol = "xmldb:exist://localhost:8080/exist/xmlrpc/db/";

	public static void main(String[] args) {
		
		
		try {
			DatabaseManager dBM = ModeloXMLDB.getCon();
			//Comentario
			
			
			
			
			
//			ModeloXMLDB.listarContenido(dBM.getCollection(URICol+"ejercicio_clase",user, password));
//			ModeloXMLDB.creaBorraColeccion(dBM.getCollection(URICol+"ejercicio_clase",user, password), "datosHacienda", 'c');
//			ModeloXMLDB.creaBorraColeccion(dBM.getCollection(URICol+"ejercicio_clase",user, password), "coleccion2", 'd');
//			ModeloXMLDB.creaCreaDocumentoString(dBM.getCollection(URICol+"ejercicio_clase/datosHacienda",user, password), 
//					"mascotas.xml", "<mascotas><mascota tipo='perro' nombre='lolo'/></mascotas>");
//			ModeloXMLDB.descargarDocumento(dBM.getCollection(URICol+"ejercicio_clase/datosHacienda",user, password), 
//					"mascotas.xml", "C:\\Users\\admin\\Documents");
			
			ModeloXMLDB.subirDocumento(dBM.getCollection(URICol+"ejercicio_clase/datosHacienda",user, password), 
					"C:\\Users\\admin\\Documents\\mascotas.xml");
			
			
			
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			
	
	}
}
