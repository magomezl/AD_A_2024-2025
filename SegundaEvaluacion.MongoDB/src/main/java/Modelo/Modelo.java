package Modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
//import com.mongodb.client.model.Filters.eq;
import java.util.Iterator;
import java.util.Random;

import org.bson.BsonObjectId;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;

public class Modelo {

	private static MongoClient cliente;
	private static MongoDatabase db;
	
	
	
	
	public static void main(String[] args) {
	
//			conexionLocal();
			conexionRemota();
			ArrayList<String> personajes = new ArrayList<String>(Arrays.asList("Filomeno", "Juana"));
//			
			anadirLibroAutorEmbebido("Filomeno, a mi pesar", "novela", "Gonzalo Torrente Ballester", "Español", 1910, personajes);
			anadirLibroAutorReferenciado("Los gozos y las sombras", "novela", "Gonzalo Torrente Ballester", "Español", 1910, personajes);
			
//			mostrarLibros("La conjura de los necios", "Jonh Kennedy Toole");
			
//			mostrarLibros("ciencia ficción");
			System.out.println("\n\n TODOS ANTES");
			mostrarLibros();
//			actualizarPrecios(50);
//			System.out.println("\n\n TODOS DESPUES");
//			mostrarLibros();
			cliente.close();

	}


	private static void anadirLibroAutorEmbebido(String titulo, String genero, String nombreAutor, String nacionalidadAutor, 
			int nacimientoAutor, ArrayList<String> personajes) {
		Document doc = new Document()
				.append("titulo", titulo)
				.append("genero", genero)
				.append("autor", 
						new Document()
							.append("nombre", nombreAutor)
							.append("nacionalidad", nacionalidadAutor)
							.append("nacimiento", nacimientoAutor))
				.append("personajes", personajes);
		db.getCollection("libros").insertOne(doc);
	}

	private static void anadirLibroAutorReferenciado(String titulo, String genero, String nombreAutor, String nacionalidadAutor, int nacimientoAutor, ArrayList<String> personajes) {
		Document docAutor = null;
		ObjectId id;
		docAutor = db.getCollection("autores").find(Filters.and(
				Filters.eq("nombre", nombreAutor), 
				Filters.eq("nacionalidad", nacionalidadAutor), 
				Filters.eq("nacimiento", nacimientoAutor))).first();
		
		// Si no existe el autor lo añade en la colección autores
		if (docAutor==null) {
			docAutor = new Document()
					.append("nacimiento", nacimientoAutor)
					.append("nacionalidad", nacionalidadAutor)
					.append("nombre", nombreAutor);
			db.getCollection("autores").insertOne(docAutor);
		}
		id = docAutor.getObjectId("_id");
		
		
		Document docLibro = new Document()
				.append("titulo", titulo)
				.append("genero", genero)
				.append("autor", id) 
				.append("personajes", personajes);
		db.getCollection("libros").insertOne(docLibro);
	}
	
	private static void mostrarLibros() {
		FindIterable<Document> fitL = db.getCollection("libros").find();
		Iterator<Document> itL = fitL.iterator();
		while(itL.hasNext()) {
			System.out.println(itL.next().toString());
		}
	}
	
	private static void mostrarLibros(String genero) {
		FindIterable<Document> fitL = db.getCollection("libros").find(Filters.eq("genero", genero));
		Iterator<Document> itL = fitL.iterator();
		while(itL.hasNext()) {
			System.out.println(itL.next().toString());
		}
	}
	
	private static void mostrarLibros(String titulo, String nombreAutor) {
		HashSet<ObjectId> idsAutor = new HashSet<ObjectId>();
		
		//Obtengo el ObjectId del autor
		FindIterable<Document> fitA = db.getCollection("autores").find(Filters.eq("nombre", nombreAutor)); 
		Iterator<Document> itA = fitA.iterator();
		while(itA.hasNext()) {
			idsAutor.add(itA.next().getObjectId("_id"));
		}
		// Faltarían dos opciones más: autor nombre directamente y autro id 
		FindIterable<Document> fitL = db.getCollection("libros").find(
				Filters.or(
					Filters.and(
						Filters.eq("titulo", titulo), 
						Filters.eq("autor.nombre", nombreAutor)
					),
					Filters.and(
						Filters.eq("titulo", titulo), 
						Filters.in("autor", idsAutor)
					)
				));
		Iterator<Document> itL = fitL.iterator();
		while(itL.hasNext()) {
			System.out.println(itL.next().toString());
		}
	}
	
	private static void ponerPrecios() {
			db.getCollection("libros").updateMany(Filters.exists("precio", false), Updates.set("precio", new Random().nextDouble(25-10+1)+10));
	}
		
		// añadirles una propiedad precio a la que le asignamos un valor aleatorio entre 10 y 25
		
		
	
	
	private static void actualizarPrecios(float porcentaje) {
		// Localizar los libros con precio y mostrarlos
		// actualizar la propiedad precio en en el porcentaje indicado
		db.getCollection("libros").updateMany(Filters.exists("precio", true), Updates.mul("precio", 1 + porcentaje / 100));
				
				
		
		
	}
	
	private static void conexionRemota() {
		String connectionString = "mongodb+srv://invitado2:invitado2@cluster0.pgow3.mongodb.net/?retryWrites=true&w=majority";
		ServerApi serverApi = ServerApi.builder()
				.version(ServerApiVersion.V1)
				.build();
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(connectionString))
				.serverApi(serverApi)
				.build();
		// Create a new client and connect to the server
		cliente = MongoClients.create(settings);
		db = cliente.getDatabase("biblioteca");
	   }


	private static void conexionLocal() {
		try {
			cliente = MongoClients.create();
			db = cliente.getDatabase("biblioteca");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
