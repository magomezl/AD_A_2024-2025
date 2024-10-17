package ficheros;
import java.io.File;
import java.util.Scanner;

public class Ejercicio2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Indique el nombre del archivo o directorio a analizar:");
		String fileName = sc.nextLine();
		File f = new File(fileName);
		
		if (f.exists()) {
			System.out.println((f.isFile()?"Se trata de un fichero":"Se trata de un directorio"));
			infoFichero(f);
			if (f.isDirectory()) {
				File[] hijos = f.listFiles();
				for (File file: hijos) {
					System.out.print("\t" + file.getName() + "\t" + (file.isFile()?"F\t":"D\t"));
					infoFichero(file);
				}
			}
		}else {
			System.out.println("El fichero o directorio no existe");
		}
		sc.close();
	}
	
	public static void infoFichero(File f) {
		System.out.println(
				(f.isFile()
				?		
				f.length() + " bytes " +
				(f.canRead()?" r":" -") +
				(f.canWrite()?"w":"-") +
				(f.canExecute()?"x":"-")
				:"")		
		);
	}
}
