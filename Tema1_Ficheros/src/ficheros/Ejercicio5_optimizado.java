package ficheros;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Ejercicio5_optimizado {
	private final static String DOCTRABAJO_IN = "FicheroAleatorioNumerosOptimizado.dat";
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
	
	/**
	 * Escribe el números del 1 al 100 en un fichero de acceso aleatorio o modifica el valor de una
	 *  posición determinada (ambos valores, posición y nuevo valor, se pasan como parámetros) o añade 
	 *  al final del fichero un valor que se pasa como parámetro. 
	 * @param valores
	 * 		si vacio, inicializa el fichero con valores del 1 al 100
	 * 		si tiene un único valor, se añade al final
	 * 		si tiene dos valores, el primero es tomado como la posición a sobreescribir y el segundo como el nuevo valor
	 */
	public static void escribir(int ...valores) {

		try {
			if (valores.length==0) {
				for (int i=1; i<=100; i++) 
					rFile.writeInt(i);
			}else if (valores.length==1) {
				rFile.seek(rFile.length());
				rFile.writeInt(valores[0]);
			}else if (valores.length==2) {
				// primer parámetro lo tomo como lugar y el segundo como nuevo valor
				int posicion = (valores[0]-1)*TAMANIO;
				// Compruebo que la posición sea correcta no superior al tamaño del fichero ni inferior a 0 
				if (posicion >= rFile.length() || posicion <0)
					System.out.println("La posición indicada no es correcta");
				else {
					rFile.seek(posicion);
					rFile.writeInt(valores[1]);
				}
			}
		} catch (IOException e) {
			System.out.println("Error de lectura/escritura");
			e.printStackTrace();
		}
	}
	
	/**
	 * Lee el contenido del fichero o el valor de una posición que se pasa como parámetro
	 * @param valores
	 * 	si vacio, lee y muestra el contenido de todo el fichero
	 * 	si tiene un único valor, muestra el elemento contenido en ese lugar
	 */
	public static void leer(int ...valores) {
		
		try {
			if (valores.length==0) {
				rFile.seek(0);
				while(rFile.getFilePointer() != rFile.length()) 
					System.out.print(rFile.readInt() + "\t");
			}else if (valores.length==1) {
				// Calculo la posición
				int posicion = (valores[0]-1)*TAMANIO;
				// Compruebo que la posición sea correcta no superior al tamaño del fichero ni inferior a 0 
				if (posicion >= rFile.length() || posicion <0)
					System.out.println("La posición indicada no es correcta");
				else {
					rFile.seek(posicion);
					System.out.println("Valor en la posición " + valores[0] + ": " + rFile.readInt());
				}
			}
		}catch (IOException e) {
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
					leer(sc.nextInt());
					break;
				case 3:
					System.out.println("AÑADIENDO ELEMENTO AL FINAL DEL FICHERO");
					System.out.println("Indique el valor a añadir: ");
					escribir(sc.nextInt());
					break;
				case 4:
					System.out.println("MODIFICANDO EL VALOR DE UNA POSICIÓN");
					System.out.println("Indique la posición a modificar: ");
					lugar = sc.nextInt();
					System.out.println("Indique el nuevo valor: ");
					escribir(lugar, sc.nextInt());
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
