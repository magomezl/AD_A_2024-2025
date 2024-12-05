package ficheros;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Ejercicio5 {
	private final static String DOCTRABAJO_IN = "FicheroAleatorioNumeros.dat";
	private final static int TAMANIO = 4;

	private static RandomAccessFile rFile;
	
	public static void main(String[] args) {
		try {
			rFile = new  RandomAccessFile(new File(Ejercicio3.RUTA +DOCTRABAJO_IN ), "rw");
			escribir();
	
			menu();
			rFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no existe");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error de lectura/escritura");
			e.printStackTrace();
		}
	}

	public static void escribir() {
		
		try {
			for (int i=1; i<=100; i++) 
				rFile.writeInt(i);
		} catch (IOException e) {
			System.out.println("Error de lectura/escritura");
			e.printStackTrace();
		}
	}
	
	public static void leer() {
		
		try {
			rFile.seek(0);
			while(rFile.getFilePointer() != rFile.length()) 
				System.out.print(rFile.readInt() + "\t");
		}catch (IOException e) {
			System.out.println("Error de lectura/escritura");
			e.printStackTrace();
		}
	}
	/**
	 * Método que muestra la posición del elemento que ocupa el puesto lugar en el fichero
	 * @param lugar número de elemento a mostrar: 1 para primero, 2 para segundo...
	 */
	public static void consultar(int lugar) {
		
		try {
			// Calculo la posición
			int posicion = (lugar-1)*TAMANIO;
			// Compruebo que la posición sea correcta no superior al tamaño del fichero ni inferior a 0 
			if (posicion >= rFile.length() || posicion <0)
				System.out.println("La posición indicada no es correcta");
			else {
				rFile.seek(posicion);
				System.out.println("Valor en la posición " + lugar + ": " + rFile.readInt());
			}
		} catch (IOException e) {
			System.out.println("Error de lectura/escritura");
			e.printStackTrace();
		}
	}

	/**
	 * Método que añade un número al final del fichero
	 * @param valor número a añadir
	 */
	public static void anadirFinal(int valor) {

		try {
			rFile.seek(rFile.length());
			rFile.writeInt(valor);
		} catch (IOException e) {
			System.out.println("Error de lectura/escritura");
			e.printStackTrace();
		}
	}
	/**
	 * Método que modifica un número en una determinada posición
	 * @param valor nuevo número a escribir en la nueva posición 
	 * @param lugar número de elemento que se modificará
	 */
	public static void modificarValor(int valor, int lugar) {

		try {
			int posicion = (lugar-1)*TAMANIO;
			// Compruebo que la posición sea correcta no superior al tamaño del fichero ni inferior a 0 
			if (posicion >= rFile.length() || posicion <0)
				System.out.println("La posición indicada no es correcta");
			else {
				rFile.seek(posicion);
				rFile.writeInt(valor);
			}
		} catch (IOException e) {
			System.out.println("Error de lectura/escritura");
			e.printStackTrace();
		}
	}
	
	
	public static void menu() {
		Scanner sc = new Scanner(System.in);
		boolean salir = false;
		int opcion, lugar;

		while (!salir) {
			System.out.println("\n1. Leer fichero");
			System.out.println("2. Consultar");
			System.out.println("3. Añadir al final");
			System.out.println("4. Modificar valor");
			System.out.println("5. Salir");
			try {
				System.out.println("Escriba una de las opciones:");
				opcion = sc.nextInt();
				switch(opcion) {
				case 1:
					System.out.println("LEYENDO EL CONTENIDO DEL FICHERO");
					leer();
					break;
				case 2:
					System.out.println("CONSULTANDO UNA POSICIÓN DEL FICHERO");
					System.out.println("Indique la posición a consultar: ");
					consultar(sc.nextInt());
					break;
				case 3:
					System.out.println("AÑADIENDO ELEMENTO AL FINAL DEL FICHERO");
					System.out.println("Indique el valor a añadir: ");
					anadirFinal(sc.nextInt());
					break;
				case 4:
					System.out.println("MODIFICANDO EL VALOR DE UNA POSICIÓN");
					System.out.println("Indique la posición a modificar: ");
					lugar = sc.nextInt();
					System.out.println("Indique el nuevo valor: ");
					modificarValor(sc.nextInt(), lugar);
					break;
				case 5:
					salir = true;
					break;
				default:
					System.out.println("Solo números entre 1 y 5");
					break;
				}

			}catch(InputMismatchException e) {
				System.out.println("Debes escribir un número del 1 al 5");
				sc.next();
				
			}
		}
	}
}
