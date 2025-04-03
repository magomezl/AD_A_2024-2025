package modelo.conexionesSingleton;

import java.lang.reflect.InvocationTargetException;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

public class CnnXMLDB {
		private static  CnnXMLDB instancia;
		private static Collection col; 
		
		private CnnXMLDB(String user, String passwd, String URICol) {
			try {
				//Cargamos el driver eXist
				Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
				// Creamos una instancia de la bbdd
				Database database = (Database) cl.getDeclaredConstructor().newInstance();
				// Registro del driver
				DatabaseManager.registerDatabase(database);
				col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/" + URICol,
						user, passwd);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMLDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		public static synchronized CnnXMLDB getInstancia(String user, String passwd, String URICol) {
			if (instancia==null ) {
				instancia = new CnnXMLDB(user, passwd, URICol);
			}
			return instancia;
		}

		public static Collection getCol() {
			return col;
		}
	}
	
