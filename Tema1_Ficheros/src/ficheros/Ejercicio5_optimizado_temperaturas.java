package ficheros;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Ejercicio5_optimizado_temperaturas {
	private final static String DOCTRABAJO_IN = "FicheroAleatorioTemperaturas.dat";
	private final static int TAMANIO = 13;

	private static RandomAccessFile rFile;
	
	public static void main(String[] args) {
		try {
			rFile = new  RandomAccessFile(new File(Ejercicio3.RUTA +DOCTRABAJO_IN ), "rw");
			escribir("");
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
	 * Escribe registros de meses y temperaturas en un fichero de acceso aleatorio o modifica el valor de una
	 *  posición determinada (los valores, posición y nuevas temperaturas, se pasan como parámetros) o añade 
	 *  al final del fichero un registro de temperaturas que se pasa como parámetro. 
	 * @param mes
	 * 		mes a modificar o añadir (cadena con tres caracteres)
	 * @param valores
	 * 		\nsi vacio, copia registros de temperaturas en el fichero 
	 * 		\nsi tiene dos valores se toma el primero como temperatura mínima el segundo como máxima y se añade al final
	 * 		\nsi tiene tres valores, se toma el primero como temperatura mínima el segundo como máxima y el tercero 
	 * 		como posición a sobreescribir
	 */
	
	public static void escribir(String mes, int ...valores) {

		try {
			if (valores.length==0) {
				// Escribir 
				String[] meses = {"ene", "feb", "mar", "abr", "may", "jun"};
				int[] tem_min = {0, -1, 4, 10, 15, 15};
				int[] tem_max = {17, 18, 20, 22, 22, 25};
				for (int i=0; i<meses.length; i++) {
					rFile.writeUTF(meses[i]);
					rFile.writeInt(tem_min[i]);
					rFile.writeInt(tem_max[i]);
				}
			}else if (valores.length==2) {
				//Añadir al final
				rFile.seek(rFile.length());
				rFile.writeUTF(mes);
				rFile.writeInt(valores[0]);
				rFile.writeInt(valores[1]);
			}else if (valores.length==3) {
				// primer parámetro lo tomo como mínima el segundo como máxima y el tercero como lugar
				int posicion = (valores[2]-1)*TAMANIO;
				// Compruebo que la posición sea correcta no superior al tamaño del fichero ni inferior a 0 
				if (posicion >= rFile.length() || posicion <0)
					System.out.println("La posición indicada no es correcta");
				else {
					rFile.seek(posicion);
					rFile.writeUTF(mes);
					rFile.writeInt(valores[0]);
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
	 * 	\nsi vacio, lee y muestra el contenido de todo el fichero\n
	 * 	\nsi tiene un único valor, muestra el elemento contenido en ese lugar
	 */
	public static void leer(int ...valores) {
		
		try {
			if (valores.length==0) {
				rFile.seek(0);
				while(rFile.getFilePointer() != rFile.length()) {
					System.out.println(rFile.readUTF() + ":\t" + rFile.readInt() + "ºC/" + rFile.readInt() +"ºC" );
				}
					
			}else if (valores.length==1) {
				// Calculo la posición
				int posicion = (valores[0]-1)*TAMANIO;
				// Compruebo que la posición sea correcta no superior al tamaño del fichero ni inferior a 0 
				if (posicion >= rFile.length() || posicion <0)
					System.out.println("La posición indicada no es correcta");
				else {
					rFile.seek(posicion);
					System.out.println("Valor en la posición " + valores[0] + ": " + 
					rFile.readUTF() + ":\t" + rFile.readInt() + "ºC/" + rFile.readInt() +"ºC");
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
		int opcion, lugar, min, max;
		String mes;

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
					System.out.print("Indique el valor del mes: ");
					mes =sc.next();
					System.out.print("Indique temperatura mínima: ");
					min = sc.nextInt();
					System.out.print("Indique temperatura máxima: ");
					max = sc.nextInt();
					escribir(mes, min, max);
					break;
				case 4:
					System.out.println("MODIFICANDO EL VALOR DE UNA POSICIÓN");
					System.out.println("Indique la posición a modificar: ");
					lugar = sc.nextInt();
					System.out.print("Indique el valor del mes: ");
					mes =sc.next();
					System.out.print("Indique temperatura mínima: ");
					min = sc.nextInt();
					System.out.print("Indique temperatura máxima: ");
					max = sc.nextInt();
					escribir(mes, min, max, lugar);
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
