package modelo;

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
import org.w3c.dom.Node;

import modelo.clasesHibernate.Ciudades;
import modelo.clasesHibernate.Paises;
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

			if (traspasaReligiones(sesion)==-1 || traspasaPaises(sesion)==-1 || traspasaCiudades(sesion)==-1) {
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
