package ficheros.ejercicio6;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;

import ficheros.Ejercicio3;

public class Ejercicio6 {
	private final static String DOCTRABAJO_IN = "FicheroPersonasSerializado.dat";
	private static Scanner sc = new Scanner(System.in);
	private static ObjectOutputStream oOS; 
	
	public static void inicializar() {
		try {
			File file = new File(Ejercicio3.RUTA+DOCTRABAJO_IN);
			if (file.exists() && file.length()>0) {
				oOS = new MyObjectOutputStream(new FileOutputStream(file, true));
			}else {
				oOS = new ObjectOutputStream(new FileOutputStream(file, true));
			}
		} catch (IOException e) {
			System.out.println("Error al abrir el fichero");
			e.printStackTrace();
		}
	}
	/**
	 * Pide por teclado los datos de la persona
	 * @return un objeto de la clase Persona 
	 */
	public static Persona obtenerDatos() {
		Persona persona = new Persona();
		System.out.println("DATOS DEL USUARIO\n\tNombre: ");
		persona.setNombre(new StringBuilder(sc.nextLine()));
		System.out.println("\tPrimer Apellido: ");
		persona.setApellido1(new StringBuilder(sc.nextLine()));
		System.out.println("\tSegundo Apellido: ");
		persona.setApellido2(new StringBuilder(sc.nextLine()));
		System.out.println("\tFecha de Nacimiento (dd-MM-yyyy): ");
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			persona.setNacimiento(formatter.parse(sc.nextLine()));
		} catch (ParseException e) {
			System.out.println("Error en el formato de la fecha");
			e.printStackTrace();
		}
		return persona;
	}
	/**
	 * Escribe un objeto de la clase Persona en un stream de salida
	 * @param persona
	 * 		objeto a escribir
	 */
	public static void escribirObjeto(Persona persona) {

		try {
			oOS.writeObject(persona);
		} catch (IOException e) {
			System.out.println("Error al abrir el fichero para escritura");
			e.printStackTrace();
		}
	}
	
	
	public static void leerObjetos() {
		try {
			ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(new File(Ejercicio3.RUTA+DOCTRABAJO_IN)));
			try {
				Persona persona = new Persona();
				while(true) {
					persona = (Persona) oIS.readObject();
					System.out.println(persona);
				}
			} catch (ClassNotFoundException e) {
				System.out.println("Error de lectura");
				e.printStackTrace();
			} catch (EOFException e) {
				
			}finally {
				oIS.close();
			}
		} catch (IOException e) {
			System.out.println("Error al abrir el fichero para lectura");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		inicializar();
		escribirObjeto(obtenerDatos());
		escribirObjeto(obtenerDatos());
		leerObjetos();
		try {
			oOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
