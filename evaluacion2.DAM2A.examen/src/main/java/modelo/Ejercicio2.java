package modelo;

import java.lang.reflect.InvocationTargetException;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.Service;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio2 {
	public static void creaRecursosMongoDB_ExistDB() {
		try {
			//Conexión con MongoDB
			MongoClient cliente = MongoClients.create();
			MongoDatabase db = cliente.getDatabase("TurismoCyL");
			
			//Conexion con ExistDB
			Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);
			
			Collection colPadre = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/", "admin", "admin");
			// Creo la coleccion
			CollectionManagementService serMS = (CollectionManagementService) colPadre.getService("CollectionManagementService", "1.0");
			Collection colTrabajo = serMS.createCollection("HosteleriaCyL");
			
			DistinctIterable<String> tpos = db.getCollection("Alojamientos").distinct("tipo", String.class);
			DistinctIterable<String> provs = db.getCollection("Alojamientos").distinct("ubicacion.provincia", String.class);
			
			for(String tpo: tpos) {
				Resource recurso = colTrabajo.createResource(tpo+".xml", XMLResource.RESOURCE_TYPE);
				StringBuilder contenido = new StringBuilder();
				contenido.append("<alojamiento tipo='" + tpo + "'>");
				for(String prov: provs) {
					Long total = db.getCollection("Alojamientos").countDocuments(
							Filters.and(
									Filters.eq("tipo", tpo),
									Filters.eq("ubicacion.provincia", prov)
							));
					contenido.append("<provincia total='" + total + "'>" +  prov + "</provincia>" );
				}
				contenido.append("</alojamiento>");
				recurso.setContent(contenido);
				colTrabajo.storeResource(recurso);
				recurso=null;
			}
			cliente.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
			
}
