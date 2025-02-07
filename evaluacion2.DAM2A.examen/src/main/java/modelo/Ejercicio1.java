package modelo;

import java.util.HashSet;
import java.util.List;

import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import clasesHibernate.Alojamientosturismorural;

public class Ejercicio1 {

	public static void migraDatosMySQL_MongoDB() {
		//Conexión con MySQL WorkBench
		Configuration cfg = new Configuration().configure();
		SessionFactory sf = cfg.buildSessionFactory();
		
		//Conexión con MongoDB
		MongoClient cliente = MongoClients.create();
		MongoDatabase db = cliente.getDatabase("TurismoCyL");
		
		Session sesion = sf.openSession();
		
		//Almaceno las categorias en un HashSet 
//		HashSet<String> categorias = new HashSet<String>();
		Document docAlojamiento = null;
		Document docCategoria = null;
		List<Alojamientosturismorural> alojSTR = sesion.createQuery("FROM Alojamientosturismorural", Alojamientosturismorural.class).getResultList();
		for(Alojamientosturismorural aTR: alojSTR) {
			// TODO Mejor hacer una b´suqueda enm MOngo
//			if (categorias.add(aTR.getCategoria())) {
//				//No está la categoria -> la añado a MongoDB
//				docCategoria = new Document()
//						.append("categoria", aTR.getCategoria());
//				db.getCollection("Categorias").insertOne(docCategoria);
//			}
			if ((docCategoria = db.getCollection("Categorias").find(Filters.eq("categoria", aTR.getCategoria() )).first())==null) {
				docCategoria = new Document()
						.append("categoria", aTR.getCategoria());
				db.getCollection("Categorias").insertOne(docCategoria);
				docCategoria = db.getCollection("Categorias").find(Filters.eq("categoria", aTR.getCategoria() )).first();
			}
			
			docAlojamiento = new Document()
					.append("nombre", aTR.getNombre())
					.append("tipo", aTR.getTipos().getDescripcion())
					.append("categoria", docCategoria.get("_id")) 
					.append("ubicacion", new Document()
							.append("direccion", aTR.getDireccion())
							.append("cp", aTR.getCp())
							.append("poblacion", aTR.getLocalidad())
							.append("provincia", aTR.getProvincias().getNombre()))
					.append("telefono", aTR.getTelefono());
			db.getCollection("Alojamientos").insertOne(docAlojamiento);
		}
		cliente.close();
		sesion.close();
	}
}
		