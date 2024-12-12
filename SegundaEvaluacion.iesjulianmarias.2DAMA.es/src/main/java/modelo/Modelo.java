package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
//		System.out.println("MUESTRO LOS DE PAMPLONA");
//		dptos = listarDptos("Pamplona");
//		for(Departamentos dpto: dptos) {
//			System.out.println(dpto);
//		}
		System.out.println("MUESTRO LOS DE PERSONAL");
		dptos = listarDptosNombre("Personal");
		for(Departamentos dpto: dptos) {
			System.out.println(dpto);
		}
//		System.out.println("MUESTRO EL 45");
//		System.out.println(listarDptos(45));
		
	//	borrarDpto("Personal");
		borrarDpto(40);
		
		//System.out.println(modificadDpto(40, null, "Zamora")?"Dpto modificado con éxito":"Dpto NO modificado");

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
	
	private static boolean borrarDpto(String dptoNombre) {
		Scanner sc = new Scanner(System.in);
		boolean flag= false;
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		String hql = "from Departamentos where dnombre='" + dptoNombre +"'";
		TypedQuery<Departamentos> consulta =  sesion.createQuery(hql, Departamentos.class);
		ArrayList<Departamentos> dptos = (ArrayList<Departamentos>) consulta.getResultList();
		System.out.println("Hay " + dptos.size() + " departamentos de " +  dptoNombre);
		for (Departamentos dpto: dptos) {
			System.out.println("¿Desea elimninar el departamento " + dpto.getDnombre() + " situado en " + dpto.getLoc() + "? (S/N):" );
			if (sc.next().toLowerCase().equals("s")) {
				sesion.remove(dpto);
				System.out.println("Departamento eliminado con éxito");
				flag = true;
			}
		}
		t.commit();
		sesion.close();
		sc.close();
		return flag;
	}
	

	private static boolean borrarDpto(int dptoId) {
		Scanner sn = new Scanner(System.in);
		boolean flag= false;
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dpto = sesion.get(Departamentos.class, dptoId);
		System.out.println("¿Desea elimninar el departamento " + dpto.getDnombre() + " situado en " + dpto.getLoc() + "? (S/N):" );
		if (sn.next().toLowerCase().equals("s")) {
			sesion.remove(dpto);
			System.out.println("Departamento eliminado con éxito");
			flag = true;
		}
		t.commit();
		sesion.close();
		sn.close();
		return flag;
	}

	/**
	 * dpto puede corresponderse con varios. Preguntar cuál mostrando los dptos con ese nombre
	 * dpto no existe. Confirmar que es correcto, si lo es, lo añadimos preguntando la localidad
	 * 
	 * Añadir el empleado al dpto. 
	 * 
	 * 
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param dpto
	 * @return
	 */
	
	private static boolean anadirEmpleado(String nombre, String apellido1, String apellido2, String dpto) {
		
	}
	
	
		
		
	
}
