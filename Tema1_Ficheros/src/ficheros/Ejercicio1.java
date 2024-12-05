package ficheros;
import java.io.File;

public class Ejercicio1 {

	public static void main(String[] args) {
		File dirActual = new File(System.getProperty("user.dir"));
		File[] hijos = dirActual.listFiles();
		
		System.out.println("Nombre del directorio de trabajo actual :" + dirActual.getName() + 
				" tiene " + hijos.length + " ficheros y directorios contenidos. \n\t Contenido:");
		
		for(File f: hijos) {
			System.out.println("\t\t" + f.getName() + " ----->\t" + (f.isFile()?"F":"D"));
		}

	}

}
