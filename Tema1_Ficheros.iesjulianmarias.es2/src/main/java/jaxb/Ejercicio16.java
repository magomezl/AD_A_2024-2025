package jaxb;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jaxb.clasesEjercicio16.Autor;
import jaxb.clasesEjercicio16.Examen;
import utilidades.Utilidades;

public class Ejercicio16 {
	private static JAXBContext jC; 
	private static Examen examen; 
	private static ArrayList<Autor> autores;
	private static final String DOCUMENTO = "ejercicio16.xml";

	public static void main(String[] args) {
		unmarshalling();
		menu();
		marshalling();
	}
	private static void menu() {
		Scanner sc = new Scanner(System.in);
		Scanner scI = new Scanner(System.in);
		String idAutor, newET, newP;
		boolean salir = false;
		while(!salir) {
			System.out.println("\n1.Leer autores \n2.Añadir autor \n3.Modificar autor \n4.Eliminar autor \n5.Salir");
			try {
				System.out.print("Seleccione una opción: ");
				switch(sc.nextInt()) {
				case 1:
					System.out.println("LEYENDO AUTORES");
					leerAutores();
					break;
				case 2:
					System.out.println("AÑADIENDO AUTOR");
					System.out.println(anadirAutor(pedirDatosAutor())?"Autor añadido correctamente":"El autor NO se añadió");
					break;
				case 3:
					System.out.println("MODIFICANDO AUTOR");
					System.out.println("Id del autor a modificar: ");
					idAutor = scI.nextLine();
					System.out.println("Nueva entidad de trabajo: ");
					newET = scI.nextLine();
					System.out.println("Nuevo puesto: ");
					newP = scI.nextLine();
					System.out.println(modificarAutor(idAutor, newET, newP)?"Autor modificado correctamente":"El autor NO se modificó");
					break;
				case 4:
					System.out.println("ELIMINANDO AUTOR");
					System.out.println("Id del autor a eliminar: ");
					idAutor = scI.nextLine();
					System.out.println(eliminarAutor(idAutor)?"Autor eliminado correctamente":"El autor NO se eliminó");
					break;
				case 5:
					salir = true;
					break;
				default:
					System.out.println("Solo números entre 1 y 5");
					break;
				}
			}catch (InputMismatchException e) {
				System.out.println("Debes escribir un número");
				sc.next();
			}
		}
	}
	
	private static boolean eliminarAutor(String idAutor) {
		return autores.remove(localizarAutor(idAutor));
	}
	
	private static boolean modificarAutor(String idAutor, String newET, String newP) {
		Autor autor = localizarAutor(idAutor);
		if (autor!=null) {
			autor.setEntidadTrabajo(newET);
			autor.setPuesto(newP);
			return true;
		}
		return false;
	}
	
	private static Autor localizarAutor(String idAutor) {
		Iterator<Autor> it = autores.iterator();
		Autor autorEnCurso;
		while(it.hasNext()) {
			autorEnCurso = it.next(); 
			if (autorEnCurso.getId().equals(idAutor)) {
				return autorEnCurso;
			}
		}
		return null;
	}
	
	private static boolean anadirAutor(Autor autor) {
		return autores.add(autor);
	}
	
	private static Autor pedirDatosAutor() {
		Scanner sn = new Scanner(System.in);
		Autor autor = new Autor();
		System.out.println("Identificador: ");
		autor.setId(sn.nextLine());
		System.out.println("Nombre: ");
		autor.setNombre(sn.nextLine());
		System.out.println("Primer Apellido: ");
		autor.setApellido1(sn.nextLine());
		System.out.println("Segundo Apellido: ");
		autor.setApellido2(sn.nextLine());
		System.out.println("Entidad de Trabajo: ");
		autor.setEntidadTrabajo(sn.nextLine());
		System.out.println("Puesto: ");
		autor.setPuesto(sn.nextLine());
		return autor;
	}
	
	private static void leerAutores() {
		Iterator<Autor> it = autores.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	/**
	 * Carga en el javabean (clases creadas y etiquetadas) en xml
	 */
	public static void unmarshalling() {
		try {
			jC = JAXBContext.newInstance(Examen.class);
			Unmarshaller  uM= jC.createUnmarshaller();
			examen = (Examen) uM.unmarshal(new File(Utilidades.RUTA+DOCUMENTO));
			autores = examen.getListaAutor();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void marshalling() {
		try {
			Marshaller m = jC.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(examen, new File(Utilidades.RUTA+DOCUMENTO));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
