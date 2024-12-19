package modelo;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;


import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import clasesHibernate.Departamentos;
import clasesHibernate.Empleados;
import jakarta.persistence.NoResultException;


public class Modelo {
	
	private static final Configuration cfg = new Configuration().configure();
	private static final SessionFactory sf = cfg.buildSessionFactory();
	//Elimino la propiedad sesion para tener más control sobre las sesiones que abro
	//private static Session sesion;
	
	
	
	public static void main(String[] args) {
		
//		borrarDpto(1);
//		modificarDpto(20, "Ventolin", "Soria");
//		List<Departamentos> dptos = listarDptosNombre("Fisioterápia");
//		List<Departamentos> dptos = listarDptos("Salamanca");
//		List<Departamentos> dptos = listarDptos();
//		for(Departamentos dpto: dptos) {
//			System.out.println(dpto);
//		}
//		System.out.println(listarDptos(2));

//		anadirDpto("Música", "Palencia");

//		anadirEmpleado("Pablo", "Sanchez", "Quintana", "Mecánica");
		
		

//		eliminarEmpleado("Alvarita", "Sanchez", "Quintana");

//		ArrayList<Empleados> empleados =  listarEmpleados();
//		ArrayList<Empleados> empleados = listarEmpleados("Mates");
//		for(Empleados empl: empleados) {
//			System.out.println(empl);
//			
//		}
		
		modificarEmpleado("Anselmo", "Garcia", "Nieto", "Aerobic");

		
		
	}
	
	/**
	 * CREATE
	 * 
	 * Comprobar si existe un empleado con el mismo nombre y apellidos, si es así no hace nada
	 * si no, comprobar existencia del dpto, si el departamento no existe, lo almacenamos pidiendo los datos que sean necesarios,
	 * Pueden existir varios departamentos con el mismo nombre, los mostramos y pedimos al usuario que seleccione uno 
	 * y se almacena el empleado en ese departamento 
	  
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param dpto
	 */
	private static void anadirEmpleado(String nombre, String apellido1, String apellido2, String dpto) {
		Scanner sc = new Scanner(System.in);
		
		Departamentos dptoObj;
		Empleados empObj = null;

		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		if (existeEmpleado(sesion, nombre, apellido1, apellido2)!=0) {
			sesion.close();
			System.out.println("Ya existe el empleado");
		}else {
			List<Departamentos> dptos = listarDptosNombre(sesion, dpto);
			if (dptos.isEmpty()) {
				System.out.println("No existe el departamento indicado, indique la localidad del mismo para incluirlo:");
				String localidad = sc.next();
				//Añadimos constructores a las clases generadas por Hibernate
				dptoObj = new Departamentos(dpto, localidad);
				sesion.persist(dptoObj);
				empObj = new Empleados(dptoObj, nombre, apellido1, apellido2);		
				// TODO También lo podemos hacer persistiendo un objeto Departamento
			}else if (dptos.size()>1) {
				//Elegir departamento
				System.out.println("Departamentos con el nombre seleccionado:");
				for(Departamentos dptoObjbis: dptos) {
					System.out.println(dptoObjbis);
				}
				System.out.println("Indique el código del departamento al que desea añadir al empleado:");
				String idDpto = sc.next();
				dptoObj = sesion.get(Departamentos.class, Integer.valueOf(idDpto));
				empObj = new Empleados(dptoObj, nombre, apellido1, apellido2);
			}else if (dptos.size()==1) {
				dptoObj = dptos.getFirst();
				empObj = new Empleados(dptoObj, nombre, apellido1, apellido2);
			}
			sesion.persist(empObj);
			t.commit();
			sesion.close();
			sc.close();
		}
	}

	/**
	 * UPDATE
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param dptoNew
	 */
	private static void modificarEmpleado(String nombre, String apellido1, String apellido2, String dptoNew) {
		Scanner sc = new Scanner(System.in);
		Departamentos dptoObj = null; 
		Empleados empObj;
		
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Integer idEmp; 
		if ((idEmp = existeEmpleado(sesion, nombre, apellido1, apellido2))==0) {
			sesion.close();
			System.out.println("No existe el empleado que pretende modificar");
		}else {
			List<Departamentos> dptos = listarDptosNombre(dptoNew);
			if (dptos.isEmpty()) {
				System.out.println("No existe el departamento indicado, indique la localidad del mismo para incluirlo:");
				String localidad = sc.next();
				dptoObj = new Departamentos(dptoNew, localidad);
				sesion.persist(dptoObj);
			}else if (dptos.size()>1) {
				//Elegir departamento
				System.out.println("Departamentos con el nombre seleccionado:");
				for(Departamentos dptoObjbis: dptos) {
					System.out.println(dptoObjbis);
				}
				System.out.println("Indique el código del departamento al que desea añadir al empleado:");
				String idDpto = sc.next();
				//Recuperamos el departamento
				dptoObj = sesion.get(Departamentos.class, Integer.valueOf(idDpto));
			}else if (dptos.size()==1) {
				dptoObj = dptos.getFirst();
			}
			empObj = sesion.get(Empleados.class, idEmp);
			empObj.setDepartamentos(dptoObj);
			sesion.merge(empObj);
			t.commit();
			sesion.close();
			sc.close();
		}
	}
	
	/**
	 * RETRIEVE
	 * @return
	 */

	private static ArrayList<Empleados> listarEmpleados() {
		Session sesion = sf.openSession();
		List<Empleados> empleados = sesion.createQuery("FROM Empleados", Empleados.class)
				.getResultList();
		sesion.close();
		return (ArrayList<Empleados>) empleados;
	}

	private static ArrayList<Empleados> listarEmpleados(String dpto) {
		Session sesion = sf.openSession();
		List<Empleados> empleados = sesion.createQuery("from Empleados e join fetch e.departamentos where e.departamentos.dnombre = :dptoName", Empleados.class)
				.setParameter("dptoName", dpto)
				.getResultList();
		sesion.close();
		return (ArrayList<Empleados>) empleados;
	}
	/**
	 * Recibe sesión del método anfitrión
	 * @param sesion
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @return
	 */
	private static ArrayList<Empleados> listarEmpleados(Session sesion, String nombre, String apellido1, String apellido2) {
		String hql = "FROM Empleados e WHERE e.nombre = :nombre AND e.apellido1 = :apellido1 AND "
				+ "e.apellido2 = :apellido2";
		List<Empleados> empleados = sesion.createQuery(hql, Empleados.class)
				.setParameter("nombre", nombre)
				.setParameter("apellido1", apellido1)
				.setParameter("apellido2", apellido2)
				.getResultList();
		return (ArrayList<Empleados>) empleados;
	}
		
	
	/**
	 * Comprueba si el empleado existe en la db. Si es así devuelve su id. Si no, devuelve 0
	 * @param sesion: se pasa la sesion del método pricipal. No se puede abrir una sesion dentro de otra.
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @return 0 si no existe en empleado en la db, su id si existe.
	 */
	private static Integer existeEmpleado(Session sesion, String nombre, String apellido1, String apellido2) {

		try {
			Empleados emp = null;
			emp = sesion.createQuery("from Empleados where nombre= :nombre and "
					+ " apellido1= :apellido1 and apellido2 = :apellido2", Empleados.class)
					.setParameter("nombre", nombre)
					.setParameter("apellido1", apellido1)
					.setParameter("apellido2", apellido2)
					.getResultList()
					.get(0);
			int id = emp.getId();
			return id;
		}catch (IndexOutOfBoundsException e) {
			return 0;
		}
	}

	/**
	 * DELETE
	 * 
	 * Elimina el empleado y si el departamento al que pertenecía queda vacío pregunta si se desea eliminar
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 */
	private static void eliminarEmpleado(String nombre, String apellido1, String apellido2) {
		Scanner sc = new Scanner(System.in);
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		
		List<Empleados> empleados = listarEmpleados(sesion, nombre, apellido1, apellido2); 
		
		if (empleados.isEmpty()) {
			System.out.println("No existe el empleado que desea borrar");
			
		}else {
			Departamentos dptoEmpleado = empleados.get(0).getDepartamentos();
			Long empleadosDpto = (Long) sesion.createQuery("SELECT count(e) FROM Empleados e WHERE e.departamentos = :dpto", Long.class)
					.setParameter("dpto", dptoEmpleado)
					.getSingleResult();
			if (empleadosDpto==1) {
				System.out.println("El departamento " +  dptoEmpleado.getDnombre() + " no tiene más empleados.");
                System.out.print("¿Quieres eliminar ese departamento? (S/N): ");
                sc = new Scanner(System.in);
                String respuesta = sc.nextLine().trim().toLowerCase();
                if (respuesta.equals("s")) {
                	sesion.remove(dptoEmpleado);
                }
			}
			sesion.remove(empleados.getFirst());
			t.commit();
			sesion.close();
		}
	}

	
	/******************************************
	 * CRUD DEPARTAMENTOS
	 */

	/**
	 * CREATE
	 * @param nombre
	 * @param localidad
	 */
	private static void anadirDpto(String nombre, String localidad) {
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dpto = new Departamentos(nombre, localidad, null);
		sesion.persist(dpto);
		t.commit();
		sesion.close();
	}
	
	/**
	 * RETRIEVE
	 * Para consultar no necesitamos comenzar una transacción
	 * @return
	 */
	private static List<Departamentos> listarDptos() {
		Session sesion = sf.openSession();
		List<Departamentos> dptos = sesion.createQuery("from Departamentos", Departamentos.class)
			.getResultList();
		sesion.close();
		return dptos;
	}
	
	private static Departamentos listarDptos(int dptoNum) {
		Session sesion = sf.openSession();
		Departamentos dptoObj = null;
		try {
			dptoObj= sesion.createQuery("from Departamentos where deptNo= :dpto", Departamentos.class)
					.setParameter("dpto", dptoNum)
					.getSingleResult();
		}catch (NoResultException e) {} 
		sesion.close();
		return dptoObj;
	}
	
	private static List<Departamentos> listarDptos(String localidad) {
		Session sesion = sf.openSession();
		List<Departamentos> dptos =  sesion.createQuery("from Departamentos where loc='" + localidad + "'", Departamentos.class)
				.getResultList();
		sesion.close();
		return dptos;
	}
	
	private static List<Departamentos> listarDptosNombre(String nombre) {
		Session sesion = sf.openSession();
		List<Departamentos> dptos =  sesion.createQuery("from Departamentos where dnombre='" + nombre + "'", Departamentos.class)
				.getResultList();
		sesion.close();
		return dptos; 
	}

	/**
	 * Método que devuelve los departamentos con un determinado nombre y utiliza la sesion del método anfitrion
	 * @param sesion
	 * @param nombre
	 * @return
	 */
	private static List<Departamentos> listarDptosNombre(Session sesion, String nombre) {
		List<Departamentos> dptos =  sesion.createQuery("from Departamentos where dnombre='" + nombre + "'", Departamentos.class)
				.getResultList();
		return dptos; 
	}

	
	/**
	 * UPDATE
	 * @param idDpto
	 * @param nombreNew
	 * @param localidadNew
	 */
	private static boolean modificarDpto(int idDpto, String nombreNew, String localidadNew) {
		boolean flag = false;
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dpto = sesion.get(Departamentos.class, idDpto);
		if (dpto!=null) {
			if (nombreNew!=null) {
				dpto.setDnombre(nombreNew);
				flag = true;
			}
			if (localidadNew!=null) {
				dpto.setLoc(localidadNew);
				flag = true;
			}
			sesion.merge(dpto);
			t.commit();
		}
		sesion.close();
		return flag;
	}
	
	/**
	 * DELETE
	 * @param dptoNum
	 * @return
	 */
	private static boolean borrarDpto(int dptoNum) {
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dpto = sesion.get(Departamentos.class, dptoNum);
		if (dpto!=null) {
			if (JOptionPane.showConfirmDialog(null,"¿Esta seguro de querer eliminar el dpto " + dpto.getDnombre() + "?")==JOptionPane.YES_OPTION) {
				sesion.remove(dpto);
				t.commit();
				sesion.close();
				return true;
			}
		}
		sesion.close();
		return false;
	}
	

}
