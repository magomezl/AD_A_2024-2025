package modelo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import clasesHibernate.Departamentos;
import jakarta.persistence.TypedQuery;

public class Modelo {
	private static final Configuration cfg = new Configuration().configure();
	private static final SessionFactory sf = cfg.buildSessionFactory();
	
		
	
	
	public static void main(String[] args) {
		//anadirDpto("Personal", "Zamora");
		System.out.println("MUESTRO TODOS");
		ArrayList<Departamentos> dptos = listarDptos();
		for(Departamentos dpto: dptos) {
			System.out.println(dpto);
		}
		System.out.println("MUESTRO LOS DE PAMPLONA");
		dptos = listarDptos("Pamplona");
		for(Departamentos dpto: dptos) {
			System.out.println(dpto);
		}
		System.out.println("MUESTRO LOS DE PERSONAL");
		dptos = listarDptosNombre("Personal");
		for(Departamentos dpto: dptos) {
			System.out.println(dpto);
		}
		System.out.println("MUESTRO EL 45");
		System.out.println(listarDptos(45));
		
		
		System.out.println(modificadDpto(40, null, "Zamora")?"Dpto modificado con éxito":"Dpto NO modificado");

	}
	/**
	 * 
	 * @param id
	 * @param nuevoNombre
	 * @param nuevaLocalidad
	 * @return true si he modificado el departamento, false en caso contrario
	 */
	
	private static Boolean modificadDpto(int id, String nuevoNombre, String nuevaLocalidad) {
		Boolean flag = false;
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dpto = sesion.get(Departamentos.class, id);
		if(nuevoNombre!=null) {
			dpto.setDnombre(nuevoNombre);
			flag = true;
		}
		if (nuevaLocalidad!=null) {
			dpto.setLoc(nuevaLocalidad);
			flag = true;
		}
		sesion.merge(dpto);
		t.commit();
		sesion.close();
		return flag;
	}

	private static ArrayList<Departamentos> listarDptos() {
		Session sesion = sf.openSession();
		String hql = "from Departamentos";
		TypedQuery<Departamentos> consulta =  sesion.createQuery(hql, Departamentos.class);
		ArrayList<Departamentos> dptos = (ArrayList<Departamentos>) consulta.getResultList();
		sesion.close();
		return dptos;
	}
	
	private static ArrayList<Departamentos> listarDptos(String localidad) {
		Session sesion = sf.openSession();
		String hql = "from Departamentos where loc='" + localidad +"'";
		TypedQuery<Departamentos> consulta =  sesion.createQuery(hql, Departamentos.class);
		ArrayList<Departamentos> dptos = (ArrayList<Departamentos>) consulta.getResultList();
		sesion.close();
		return dptos;
	}
	
	private static Departamentos listarDptos(int dptoId) {
		Session sesion = sf.openSession();
		String hql = "from Departamentos where deptNo=" + dptoId ;
		TypedQuery<Departamentos> consulta =  sesion.createQuery(hql, Departamentos.class);
		Departamentos dpto =consulta.getSingleResult();
		sesion.close();
		return dpto;
	}
	
	private static ArrayList<Departamentos> listarDptosNombre(String nombre) {
		Session sesion = sf.openSession();
		String hql = "from Departamentos where dnombre='" + nombre +"'";
		TypedQuery<Departamentos> consulta =  sesion.createQuery(hql, Departamentos.class);
		ArrayList<Departamentos> dptos = (ArrayList<Departamentos>) consulta.getResultList();
		sesion.close();
		return dptos;
	}
	
	

	private static void anadirDpto(String dptoNombre, String dptoLocalidad) {
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dpto = new Departamentos(dptoNombre, dptoLocalidad, null);
		sesion.persist(dpto);
		t.commit();
		sesion.close();
	}
	
	
	

}
