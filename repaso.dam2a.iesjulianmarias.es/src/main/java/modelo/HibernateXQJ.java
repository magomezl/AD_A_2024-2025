package modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.w3c.dom.Node;

import modelo.clasesHibernate.Ciudades;
import modelo.clasesHibernate.Idiomas;
import modelo.clasesHibernate.Paises;
import modelo.clasesHibernate.Practicareligiones;
import modelo.clasesHibernate.PracticareligionesId;
import modelo.clasesHibernate.Religiones;
import modelo.conexionesSingleton.CnnXQJ;

public class HibernateXQJ {

	private static Configuration cfg = new Configuration().configure();
	private static SessionFactory sf = cfg.buildSessionFactory();
	private static XQConnection xqc = CnnXQJ.getInstancia("admin", "admin").getCon();
	
	// Están completas las tablas Religiones, Paises y Ciudades, tenéis que completar el resto de tablas
	
	public static void main(String[] args) {
		try {
	
			Session sesion = sf.openSession();
			Transaction t = sesion.beginTransaction();

			if (traspasaReligiones(sesion)==-1 || traspasaPaises(sesion)==-1 || traspasaCiudades(sesion)==-1
					|| traspasaIdiomas(sesion)==-1 || traspasaIdiomasPaises(sesion)==-1 || traspasaPracticaReligiones(sesion)==-1) {
				t.rollback();
			}else {
				t.commit();
			}
			sesion.close();
			xqc.close();
			
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (PersistenceException e) {
			//Capturo esta excepción para que no tener problemas con los valores unique de las tablas 
		}
	}
	
	private static int traspasaPracticaReligiones(Session sesion) {
		try {
			String queryPractica = "for $practica in doc('EjerciciosRepaso/religiones.xml')//religiones_en_paises/practica\r\n"
					+ "	let $nombre_pais := doc('EjerciciosRepaso/religiones.xml')//paises/pais[@id_pais = $practica/@id_pais]/@nombre\r\n"
					+ "	let $nombre_religion := doc('EjerciciosRepaso/religiones.xml')//religiones/religion[@id_religion = $practica/@id_religion]/@denominacion\r\n"
					+ "	    return\r\n"
					+ "		    <practica pais=\"{$nombre_pais}\" religion=\"{$nombre_religion}\" practicantes=\"{$practica/@practicantes}\" /> "; 
			XQPreparedExpression exprPractica= xqc.prepareExpression(queryPractica);
			XQResultSequence resultPractica = exprPractica.executeQuery();
			while(resultPractica.next()) {
				
				Node nodo = resultPractica.getNode();
				//Obtengo pais y asigno idioma 
				
				String hql = "FROM Paises p WHERE p.nombre = :nombrePais";
				Query query = sesion.createQuery(hql, Paises.class);
				query.setParameter("nombrePais", nodo.getAttributes().getNamedItem("pais").getNodeValue());
				Paises pais = (Paises) query.uniqueResult();
				
				String hql2 = "FROM Religiones r WHERE r.nombre = :nombreReligion";
				Query query2 = sesion.createQuery(hql2, Religiones.class);
				query2.setParameter("nombreReligion", nodo.getAttributes().getNamedItem("religion").getNodeValue());
				Religiones religion = (Religiones) query2.uniqueResult();
								
				Practicareligiones practicaR = new Practicareligiones(new PracticareligionesId(pais.getIdPais(), religion.getIdReligion()), 
						religion, pais, Float.parseFloat(nodo.getAttributes().getNamedItem("practicantes").getNodeValue()));
				sesion.persist(practicaR);
			}
		}catch (PersistenceException e) {
			//Capturo esta excepción para que no tener problemas con los valores unique de las tablas
			e.printStackTrace();
			return -1;
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;
		
		
	}

	private static int traspasaIdiomasPaises(Session sesion) {
		try {
			String queryPais = " for $p in doc('EjerciciosRepaso/religiones.xml')//paises/pais \r\n"
					+ "    return \r\n"
					+ "        $p"; 
			XQPreparedExpression exprPais = xqc.prepareExpression(queryPais);
			XQResultSequence resultPais = exprPais.executeQuery();
			while(resultPais.next()) {
				
				Node nodo = resultPais.getNode();
				//Obtengo pais y asigno idioma 
				
				String hql = "FROM Paises p WHERE p.nombre = :nombrePais";
				Query query = sesion.createQuery(hql, Paises.class);
				query.setParameter("nombrePais", nodo.getAttributes().getNamedItem("nombre").getNodeValue());
				Paises pais = (Paises) query.uniqueResult();
				
				String hql2 = "FROM Idiomas i WHERE i.idioma = :nombreIdioma";
				Query query2 = sesion.createQuery(hql2, Idiomas.class);
				query2.setParameter("nombreIdioma", nodo.getAttributes().getNamedItem("idioma_oficial").getNodeValue());
				Idiomas idioma = (Idiomas) query2.uniqueResult();
				
				Set idiomas = pais.getIdiomases();
				//Muy importante hacer esta comprobación para evitar NullPointerException
				if (idiomas==null) {
					idiomas = new HashSet<>();
				}
				idiomas.add(idioma);
				pais.setIdiomases(idiomas);
				
				sesion.update(pais);
			}
		}catch (PersistenceException e) {
			//Capturo esta excepción para que no tener problemas con los valores unique de las tablas 
			return -1;
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	private static int traspasaIdiomas(Session sesion) {
		try {

			String queryIdioma = "for $p in doc(\"EjerciciosRepaso/religiones.xml\")/geografia/paises/pais\r\n"
					+ "	return data($p//@idioma_oficial)";


			XQPreparedExpression exprIdioma = xqc.prepareExpression(queryIdioma);
			XQResultSequence resulIdioma = exprIdioma.executeQuery();
			HashSet<String> idiomas = new HashSet<String>();
			while(resulIdioma.next()) {
				idiomas.add(resulIdioma.getAtomicValue());
			}
			for (String idi: idiomas) {
				Idiomas idioma = new Idiomas(idi);
				sesion.persist(idioma);
			}
		} catch (XQException e) {
			
			e.printStackTrace();
			return -1;
		}catch (PersistenceException e) {
			//Capturo esta excepción para que no tener problemas con los valores unique de las tablas 
			return -1;
		}
		return 0;
		
	}

	private static int traspasaReligiones(Session sesion) {
		try {
			String queryRel = " for $r in doc('EjerciciosRepaso/religiones.xml')//religiones/religion return \r\n"
					+ "						<religion denominacion='{$r/@denominacion}'/> \r\n"; 
			XQPreparedExpression exprRel = xqc.prepareExpression(queryRel);
			XQResultSequence resultRel = exprRel.executeQuery();
			while(resultRel.next()) {
				Node nodo = resultRel.getNode();
				Religiones religion = new Religiones(nodo.getAttributes().getNamedItem("denominacion").getNodeValue());
				sesion.persist(religion);
			}
		}catch (PersistenceException e) {
			//Capturo esta excepción para que no tener problemas con los valores unique de las tablas
			return -1;
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	

	private static int traspasaPaises(Session sesion) {
		try {
			String queryPais = " for $p in doc('EjerciciosRepaso/religiones.xml')//paises/pais \r\n"
					+ "    return \r\n"
					+ "        $p"; 
			XQPreparedExpression exprPais = xqc.prepareExpression(queryPais);
			XQResultSequence resultPais = exprPais.executeQuery();
			while(resultPais.next()) {
				Node nodo = resultPais.getNode();
				// <superficie> sería el nodo 0, su contenido el nodo 1, <km_linea_costa> sería el nodo 2, su contenido el nodo 3, y así sucesivamente
				
				Paises pais = new Paises(
						nodo.getAttributes().getNamedItem("nombre").getNodeValue(),
						Float.parseFloat(nodo.getAttributes().getNamedItem("num_habitantes").getNodeValue()),
						Float.parseFloat(nodo.getChildNodes().item(1).getTextContent()),
						Float.parseFloat(nodo.getChildNodes().item(3).getTextContent()),
						Float.parseFloat(nodo.getChildNodes().item(5).getTextContent()),
						Float.parseFloat(nodo.getChildNodes().item(7).getTextContent()), null, null, null);
				sesion.persist(pais);
			}
		}catch (PersistenceException e) {
			//Capturo esta excepción para que no tener problemas con los valores unique de las tablas 
			return -1;
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;

	}

	private static int traspasaCiudades(Session sesion) {
		try {
			String queryCity = "for $c in doc('EjerciciosRepaso/religiones.xml')//ciudades/ciudad\r\n"
					+ "let $p := doc('EjerciciosRepaso/religiones.xml')//paises/pais[@id_pais = $c/@pais]/@nombre\r\n"
					+ "return <ciudad \r\n"
					+ "    nombre=\"{$c/@nombre}\" \r\n"
					+ "    num_habitantes=\"{$c/@num_habitantes}\" \r\n"
					+ "    pais=\"{$p}\">\r\n"
					+ "    {$c/*}\r\n"
					+ "</ciudad>"; 
			XQPreparedExpression exprCity = xqc.prepareExpression(queryCity);
			XQResultSequence resultCity = exprCity.executeQuery();
			while(resultCity.next()) {
				Node nodo = resultCity.getNode();
				Paises pais = sesion.createQuery("from Paises where nombre='" + nodo.getAttributes().getNamedItem("pais").getNodeValue()+ "'", Paises.class).getResultList().getFirst();
				Ciudades city = new Ciudades(pais, 
						nodo.getAttributes().getNamedItem("nombre").getNodeValue(),
						Float.parseFloat(nodo.getAttributes().getNamedItem("num_habitantes").getNodeValue()),
						Float.parseFloat(nodo.getChildNodes().item(1).getTextContent()),
						Float.parseFloat(nodo.getChildNodes().item(3).getTextContent()),
						Float.parseFloat(nodo.getChildNodes().item(5).getTextContent()),
						Float.parseFloat(nodo.getChildNodes().item(7).getTextContent()));
						 
				sesion.persist(city);
			}
			
		}catch (PersistenceException e) {
			return -1;
			//Capturo esta excepción para que no tener problemas con los valores unique de las tablas 
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	
}
