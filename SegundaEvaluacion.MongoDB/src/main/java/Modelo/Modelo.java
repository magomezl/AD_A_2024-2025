package Modelo;

import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Modelo {

	private static MongoClient cliente;
	private static MongoDatabase db;
	
	
	
	
	public static void main(String[] args) {
	
			conexionLocal();
//			conexionRemota();
			ArrayList<String> personajes = new ArrayList<String>(Arrays.asList("Personaje1S", "Personaje2S"));
			
			anadirLibro("LibroDesconocido", "novelaS", "Milan KunderaS", "ChecoS", 1946, personajes);
			cliente.close();

	}


	private static void anadirLibro(String titulo, String genero, String nombreAutor, String nacionalidadAutor, int nacimientoAutor, ArrayList<String> personajes) {
		Document doc = new Document()
				.append("titulo", titulo)
				.append("genero", genero)
				.append("autor", 
						new Document()
							.append("nombre", nombreAutor)
							.append("nacionalidad", nacionalidadAutor)
							.append("nacimiento", nacimientoAutor))
				.append("personajes", personajes);
				
			System.out.println("Hola");			
		db.getCollection("libros").insertOne(doc);
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
