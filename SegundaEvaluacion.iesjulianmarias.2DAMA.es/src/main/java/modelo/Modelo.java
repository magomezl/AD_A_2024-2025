package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import clasesHibernate.Departamentos;
import clasesHibernate.Empleados;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class Modelo {
	private static final Configuration cfg = new Configuration().configure();
	private static final SessionFactory sf = cfg.buildSessionFactory();
	
		
	
	
	public static void main(String[] args) {
		//anadirDpto("Personal", "Zamora");
//		System.out.println("MUESTRO TODOS");
//		ArrayList<Departamentos> dptos = listarDptos();
//		for(Departamentos dpto: dptos) {
//			System.out.println(dpto);
//		}
//		System.out.println("MUESTRO LOS DE PAMPLONA");
//		dptos = listarDptos("Pamplona");
//		for(Departamentos dpto: dptos) {
//			System.out.println(dpto);
//		}
//		System.out.println("MUESTRO LOS DE PERSONAL");
//		dptos = listarDptosNombre("Personal");
//		for(Departamentos dpto: dptos) {
//			System.out.println(dpto);
//		}
//		System.out.println("MUESTRO EL 45");
//		System.out.println(listarDptos(45));
		
	//	borrarDpto("Personal");
//		borrarDpto(40);
		
		//System.out.println(modificadDpto(40, null, "Zamora")?"Dpto modificado con éxito":"Dpto NO modificado");
		anadirEmpleado("Almudena", "Alonso", "Gil", "Control");
		
		System.out.println("Id del empleado Rosa Gil Gil " + buscarEmpleados("Rosa", "Gil", "Gil"));
		
		System.out.println("Empleados de todos los departamentos");
		ArrayList<Empleados> empleados = buscarEmpleados();
		for(Empleados emp: empleados) {
			System.out.println(emp);
		}
		
		System.out.println("\n\nEmpleados del departamento Control");
		ArrayList<Empleados> empleados2 = buscarEmpleados("Control");
		for(Empleados emp: empleados) {
			System.out.println(emp);
		}
		
		
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
	 * @param dptoName
	 * @return
	 */
	
	private static boolean anadirEmpleado(String nombre, String apellido1, String apellido2, String dptoName) {
		Scanner sn = new Scanner(System.in);
		ArrayList<Departamentos> dptos = listarDptosNombre(dptoName);
		boolean flag = false;
		Departamentos dpto = null;
		Empleados emp = null;
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		if (buscarEmpleados(nombre, apellido1, apellido2)!=0) {
			System.out.println("El empleado ya existe en la db");
			return false; 
		}else {
			if (dptos.size()==0) {
				//El departamento no existe
				System.out.println("¿Desea incluir el departamento " + dptoName +  " en la base de datos? (S/N): ");
				String localidad =null;
				if (sn.next().toLowerCase().equals("s")) {
					System.out.println("Localidad: ");
					localidad = sn.next();
					dpto = new Departamentos(dptoName, localidad, null);
					sesion.persist(dpto);
					Integer idGenerado = (Integer) sesion.getIdentifier(dpto);
					//Integer idGenerado = (Integer) sesion.save(dpto);

					// TODO Aquí no --> Hacer comprobación de que el empleado no existe ya en la db. Posibilidad de rollback
					emp = new Empleados(sesion.get(Departamentos.class, idGenerado), nombre, apellido1, apellido2);
					flag = true;
				}
			}else if (dptos.size()==1) {
				dpto = dptos.get(0);
				emp = new Empleados(dpto, nombre, apellido1, apellido2);
				flag = true;
			}else {
				int i = 1;
				for(Departamentos dep: dptos) {
					System.out.println(i++ + ".-" + dep );
				}
				System.out.println("¿En cuál de los siguientes departamentos desea incorporar al empleado? (Indique número):");
				int opcion = sn.nextInt();
				if (opcion>=1 && opcion<=dptos.size()) {
					emp = new Empleados(sesion.get(Departamentos.class, opcion-1), nombre, apellido1, apellido2);
					flag = true;
				}
			}
			if (emp!=null) {
				sesion.persist(emp);
				t.commit();
			}
		}
		sesion.close();
		return flag;
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
		System.out.println("¿Desea elimninar el departamento " + dpto.getDnombre() + " situado en " + dpto.getLoc() + "? (S/N): " );
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

	
	
	
	// *Cambiar empleado de dpto 
	// *Eliminar empleado. Comprobar que el dpto no tiene más empleados, si es así se da la opción de borrar el dpto
	// *Listar Empleados con nombre de departamento. Sobrecargar método para que al pasar nombre de dpto
	//    se muestren empleados del mismo 
		
	/**
	 * Localiza un empleado en la db y retorna su id, 0 si no existe
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @return
	 */
	private static int buscarEmpleados(String nombre, String apellido1, String apellido2) {
		int valorRetornado = 0;
		Session sesion = sf.openSession();
		String hql = "from Empleados where nombre = '" + nombre + "' and  apellido1 = '" + 
				apellido1 + "' and apellido2 = '" + apellido2 +"'";
		TypedQuery<Empleados> consulta =  sesion.createQuery(hql, Empleados.class);
		ArrayList<Empleados> empleados = (ArrayList<Empleados>) consulta.getResultList();
		if ( empleados.size()>0) {
				Empleados emp = empleados.get(0);
				valorRetornado = emp.getId();
		}
		sesion.close();
		return valorRetornado;
	}
	
	/**
	 * Devuelve todos los empleados de la db
	 * @return
	 */
	
	private static ArrayList<Empleados> buscarEmpleados() {
		ArrayList<Empleados> valorRetornado = null;
		Session sesion = sf.openSession();
		String hql = "from Empleados"; 
		TypedQuery<Empleados> consulta =  sesion.createQuery(hql, Empleados.class);
		valorRetornado = (ArrayList<Empleados>) consulta.getResultList();
		return valorRetornado;
	}
	
	/**
	 * Devuelve los empleados del departamento que se pasa como parámetro
	 * @param dpto
	 * @return
	 */
	private static ArrayList<Empleados> buscarEmpleados(String dpto) {
		
		//TODO revisar consulta
		ArrayList<Empleados> valorRetornado = null;
		Session sesion = sf.openSession();
		String hql = "from Empleados where departamentos.dnombre='" + dpto + "'"; 
		TypedQuery<Empleados> consulta =  sesion.createQuery(hql, Empleados.class);
		valorRetornado = (ArrayList<Empleados>) consulta.getResultList();
		return valorRetornado;
	}
	
	private static boolean modificarEmpleado(String nombre, String apellido1, String apellido2, String dptoNameNew) {	
		
		return false;
	}
	
}
