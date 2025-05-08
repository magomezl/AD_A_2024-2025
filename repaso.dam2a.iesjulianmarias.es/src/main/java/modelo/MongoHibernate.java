package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import modelo.clasesHibernate.Ciudades;
import modelo.clasesHibernate.Idiomas;
import modelo.clasesHibernate.Paises;
import modelo.clasesHibernate.Practicareligiones;

public class MongoHibernate {
	private static Configuration cfg = new Configuration().configure();
	private static SessionFactory sf = cfg.buildSessionFactory();
	private static MongoClient cliente = MongoClients.create();;
	private static MongoDatabase mDB = cliente.getDatabase("geografia");;

	public static void main(String[] args) {
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		
		String hqlPaises = "from Paises";
		TypedQuery<Paises> consultaPaises = sesion.createQuery(hqlPaises, Paises.class);
		List<Paises> paises = consultaPaises.getResultList();
		
		for(Paises pais: paises) {
			Document docPais = new Document();
			// Consulta para obtener las ciudades de ese pais
			String hqlCiudadesPais = "from Ciudades where paises.idPais=" + pais.getIdPais();
			TypedQuery<Ciudades> consultaCiudadesPais = sesion.createQuery(hqlCiudadesPais, Ciudades.class);
			List<Ciudades> ciudades = consultaCiudadesPais.getResultList();
			//creo un List de documentos va a ser el array de documentos json ciudad 
			List<Document> listaCiudadesDoc = new ArrayList<Document>();
			for(Ciudades ciudad: ciudades) {
				Document docCiudad = new Document()
						.append("nombreCiudad", ciudad.getNombre())
						.append("num_habitantes", ciudad.getNumHabitantes());
				listaCiudadesDoc.add(docCiudad);
			}
			
			//Consulta para obtener las religiones del pais
			String hqlReligionesPais = "from Practicareligiones where paises.idPais=" + pais.getIdPais();
			TypedQuery<Practicareligiones> consultaReligionesPais = sesion.createQuery(hqlReligionesPais, Practicareligiones.class);
			List<Practicareligiones> religiones = consultaReligionesPais.getResultList();
			List<Document> listaReligionesDoc = new ArrayList<Document>();
			for(Practicareligiones religion: religiones) {
				Document docReligion = new Document()
						.append("nombreReligion", religion.getReligiones().getNombre())
						.append("practicantes", religion.getPracticantes());
				listaReligionesDoc.add(docReligion);
			}
			
			Set<Idiomas> idiomasP = pais.getIdiomases();
			List<String> ListaIdiomas = new ArrayList<String>();
			for(Idiomas id: idiomasP) {
				ListaIdiomas.add(id.getIdioma());
			}
			docPais.append("nombrePais", pais.getNombre())
				.append("num_habitantes", pais.getNumHabitantes())
				.append("idioma", ListaIdiomas)
				.append("ciudades", listaCiudadesDoc)
				.append("religiones", listaReligionesDoc);
			mDB.getCollection("datos").insertOne(docPais);	
		}

	}

}
